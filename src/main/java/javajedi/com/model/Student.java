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
     *
     *
     * Try to delete a student generates the following sql:
     * Hibernate:
     *     delete
     *     from
     *         student_emails
     *     where
     *         student_id=?
     * Hibernate:
     *     delete
     *     from
     *         email
     *     where
     *         id=?
     * Hibernate:
     *     delete
     *     from
     *         student
     *     where
     *         id=?
     * Totally inefficient, since 3 different sql statements are generated
     *
     * Getting a specific student generates the following sql:
     * Hibernate:
     *     select
     *         student0_.id as id1_1_0_,
     *         student0_.first_name as first_na2_1_0_,
     *         student0_.last_name as last_nam3_1_0_,
     *         student0_.user_name as user_nam4_1_0_
     *     from
     *         student student0_
     *     where
     *         student0_.id=?
     * Hibernate:
     *     select
     *         emails0_.student_id as student_1_2_0_,
     *         emails0_.emails_id as emails_i2_2_0_,
     *         email1_.id as id1_0_1_,
     *         email1_.email_box_name as email_bo2_0_1_
     *     from
     *         student_emails emails0_
     *     inner join
     *         email email1_
     *             on emails0_.emails_id=email1_.id
     *     where
     *         emails0_.student_id=?
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
