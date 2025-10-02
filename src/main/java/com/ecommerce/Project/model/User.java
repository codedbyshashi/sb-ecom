package com.ecommerce.Project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max=20)
    private String username;

    @NotBlank
    @Size(max=20)
    @Email
    private String email;

    @NotBlank
    @Size(max=120)
    private String password;

    public User(Long userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},
    fetch=FetchType.EAGER)
    @JoinTable(name="user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<Role>();

}
