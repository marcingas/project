package pl.marcin.project.runners;

import pl.marcin.project.controller.Shop;
import pl.marcin.project.runners.AppRunner;

public class ShopAppRunner implements AppRunner {
    @Override
    public void runApplication() {

        Shop.main(new String[]{});
    }
}
