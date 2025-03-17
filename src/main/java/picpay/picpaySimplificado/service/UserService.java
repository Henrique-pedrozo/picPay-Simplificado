package picpay.picpaySimplificado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picpay.picpaySimplificado.DTO.UsersDTO;
import picpay.picpaySimplificado.entities.Users;
import picpay.picpaySimplificado.enums.UsersType;
import picpay.picpaySimplificado.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Optional<Users> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Users saveUsers(Users users) {
        return userRepository.save(users);
    }

    public void validateTransaction(Users sender, Double amount) throws Exception {
        if (sender.getUserType() == UsersType.MERCHANT) {
            throw new Exception("Usuario não permitido para fazer transação");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public String usersRegister(UsersDTO usersDTO) {
        Users users = new Users();
        users.setName(usersDTO.name());
        users.setCpf(usersDTO.cpf());
        users.setEmail(usersDTO.email());
        users.setPassword(usersDTO.password());
        users.setBalance(0.0);
        if (users.getUserType() == UsersType.MERCHANT) {
            users.setUserType(UsersType.MERCHANT);
        }else {
            users.setUserType(UsersType.COMMON);
        }
        userRepository.save(users);
        return "Usuario registrado com sucesso";
    }
}
