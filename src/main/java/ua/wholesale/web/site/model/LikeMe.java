package ua.wholesale.web.site.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "like_me")
public class LikeMe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long messId;

    private Long userId;

}
