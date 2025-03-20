package picpay.picpaySimplificado.DTO;

import picpay.picpaySimplificado.enums.UsersType;

public record UsersDTO(String name, String cpf, String email, String password, Double balance, UsersType userType) {
}
