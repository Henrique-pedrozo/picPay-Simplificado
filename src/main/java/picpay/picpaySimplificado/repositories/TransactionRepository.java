package picpay.picpaySimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import picpay.picpaySimplificado.entities.Transactions;

public interface TransactionRepository extends JpaRepository<Transactions, Integer> {
}
