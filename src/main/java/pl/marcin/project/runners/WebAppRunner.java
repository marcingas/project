package pl.marcin.project.runners;

import org.springframework.boot.SpringApplication;
import pl.marcin.project.ProjectApplication;

public class WebAppRunner implements AppRunner {
    private static WebAppRunner instance;

    private WebAppRunner() {
    }

    public static synchronized WebAppRunner getInstance() {
        if (instance == null) {
            instance = new WebAppRunner();
        }
        return instance;
    }

    @Override
    public void runApplication() {
        System.out.println("Running application....");
        SpringApplication.run(ProjectApplication.class);
    }
}
