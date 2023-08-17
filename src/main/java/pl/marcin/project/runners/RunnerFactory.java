package pl.marcin.project.runners;

import java.util.Scanner;


public class RunnerFactory {

    private AppRunner appRunner;

    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("How do you want to run your application: web or shop ?");

        String runningWay = scanner.nextLine();
        if ("web".equals(runningWay)) {
            System.out.println("running...");

            appRunner = new WebAppRunner();
        } else if ("shop".equals(runningWay)) {
            appRunner = new ShopAppRunner();
        }
        appRunner.runApplication();
    }
}


