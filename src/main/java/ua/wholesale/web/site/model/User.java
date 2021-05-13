package ua.wholesale.web.site.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotEmpty()
    @Size(max = 255, min = 5)
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty()
    @Size(max = 255, min = 5)
    @Column(name = "lastname")
    private String lastname;

    @NotEmpty()
    @Size(max = 255, min = 5)
    @Column(name = "username",unique = true)
    private String username;

    @NotEmpty()
    @Size(max = 255, min = 5)
    @Column(name = "date")
    private String date;

    @NotEmpty()
    @Size(max = 255, min = 5)
    @Column(name = "password")
    private String password;

    @Transient
    @NotEmpty()
    @Size(max = 255, min = 5)
    @Column(name = "password2")
    private String password2;

    @NotEmpty()
    @Email()
    @Column(name = "email",unique = true)
    private String email;

    @NotEmpty()
    @Size(max = 255, min = 1)
    @Column(name = "phone",unique = true)
    private String phone;

    @Column(name = "gender")
    private String gender;

    @Column(name = "filenameq")
    private String filenameq;

    @Column(name = "status")
    private boolean status;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Goods> goods;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
