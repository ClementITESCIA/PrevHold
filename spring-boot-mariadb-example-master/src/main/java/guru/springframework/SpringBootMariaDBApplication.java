package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

//Cette classe hérite de SpringBootServletInitializer, ce qui lui permet d'indiquer à Tomcat qu'il y a des Servlets
//et à l'applicaiton de démarrer.
@SpringBootApplication
@EnableScheduling
public class SpringBootMariaDBApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootMariaDBApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMariaDBApplication.class, args);
    }
}

