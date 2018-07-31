package vertx;


import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import vertx.codec.Mycodec;
import vertx.codec.Mycodec2;
import vertx.request.*;
import vertx.verticle.module.AccountVerticle;
import vertx.verticle.module.PaymentVerticle;
import vertx.verticle.module.QuestionVerticle;

/**
 * Created by Administrator on 5/22/2017.
 */
class ApplicationStartUp {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(new String[]{"spring-context.xml"});


        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions options3 = new VertxOptions().setClusterManager(mgr);
        //options3.setClusterHost("192.168.1.14");
        Vertx.clusteredVertx(options3, res -> {
            if (res.succeeded()) {
                Vertx vertx3 = res.result();
                vertx3.eventBus().registerDefaultCodec(AccountRequest.class, new Mycodec<AccountRequest>(AccountRequest.class.getName()));
                vertx3.eventBus().registerDefaultCodec(AccountResponse.class, new Mycodec2());
                vertx3.eventBus().registerDefaultCodec(AccountRequest2.class, new Mycodec<AccountRequest2>(AccountRequest2.class.getName()));
                vertx3.eventBus().registerDefaultCodec(AccountRequest3.class, new Mycodec<AccountRequest3>(AccountRequest3.class.getName()));
                vertx3.eventBus().registerDefaultCodec(QuestionRequest.class, new Mycodec<QuestionRequest>(QuestionRequest.class.getName()));
                vertx3.eventBus().registerDefaultCodec(AnswerResponse.class, new Mycodec<AnswerResponse>(AnswerResponse.class.getName()));

                vertx3.deployVerticle(new AccountVerticle(), event -> {
                    if (event.succeeded()) {
                        System.out.println("AccountVerticle started....");
                    }
                });

                vertx3.deployVerticle(new PaymentVerticle(), event -> {
                    if (event.succeeded()) {
                        System.out.println("PaymentVerticle started....");
                    }
                });

                vertx3.deployVerticle(new QuestionVerticle(), event -> {
                    if (event.succeeded()) {
                        System.out.println("QuestionVerticle started....");
                    }
                });

            } else {
                // failed!
            }
        });


    }
}