package pl.marcin.project.runners;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Scanner;


public class RunnerFactory implements ApplicationRunner {

    private AppRunner appRunner;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("How do you want to run your application: web or shop ?");
        Scanner scanner = new Scanner(System.in);
        String runningWay = scanner.nextLine();
        if ("web".equals(runningWay)) {
            appRunner = new WebAppRunner();
        } else if ("shop".equals(runningWay)) {
            appRunner = new ShopAppRunner();
        } else {
            throw new RuntimeException("Wrong way!");
        }
        appRunner.runApplication();
    }
}


