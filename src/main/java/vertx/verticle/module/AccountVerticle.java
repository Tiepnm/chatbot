package vertx.verticle.module;


import com.google.gson.Gson;
import infra.SpringBeanFactory;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.impl.MessageImpl;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.spi.cluster.ClusterManager;
import service.AccountService;
import service.AccountService2;
import vertx.request.AccountRequest;
import vertx.request.AccountRequest3;
import vertx.request.AccountResponse;
import vertx.request.BaseRequest;
import vertx.verticle.AbstractVerticle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 5/22/2017.
 */
public class AccountVerticle extends AbstractVerticle {


    @Override
    public void doStart() {

        ClusterManager clusterManager = ((VertxInternal) vertx).getClusterManager();
        clusterManager.getSyncMap("test");
        System.out.println(clusterManager.getSyncMap("test").get("test"));

        registerHandler(AccountRequest.class.getSimpleName(), SpringBeanFactory.beanFactory.getBean(AccountService.class));
        registerHandler(AccountRequest3.class.getSimpleName(), SpringBeanFactory.beanFactory.getBean(AccountService2.class));
//        ExecutorService executors = Executors.newFixedThreadPool(5);
//            executors.submit(() -> {
//                    });
        ExecutorService executors = Executors.newFixedThreadPool(10);
        vertx.eventBus().consumer("account", (Handler<Message<AccountRequest>>) req -> {
            MessageImpl message = (MessageImpl) req;

            System.out.println(message.body());


            Gson gson = new Gson();
            BaseRequest baseRequest = gson.fromJson(message.body().toString(), BaseRequest.class);


            executors.submit(() -> {
                if (getHandlers().get(baseRequest.getNameOfRequest()) != null) {
                    message.reply( getHandlers().get(baseRequest.getNameOfRequest()).doHandle(null));


                }
            });


//            ExecutorService executors = Executors.newFixedThreadPool(5);
//            executors.submit(() -> {
//                try {
//                    Thread.sleep(5000);
//                    message.reply(accountResponse);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });

        });

//        MessageConsumer<String> consumer = vertx.eventBus().consumer("news.uk.sport");
//        consumer.handler(message -> {
//            System.out.println("I have received a message22222: " + message.body());
//            message.reply("how interesting2222!");
//        });

    }

    // Optional - called when verticle is undeployed
    public void stop() {
    }
}