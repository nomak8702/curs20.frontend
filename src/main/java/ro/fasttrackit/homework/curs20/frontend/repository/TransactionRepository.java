package ro.fasttrackit.homework.curs20.frontend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.homework.curs20.frontend.entities.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByProductStartsWithIgnoreCase(String product);
}
