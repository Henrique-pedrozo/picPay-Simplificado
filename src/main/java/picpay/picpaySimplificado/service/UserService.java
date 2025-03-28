package picpay.picpaySimplificado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<String> validateTransaction(Optional<Users> sender, Double amount) {
        if (!sender.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Remetente não encontrado");
        }

        Users senderUser = sender.get();

        if (senderUser.getUserType() == UsersType.MERCHANT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario não permitido para fazer transação");
        }
        if (senderUser.getBalance().compareTo(amount) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Saldo atual: " + sender.get().getBalance());
    }

    public ResponseEntity<String> usersRegister(UsersDTO usersDTO) {
        Users users = new Users(usersDTO);
        if (!(userRepository.findUsersByCpf(usersDTO.cpf()).isPresent())) {
            users.setCpf(usersDTO.cpf());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cpf já cadastrado.");
        }
        if (!(userRepository.findUsersByEmail(usersDTO.email()).isPresent())) {
            users.setEmail(usersDTO.email());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado.");
        }
        userRepository.save(users);
        return ResponseEntity.ok().body("Usuario registrado com sucesso");
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
