package picpay.picpaySimplificado.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import picpay.picpaySimplificado.enums.UsersType;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String cpf;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Double balance;

    @Enumerated(EnumType.STRING)
    private UsersType userType;
}
