package vertx;

import com.google.gson.Gson;
import entity.Question;
import infra.SpringBeanFactory;
import service.QuestionService;
import vertx.request.AnswerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class ChatBotDB extends JFrame implements KeyListener {

    JPanel p = new JPanel();
    JTextArea dialog = new JTextArea(20, 50);
    JTextArea input = new JTextArea(1, 50);
    JScrollPane scroll = new JScrollPane(
            dialog,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    String[][] chatBot = {

            //default
            {"shut up", "you're bad", "noob", "stop talking",
                    "(michael is unavailable, due to LOL)"}
    };
    private boolean isProvideExplainFromUser;
    private String keywordPrivous = "";
    private String answerPrivous = "";


    public static void main(String[] args) {
        new ChatBotDB();
    }

    public ChatBotDB() {
        super("Chat Bot");

        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        dialog.setEditable(false);
        input.addKeyListener(this);

        p.add(scroll);
        p.add(input);
        p.setBackground(new Color(255, 200, 0));
        add(p);

        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(false);
            //-----grab quote-----------
            String quote = input.getText();
            input.setText("");
            addText("-->You:\t" + quote);
            quote.trim();
            while (
                    quote.charAt(quote.length() - 1) == '!' ||
                            quote.charAt(quote.length() - 1) == '.' ||
                            quote.charAt(quote.length() - 1) == '?'
                    ) {
                quote = quote.substring(0, quote.length() - 1);
            }
            quote.trim();


            byte response = 0;
            try {

                AnswerResponse answerResponse;
                //learning from  user
                if (isProvideExplainFromUser) {
                    answerResponse = sendGet(keywordPrivous, quote, answerPrivous);
                    isProvideExplainFromUser = false;

                    addText("\n-->Michael\t" + answerResponse.getAnswer());
                    addText("What do you want to talk about?");
                    addText("\n");
                }
                // searching from db
                else {
                    answerResponse = sendGet(quote, "", answerPrivous);
                    isProvideExplainFromUser = answerResponse.isProvideExplainFromUser();
                    keywordPrivous = quote;

                    addText("\n-->Michael\t" + answerResponse.getAnswer());
                    addText("\n");
                }
                answerPrivous = answerResponse.getAnswer();

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    private List<Question> getQuestionListFromQuestion(String q) {
        QuestionService service = SpringBeanFactory.beanFactory.getBean(QuestionService.class);
        List<Question> questions = service.findByQuestion(q);
        return questions;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(true);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void addText(String str) {
        dialog.setText(dialog.getText() + str);
    }

    public boolean inArray(String in, String[] str) {
        boolean match = false;
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals(in)) {
                match = true;
            }
        }
        return match;
    }

    private AnswerResponse sendGet(String question, String answer, String answerPrivous) throws Exception {

        String url = "http://localhost:8080/question?q=" + URLEncoder.encode(question, "UTF-8") + "&a=" + URLEncoder.encode(answer, "UTF-8")
                +"&p="+ URLEncoder.encode(answerPrivous, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        Gson gson = new Gson();
        AnswerResponse answerResponse = gson.fromJson(response.toString(), AnswerResponse.class);
        //print result
        return answerResponse;

    }
}