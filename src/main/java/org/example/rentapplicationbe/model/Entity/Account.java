package org.example.rentapplicationbe.model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
@Table(name = "account")
public class Account  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private String username;
    private String password;
    private String phone;
    private String full_name;
    private String address;
    private String status;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;


//    public Account(Long id) {
//        this.id = id;
//    }

}