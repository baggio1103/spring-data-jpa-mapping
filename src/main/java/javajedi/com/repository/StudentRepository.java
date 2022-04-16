package javajedi.com.repository;

import javajedi.com.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Transactional
    void deleteStudentById(Integer id);

}
