package javajedi.com.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "student",
        uniqueConstraints = {@UniqueConstraint(name = "student_unique_username", columnNames = "user_name"),
                @UniqueConstraint(name = "student_unique_email", columnNames = "email"),
})
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

}
