package pl.marcin.project;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import pl.marcin.project.runners.RunnerFactory;

@SpringBootApplication
@PropertySource("application-local.properties")
@EnableR2dbcRepositories(basePackages = "pl.marcin.project.database")
public class ProjectApplication {

    public static void main(String[] args) {

        RunnerFactory runnerFactory = new RunnerFactory();
        runnerFactory.run(new DefaultApplicationArguments());

    }
}

