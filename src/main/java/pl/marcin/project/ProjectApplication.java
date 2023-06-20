package pl.marcin.project;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {

        RunnerFactory runnerFactory = new RunnerFactory();
        runnerFactory.run(new DefaultApplicationArguments());

    }
}

