package com.js.hmanager.account.authentication;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor()
@Table(name = "users")
@Entity
public class UserDetailsModel {
    @Id
    private UUID id;
    private String email;
    private String password;
    private String userType;

    public User toUserAuth() {
        return new User(this.email, this.password, List.of(new SimpleGrantedAuthority(this.userType)));
    }
}
