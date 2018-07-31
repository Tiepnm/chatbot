package vertx.verticle.module;


import com.google.gson.Gson;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.impl.MessageImpl;
import service.AccountService2;
import vertx.request.AccountRequest2;
import vertx.request.AccountResponse;
import vertx.request.BaseRequest;
import vertx.verticle.AbstractVerticle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 5/22/2017.
 */
public class PaymentVerticle extends AbstractVerticle {


    @Override
    public void doStart() {

        registerHandler(AccountRequest2.class.getSimpleName(), new AccountService2());

        vertx.eventBus().consumer("payment", (Handler<Message<AccountRequest2>>) req -> {
            MessageImpl message = (MessageImpl) req;


            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setResult("PaymentVerticle Replly");

            Gson gson = new Gson();
            BaseRequest baseRequest = gson.fromJson(message.body().toString(), BaseRequest.class);
            if (getHandlers().get(baseRequest.getNameOfRequest()) != null) {
                getHandlers().get(baseRequest.getNameOfRequest()).doHandle(null);
            }

            ExecutorService executors = Executors.newFixedThreadPool(5);
            executors.submit(() -> {
                try {
                    Thread.sleep(5000);
                    message.reply(accountResponse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        });
    }

    // Optional - called when verticle is undeployed
    public void stop() {
    }
}