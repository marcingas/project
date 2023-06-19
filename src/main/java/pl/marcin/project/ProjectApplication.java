package pl.marcin.project;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class ProjectApplication implements CommandLineRunner {
    private ApplicationRunner applicationRunner;

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        String runningWay = "shop";
        if (runningWay.equals("web")) {
            applicationRunner = new WebApplicationRunner();
        } else if (runningWay.equals("shop")) {
            applicationRunner = new ShopApplicationRunner();
        }
        applicationRunner.runApplication();
    }
}

