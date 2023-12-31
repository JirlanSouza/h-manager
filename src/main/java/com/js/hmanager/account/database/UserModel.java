package com.js.hmanager.account.database;

import com.js.hmanager.account.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    private UUID id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String userType;

    public static UserModel from(User user) {
        return new UserModel(
                user.getId(),
                user.getName().firstName(),
                user.getName().lastName(),
                user.getEmail().value(),
                user.getPassword().getValue(),
                user.getUserType().name()
        );
    }
}
