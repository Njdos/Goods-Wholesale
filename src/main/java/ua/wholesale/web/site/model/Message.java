package ua.wholesale.web.site.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
@Table(name = "message")
public class Message {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @NotEmpty()
    @Size(max = 255, min = 1)
    @Column(name = "title")
    private String title;

    @Column(name = "heading")
    private String heading;

    @NotEmpty()
    @Size(max = 2048, min = 1)
    @Column(name = "description")
    private String description;

    @Column(name = "filename_1")
    private String filename_1;

    @Column(name = "filename_2")
    private String filename_2;

    @Column(name = "filename_3")
    private String filename_3;

    @NotEmpty()
    @Size(max = 2048, min = 1)
    @Column(name = "price")
    private long price;

    @NotEmpty()
    @Size(max = 2048, min = 1)
    @Column(name = "place")
    private String place;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Long author;

    public Message(String title, String heading, String description,long price,String place,Long user) {
        this.title = title;
        this.heading = heading;
        this.description = description;
        this.price = price;
        this.place = place;
        this.author = user;
    }
}