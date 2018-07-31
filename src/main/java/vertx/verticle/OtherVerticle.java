package vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;

/**
 * Created by Administrator on 5/22/2017.
 */
public class OtherVerticle extends AbstractVerticle  {
    public void start(Future<Void> startFuture) {
        System.out.println("OtherVerticle");

        Context context = vertx.getOrCreateContext();

        if (context.isEventLoopContext()) {
            System.out.println("OtherVerticle Context attached to Event Loop");
        } else if (context.isWorkerContext()) {
            System.out.println("OtherVerticle Context attached to Worker Thread");
        } else if (context.isMultiThreadedWorkerContext()) {
            System.out.println("OtherVerticle Context attached to Worker Thread - multi threaded worker");
        } else if (! Context.isOnVertxThread()) {
            System.out.println("OtherVerticle Context not attached to a thread managed by vert.x");
        }
        MessageConsumer<String> consumer = vertx.eventBus().consumer("news.uk.sport");
        consumer.handler(message -> {
            System.out.println("OtherVerticle I have received a message: " + message.body());
            message.reply("how interesting 1111!");
        });
    }
}
