package service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 5/24/2017.
 */
@Service
public class AccountService2 extends BaseService {
    @Override
    public Object doHandle(Object o) {
        System.out.println("do Handler 2");
        return null;
    }
}
