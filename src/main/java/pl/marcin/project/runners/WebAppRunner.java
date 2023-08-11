package pl.marcin.project.runners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("application-local.properties")
public class WebAppRunner implements AppRunner {


    @Override
    public void runApplication() {
        ConfigurableApplicationContext context = SpringApplication.run(WebAppRunner.class);
    }
}
