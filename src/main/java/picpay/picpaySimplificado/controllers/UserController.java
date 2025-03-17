package picpay.picpaySimplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import picpay.picpaySimplificado.DTO.UsersDTO;
import picpay.picpaySimplificado.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> createUser(@RequestBody UsersDTO usersDTO) {
        return ResponseEntity.ok().body(userService.usersRegister(usersDTO));
    }
}
