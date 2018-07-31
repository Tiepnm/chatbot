package vertx.verticle.controller;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.spi.cluster.ClusterManager;
import vertx.request.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 5/22/2017.
 */
public class RouterVerticle extends AbstractVerticle {

    public void start() {
        // Now deploy some other verticle:
//
//        vertx.deployVerticle("com.foo.OtherVerticle", res -> {
//            if (res.succeeded()) {
//                startFuture.complete();
//            } else {
//                startFuture.fail(res.cause());
//            }
//        });



//        MessageConsumer<String> consumer = vertx.eventBus().consumer("news.uk.sport");
//        consumer.handler(message -> {
//            System.out.println("I have received a message: " + message.body());
//            message.reply("how interesting!");
//        });


//        vertx.eventBus().consumer("service", (Handler<Message<AccountRequest>>) req -> {
//            MessageImpl message = (MessageImpl) req;
//            System.out.println(message.body());
//            AccountResponse accountResponse = new AccountResponse();
//            accountResponse.setResult("repply");
//            vertx.eventBus().registerDefaultCodec(AccountResponse.class, new Mycodec2());
//              message.reply(accountResponse);
//        });



        ClusterManager clusterManager = ((VertxInternal) vertx).getClusterManager();
        clusterManager.getSyncMap("test").put("test", "test1");

        ExecutorService executors = Executors.newFixedThreadPool(10);
        vertx.createHttpServer().requestHandler(request -> {
            // vertx.eventBus().publish("service", request);


            if (request.path().equals("/account")) {

                AccountRequest request2 = new AccountRequest("1");
                request2.setNameOfRequest(AccountRequest.class.getSimpleName());
                request2.setId("1");

                executors.submit(() -> {
                    vertx.eventBus().send("account", request2, (Handler<AsyncResult<Message<AccountResponse>>>) messageAsyncResult -> {
                                System.out.println(messageAsyncResult.result().body());

                                     request.response().end("send success" + messageAsyncResult.result().body());

                            }

                    );
                });

            }
            else if (request.path().equals("/account2")) {

                AccountRequest3 request3 = new AccountRequest3();
                request3.setNameOfRequest(AccountRequest3.class.getSimpleName());
                vertx.eventBus().send("account", request3, (Handler<AsyncResult<Message<AccountResponse>>>) messageAsyncResult -> {
                            System.out.println("send success" + messageAsyncResult.result().body());

                        }

                );

            }
            else if(request.path().equals("/payment")) {

                AccountRequest2 request2 = new AccountRequest2();
                request2.setNameOfRequest(AccountRequest2.class.getSimpleName());
                request2.setId2("1");
                vertx.eventBus().send("payment", request2, (Handler<AsyncResult<Message<AccountResponse>>>) messageAsyncResult -> {
                            System.out.println("send success" + messageAsyncResult.result().body());

                        }

                );


            }
            else if (request.path().equals("/question")) {

                QuestionRequest questionRequest = new QuestionRequest();
                questionRequest.setNameOfRequest(QuestionRequest.class.getSimpleName());
                questionRequest.setQuestion(request.getParam("q"));
                questionRequest.setUserAnswer(request.getParam("a"));
                questionRequest.setAnswerPrivous(request.getParam("p"));
                executors.submit(() -> {
                    vertx.eventBus().send("question", questionRequest, (Handler<AsyncResult<Message<AccountResponse>>>) messageAsyncResult -> {
                            System.out.println("send success" + messageAsyncResult.result().body());
                             request.response().end(messageAsyncResult.result().body()+"");

                            }

                    );
                });

            }


            System.out.println("work other.........");
            //request.response().end("Hello world");
        }).listen(8080);
    }

    // Optional - called when verticle is undeployed
    public void stop() {
    }
}