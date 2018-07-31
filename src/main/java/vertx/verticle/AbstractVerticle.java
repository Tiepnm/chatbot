package vertx.verticle;

import io.vertx.core.Future;
import service.BaseService;
import vertx.request.AccountRequest;
import vertx.request.AccountResponse;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 5/24/2017.
 */
public abstract class AbstractVerticle extends io.vertx.core.AbstractVerticle {
    private int i =0;
    @Override
    public void start() {
        doStart();
    }

    public abstract void doStart();


    private ConcurrentHashMap<String, BaseService> handlers = new ConcurrentHashMap<String, BaseService>();

    public void registerHandler(String handlerName, BaseService baseService) {
        handlers.put(handlerName, baseService);
    }

    public ConcurrentHashMap<String, BaseService> getHandlers() {
        return handlers;
    }

    public void setHandlers(ConcurrentHashMap<String, BaseService> handlers) {
        this.handlers = handlers;
    }
}
