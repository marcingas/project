package pl.marcin.project;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppRunnerConfig implements ApplicationRunner {

    private AppRunner appRunner;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String runningWay = "web";
        if (runningWay.equals("web")) {
            appRunner = new WebAppRunner();
        } else if (runningWay.equals("shop")) {
            appRunner = new ShopAppRunner();
        }
        appRunner.runApplication();
    }
}


