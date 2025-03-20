package picpay.picpaySimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import picpay.picpaySimplificado.entities.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUsersByCpf(String cpf);
    Optional<Users> findUsersByEmail(String email);
}
