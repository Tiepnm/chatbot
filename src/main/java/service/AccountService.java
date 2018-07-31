package service;

import entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CustomerRepo;
import vertx.request.AccountRequest;
import vertx.request.AccountResponse;

/**
 * Created by Administrator on 5/24/2017.
 */
@Service
@Transactional
public class AccountService extends BaseService<AccountResponse, AccountRequest>  {
    @Autowired
    private CustomerRepo customerRepo;
    @Override
    public AccountResponse doHandle(AccountRequest accountRequest) {
        Customer customer = new Customer();
        customer.setName("tiep");
        customer.setEmail("nguyenmanhtiep@gmail.com");
        customerRepo.save(customer);
        System.out.println("do Handler 1");
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setResult("true");
        return accountResponse;
    }
}
