package pl.marcin.project;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import pl.marcin.project.runners.AppRunner;
import pl.marcin.project.runners.ShopAppRunner;
import pl.marcin.project.runners.WebAppRunner;


public class RunnerFactory implements ApplicationRunner {

    private AppRunner appRunner;

    @Override
    public void run(ApplicationArguments args) {
        String runningWay = "web";
        if ("web".equals(runningWay)) {
            appRunner = new WebAppRunner();
        } else if ("shop".equals(runningWay)) {
            appRunner = new ShopAppRunner();
        }
        appRunner.runApplication();
    }
}


