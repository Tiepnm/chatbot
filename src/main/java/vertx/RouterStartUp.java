package vertx;


import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import vertx.codec.Mycodec;
import vertx.codec.Mycodec2;
import vertx.request.*;
import vertx.verticle.controller.RouterVerticle;

/**
 * Created by Administrator on 5/22/2017.
 */
class RouterStartUp {
    public static void main(String[] args) throws InterruptedException {
        Vertx vertx = Vertx.vertx();


//        DeploymentOptions options = new DeploymentOptions().setWorker(true);
//        OtherVerticle otherVerticle = new OtherVerticle();
//        vertx.deployVerticle(otherVerticle, options);


        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions options3 = new VertxOptions().setClusterManager(mgr);


//        //options3.setClusterHost("192.168.1.14");
        Vertx.clusteredVertx(options3, res -> {
            if (res.succeeded()) {
                Vertx vertx3 = res.result();

                //vertx3.eventBus().send("news.uk.sport2", "Yay! Someone kicked a ball");

                vertx3.eventBus().registerDefaultCodec(AccountRequest.class, new Mycodec<AccountRequest>(AccountRequest.class.getName()));
                vertx3.eventBus().registerDefaultCodec(AccountResponse.class, new Mycodec2());
                vertx3.eventBus().registerDefaultCodec(AccountRequest2.class, new Mycodec<AccountRequest2>(AccountRequest2.class.getName()));
                vertx3.eventBus().registerDefaultCodec(AccountRequest3.class, new Mycodec<AccountRequest3>(AccountRequest3.class.getName()));
                vertx3.eventBus().registerDefaultCodec(QuestionRequest.class, new Mycodec<QuestionRequest>(QuestionRequest.class.getName()));
                vertx3.eventBus().registerDefaultCodec(AnswerResponse.class, new Mycodec<AnswerResponse>(AnswerResponse.class.getName()));

                RouterVerticle verticle = new RouterVerticle();


                vertx3.deployVerticle(verticle, event -> {
                    if(event.succeeded())
                    {
                        System.out.println("RouterVerticle started....");
                    }
                });


                //  vertx3.eventBus().publish("service", request);


                //vertx3.eventBus().send("news.uk.sport2", "Yay! Someone kicked a ball");
            } else {
                // failed!
            }
        });


    }
}