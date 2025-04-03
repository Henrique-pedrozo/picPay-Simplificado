package picpay.picpaySimplificado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import picpay.picpaySimplificado.DTO.UsersDTO;
import picpay.picpaySimplificado.entities.Users;
import picpay.picpaySimplificado.enums.UsersType;
import picpay.picpaySimplificado.exceptions.*;
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

    public ResponseEntity<String> validateTransaction(Optional<Users> sender, Double amount) {
        Users senderUser = sender.get();
        if (senderUser.getUserType() == UsersType.MERCHANT) {
            throw new UnauthorizedTransactionException("Usuário não permitido para fazer transação");
        }
        if (senderUser.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente");
        }
        if (amount < 1) {
            throw new MinimumValueException("Valor mínimo é 1.0");
        }
        if (senderUser.getId().equals(senderUser.getId())) {
            throw new SelfTransactionException("Remetente não pode fazer transações para si próprio.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Saldo atual: " + sender.get().getBalance());
    }

    public ResponseEntity<String> usersRegister(UsersDTO usersDTO) {
        Users users = new Users(usersDTO);
        if (!(usersDTO.name().matches("[a-zA-Z]+"))) {
            throw new BadRequestException("Nome inválido");
        }
        if (!usersDTO.cpf().matches("\\d{11}")) {
            throw new ConflictCpfException("Cpf incorreto: Não pode conter letras e ser menor que 11 caracteres");
        }
        if (userRepository.findUsersByCpf(usersDTO.cpf()).isPresent()) {
            throw new ConflictCpfException("Cpf já cadastrado.");
        }
        if (userRepository.findUsersByEmail(usersDTO.email()).isPresent()) {
            throw new ConflictEmailException("Email já existe.");
        }
        if (!usersDTO.email().matches("^[\\w.-]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$")){
            throw new ConflictEmailException("Email em formato não convencional");
        }
        users.setEmail(usersDTO.email());
        users.setCpf(usersDTO.cpf());
        userRepository.save(users);
        return ResponseEntity.ok().body("Usuário registrado com sucesso");
    }

//    public ResponseEntity<String> changeType(UserTypeDTO usersTypeDTO) {
//        Users users = new Users();
//        String type = "";
//        switch (type) {
//            case "MERCHANT":
//                users.setUserType(UsersType.MERCHANT);
//                return ResponseEntity.ok().body("Mudado com sucesso");
//            case "COMMON":
//                users.setUserType(UsersType.COMMON);
//                return ResponseEntity.ok().body("Mudado com sucesso");
//        }
//        return ResponseEntity.badRequest().body("Coloque um tipo existente.");
//    }
}
