package ua.wholesale.web.site.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "goods")
@NoArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotEmpty()
    @Size(max = 255, min = 1)
    @Column(name = "title",unique = true)
    private String title;

    @Column(name = "heading")
    private String heading;

    @Column(name = "state")
    private String state;

    @NotEmpty()
    @Size(max = 2048, min = 1)
    @Column(name = "description")
    private String description;

    @NotEmpty()
    @Size(max = 2048, min = 1)
    @Column(name = "manufacturer")
    private String manufacturer;

    @NotEmpty()
    @Size(max = 999, min = 1)
    @Column(name = "count")
    private long count;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filenames")
    private String filenames;

    @Column(name = "filenamesq")
    private String filenamesq;

    @NotEmpty()
    @Size(max = 2048, min = 1)
    @Column(name = "price")
    private long price;
    
    @NotEmpty(message = "Username cannot be empty")
    @Size(max = 2048, min = 1, message = "Message too long Or too short")
    @Column(name = "place")
    private String place;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public Goods(String title, String heading, String description, String manufacturer, long price, String place, long count, User user) {
        this.title = title;
        this.heading = heading;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
        this.count = count;
        this.place = place;
        this.author = user;
    }

}
