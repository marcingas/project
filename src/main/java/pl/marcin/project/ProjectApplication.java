package pl.marcin.project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.marcin.project.runners.RunnerFactory;

@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ProjectApplication.class);
        RunnerFactory runnerFactory = new RunnerFactory();
        runnerFactory.run();

    }
}

