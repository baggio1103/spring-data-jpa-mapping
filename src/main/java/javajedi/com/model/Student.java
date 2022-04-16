package javajedi.com.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "student",
        uniqueConstraints = {@UniqueConstraint(name = "student_unique_username", columnNames = "user_name")
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

    /**
     * This type of relationship is called - Unidirectional,
     * since there is only one mapping between two entities.
     * Student entity has a list of emails and student is the parent entity or owning side
     * Output:
     * Three tables will be generated:
     * - student
     * - email
     * - student_emails
     * Executing a new student with some email will generate the following sql
     * Hibernate:
     *     insert
     *     into
     *         student
     *         (first_name, last_name, user_name)
     *     values
     *         (?, ?, ?)
     * Hibernate:
     *     insert
     *     into
     *         email
     *         (email_box_name)
     *     values
     *         (?)
     * Hibernate:
     *     insert
     *     into
     *         student_emails
     *         (student_id, emails_id)
     *     values
     *         (?, ?)
     * However, this is not the best way of mapping
     * */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Email> emails;

    public Student(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        emails = new ArrayList<>();
    }

    public void addEmail(Email email) {
        emails.add(email);
    }

}
