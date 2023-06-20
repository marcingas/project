package pl.marcin.project.runners;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.marcin.project.ProjectApplication;
import pl.marcin.project.entityService.CustomerEntityService;

public class WebAppRunner implements AppRunner {

    @Override
    public void runApplication() {
        ConfigurableApplicationContext context = SpringApplication.run(ProjectApplication.class);
        var service = context.getBean(CustomerEntityService.class);

    }
}
