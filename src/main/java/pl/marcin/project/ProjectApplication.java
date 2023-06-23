package pl.marcin.project;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.marcin.project.runners.RunnerFactory;

@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {

        RunnerFactory runnerFactory = new RunnerFactory();
        runnerFactory.run(new DefaultApplicationArguments());

    }
}

