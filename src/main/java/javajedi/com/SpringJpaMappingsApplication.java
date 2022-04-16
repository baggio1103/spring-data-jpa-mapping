package javajedi.com;

import javajedi.com.model.Email;
import javajedi.com.model.Student;
import javajedi.com.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJpaMappingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaMappingsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student marc = new Student("marc", "reus", "marc_11");
            marc.addEmail(new Email("marc@gmail.com"));
            studentRepository.save(marc);
        };
    }

}
