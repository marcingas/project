package pl.marcin.project.runners;

import org.springframework.boot.SpringApplication;
import pl.marcin.project.ProjectApplication;

public class WebAppRunner implements AppRunner {

    @Override
    public void runApplication() {
        System.out.println("Running application....");
        SpringApplication.run(ProjectApplication.class);
    }
}
