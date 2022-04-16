package javajedi.com.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "email",
        uniqueConstraints = {@UniqueConstraint(name = "email_box_name_unique", columnNames = {"email_box_name"})})
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email_box_name", nullable = false)
    private String emailBoxName;

}
