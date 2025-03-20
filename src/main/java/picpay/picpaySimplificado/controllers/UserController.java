package picpay.picpaySimplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import picpay.picpaySimplificado.DTO.UsersDTO;
import picpay.picpaySimplificado.entities.Users;
import picpay.picpaySimplificado.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("register")
    public ResponseEntity<String> createUser(@RequestBody UsersDTO usersDTO) {
        return userService.usersRegister(usersDTO);
    }

//    @PostMapping("changeType")
//    public ResponseEntity<String> changeType(@RequestBody UserTypeDTO users) {
//        return userService.changeType(users);
//    }
}
