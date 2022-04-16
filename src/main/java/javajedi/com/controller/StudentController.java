package javajedi.com.controller;

import javajedi.com.model.Student;
import javajedi.com.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("students")
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("")
    public void findAll() {
        studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public void findById(@PathVariable("id") Integer id) {
        studentRepository.findById(id).get();
    }

    @PostMapping("")
    public void saveStudent(@RequestBody Student student) {
        student.addEmail(student.getEmails().stream().findFirst().get());
        studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Integer id) {
        studentRepository.deleteStudentById(id);
    }

}
