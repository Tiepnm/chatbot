package service;

/**
 * Created by Administrator on 5/24/2017.
 */
public abstract class BaseService<T, E> {
    public abstract T doHandle(E e);
}
