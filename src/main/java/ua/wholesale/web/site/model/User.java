package ua.wholesale.web.site.model;

import lombok.Data;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "like_me")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotEmpty()
    @Size(max = 255, min = 1)
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty()
    @Size(max = 255, min = 1)
    @Column(name = "lastname")
    private String lastname;

    @NotEmpty()
    @Size(max = 255, min = 5)
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty()
    @Size(max = 255, min = 1)
    @Column(name = "date")
    private String date;

    @NotEmpty()
    @Size(max = 255, min = 1)
    @Column(name = "password")
    private String password;

    @Transient
    @NotEmpty()
    @Size(max = 255, min = 1)
    private String confirmPassword;


    @NotEmpty()
    @Email()
    @Column(name = "email", unique = true)
    private String email;

    @NotEmpty()
    @Size()
    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "gender")
    private String gender;

    @Column(name = "status")
    private boolean status;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Role roles;

}
