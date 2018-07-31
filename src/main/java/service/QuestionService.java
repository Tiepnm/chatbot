package service;

import com.google.common.base.Strings;
import entity.Answer;
import entity.Question;
import entity.QuestionHasAnswer;
import entity.QuestionHasAnswerComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import repository.AnswerRepo;
import repository.QuestionHasAnswerRepo;
import repository.QuestionRepo;
import vertx.request.AnswerResponse;
import vertx.request.QuestionRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tiep on 6/5/2017.
 */
@Service
@Transactional
public class QuestionService extends BaseService<AnswerResponse, QuestionRequest> {
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private AnswerRepo answerRepo;
    @Autowired
    private QuestionHasAnswerRepo questionHasAnswerRepo;

    public List<Question> findByQuestion(@Param("question") String question) {
        return questionRepo.findByQuestion(question);
    }

    @Override
    public AnswerResponse doHandle(QuestionRequest request) {
        String[][] chatBot = {
                //default
                {"I'm sorry, I'm understand, What is $name?"}
        };
        AnswerResponse answerResponse = new AnswerResponse();
        byte response = 1;


        List<Answer> answerList = answerRepo.findByAnswer(request.getAnswerPrivous());

        if(!Strings.isNullOrEmpty(request.getAnswerPrivous()) &&
                !CollectionUtils.isEmpty( answerList) && !Strings.isNullOrEmpty(answerList.get(0).getTarget()))
        {
            String text = answerRepo.findByAnswer(request.getAnswerPrivous()).get(0).getTarget();
            answerResponse.setProvideExplainFromUser(false);
            text = text.replace("$name", request.getQuestion());
            answerResponse.setAnswer(text);
        }
        //learning
        else if (!Strings.isNullOrEmpty(request.getUserAnswer())) {
            Question question = new Question();
            question.setQuestion(request.getQuestion());
            questionRepo.save(question);

            Answer answer = new Answer();
            answer.setAnswer(request.getUserAnswer());
            answerRepo.save(answer);

            QuestionHasAnswer questionHasAnswer = new QuestionHasAnswer();
            QuestionHasAnswerComposite questionHasAnswerComposite = new QuestionHasAnswerComposite();
            questionHasAnswerComposite.setAnswerId(answer.getId());
            questionHasAnswerComposite.setQuestionId(question.getId());
            questionHasAnswer.setKey(questionHasAnswerComposite);
            questionHasAnswerRepo.save(questionHasAnswer);

            answerResponse.setAnswer("I see...");
            answerResponse.setProvideExplainFromUser(false);
        } else {
            //search in db
            List<Question> questions = findByQuestion(request.getQuestion().toLowerCase());
            if (!CollectionUtils.isEmpty(questions)) {


                List<QuestionHasAnswer> questionHasAnswers = questionHasAnswerRepo.findByQuestionId(questions.get(0).getId());

                if (CollectionUtils.isEmpty(questionHasAnswers)) {
                    response = 1;
                } else {
                    response = 2;
                    List<Long> answerIds = new ArrayList<>();
                    for (QuestionHasAnswer questionHasAnswer : questionHasAnswers) {
                        answerIds.add(questionHasAnswer.getKey().getAnswerId());
                    }
                    List<Answer> answers = answerRepo.findByIds(answerIds);
                    if (CollectionUtils.isEmpty(answers)) {
                        response = 1;
                    } else {
                        Random randomGenerator = new Random();
                        int index = randomGenerator.nextInt(answers.size());
                        System.out.println("index:"+index);
                        answerResponse.setAnswer(answers.get(index).getAnswer());
                    }

                }

            } else {
                response = 1;
            }


            //-----default--------------
            if (response == 1) {
                int r = (int) Math.floor(Math.random() * chatBot[chatBot.length - 1].length);
                String text = chatBot[chatBot.length - 1][r].replace("$name", request.getQuestion());
                answerResponse.setAnswer(text);
                answerResponse.setProvideExplainFromUser(true);
            } else {
                answerResponse.setProvideExplainFromUser(false);
            }
        }


        return answerResponse;
    }


}

