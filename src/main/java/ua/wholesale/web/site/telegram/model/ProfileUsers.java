package ua.wholesale.web.site.telegram.model;

import lombok.Data;
import ua.wholesale.web.site.model.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "profile_users")
public class ProfileUsers{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ElementCollection(targetClass = UserTelegram.class)
    private List<UserTelegram> userProfile = new ArrayList<>();

    @Column(name = "profileUserid")
    private Long profileUserid ;

}
