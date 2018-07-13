package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
public class SpringBootMariaDBApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMariaDBApplication.class, args);

    }
}
