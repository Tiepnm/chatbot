package repository;

import entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 5/31/2017.
 */
@Repository
public interface CustomerRepo extends PagingAndSortingRepository<Customer, Long> {
}
