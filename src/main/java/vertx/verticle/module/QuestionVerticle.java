package vertx.verticle.module;

import com.google.gson.Gson;
import infra.SpringBeanFactory;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.impl.MessageImpl;
import service.AccountService;
import service.QuestionService;
import vertx.request.*;
import vertx.verticle.AbstractVerticle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 6/5/2017.
 */
public class QuestionVerticle extends AbstractVerticle {
    @Override
    public void doStart() {

        registerHandler(QuestionRequest.class.getSimpleName(), SpringBeanFactory.beanFactory.getBean(QuestionService.class));

        ExecutorService executors = Executors.newFixedThreadPool(10);
        vertx.eventBus().consumer("question", (Handler<Message<AccountRequest>>) req -> {
            MessageImpl message = (MessageImpl) req;

            Gson gson = new Gson();
            QuestionRequest questionRequest = gson.fromJson(message.body().toString(), QuestionRequest.class);


            executors.submit(() -> {
                if (getHandlers().get(questionRequest.getNameOfRequest()) != null) {
                    message.reply( getHandlers().get(questionRequest.getNameOfRequest()).doHandle(questionRequest));

                }
            });
        });
    }
}
