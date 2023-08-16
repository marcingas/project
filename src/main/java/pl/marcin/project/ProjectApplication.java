package pl.marcin.project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import pl.marcin.project.runners.RunnerFactory;

@SpringBootApplication
@PropertySource("application-local.properties")
public class ProjectApplication {

    public static void main(String[] args) {


        RunnerFactory runnerFactory = RunnerFactory.getInstance();
        runnerFactory.run();

    }
}

