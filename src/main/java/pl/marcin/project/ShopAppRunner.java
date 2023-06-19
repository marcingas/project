package pl.marcin.project;

import pl.marcin.project.controller.Shop;

public class ShopAppRunner implements AppRunner {
    @Override
    public void runApplication() {
        Shop.main(new String[]{});
    }
}
