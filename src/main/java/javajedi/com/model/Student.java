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
     * This type of relationship is called - Bidirectional,
     * It differs from Unidirectional, since two sides are linked with each other, i.e.
     * Student get all its emails and via emails it is possible to get all the users as well
     * Student entity is a parent side and email entity is a child entity
     * - mappedBy is a key word that links two sides of the relationship.
     * All the actions - {deletion, saving, updating} is propagated via parentSide
     * Importantly,
     * While saving a new Student with emails, the most important part to pay attention to is :
     *
     * public void addEmail(Email email) {
     *    emails.add(email);
     *    email.setStudent(this); //ESSENTIAL
     * }
     * if we just execute - emails.add(email) and omit this method - email.setStudent(this);;
     * we will save a new email in the email-table and a user, but email will have no parent
     * Email will reference to a null user in the database.
     * Since we use keyword - mappedBy, we should implicitly set parentEntity for the child entity

     * public void addEmail(Email email) {
     *    emails.add(email);
     *    email.setStudent(this);
     * }
     * By doing so - executing both emails.add(email); and email.setStudent(this);
     * we will save all the data correctly.
     *
     *
     *
     * Output:
     * Two tables will be generated:
     * - student
     * - email
     *
     * Saving a new student with some new email / emails will generate the following sql
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
     *         (email_box_name, student_id)
     *     values
     *         (?, ?)
     *
     * Trying to delete a student generates the following sql:
     * Hibernate:
     *     select
     *         student0_.id as id1_1_,
     *         student0_.first_name as first_na2_1_,
     *         student0_.last_name as last_nam3_1_,
     *         student0_.user_name as user_nam4_1_
     *     from
     *         student student0_
     *     where
     *         student0_.id=?
     * Hibernate:
     *     select
     *         emails0_.student_id as student_3_0_0_,
     *         emails0_.id as id1_0_0_,
     *         emails0_.id as id1_0_1_,
     *         emails0_.email_box_name as email_bo2_0_1_,
     *         emails0_.student_id as student_3_0_1_
     *     from
     *         email emails0_
     *     where
     *         emails0_.student_id=?
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
     * (The following statement is optional, since if FetchType is LAZY,
     * the following sql statement will not be generated)
     * Hibernate:
     *     select
     *         emails0_.student_id as student_3_0_0_,
     *         emails0_.id as id1_0_0_,
     *         emails0_.id as id1_0_1_,
     *         emails0_.email_box_name as email_bo2_0_1_,
     *         emails0_.student_id as student_3_0_1_
     *     from
     *         email emails0_
     *     where
     *         emails0_.student_id=?
     * */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Email> emails;

    public Student(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        emails = new ArrayList<>();
    }

    public void addEmail(Email email) {
        emails.add(email);
        email.setStudent(this);
    }

}
