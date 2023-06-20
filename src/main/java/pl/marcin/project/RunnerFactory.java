package pl.marcin.project;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import pl.marcin.project.runners.AppRunner;
import pl.marcin.project.runners.ShopAppRunner;
import pl.marcin.project.runners.WebAppRunner;


public class RunnerFactory implements ApplicationRunner {

    private AppRunner appRunner;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String runningWay = "shop";
        if ("web".equals(runningWay)) {
            appRunner = new WebAppRunner();
        } else if ("shop".equals(runningWay)) {
            appRunner = new ShopAppRunner();
        }
        appRunner.runApplication();
    }
}


