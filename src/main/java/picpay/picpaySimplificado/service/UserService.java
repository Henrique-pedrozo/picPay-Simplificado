package picpay.picpaySimplificado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picpay.picpaySimplificado.entities.Users;
import picpay.picpaySimplificado.enums.UsersType;
import picpay.picpaySimplificado.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public String transaction(Users users) {
        if (users.getUserType() == UsersType.MERCHANT) {
            return "Transação não permitida";
        } else {

            return "sim";
        }
    }
}
