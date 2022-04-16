package javajedi.com.controller;

import javajedi.com.model.Student;
import javajedi.com.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("students")
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("")
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @PostMapping("")
    public Student saveStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @DeleteMapping
    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }

}
