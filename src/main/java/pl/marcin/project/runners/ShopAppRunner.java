package pl.marcin.project.runners;

import pl.marcin.project.controller.Shop;

public class ShopAppRunner implements AppRunner {
    @Override
    public void runApplication() {
        Shop shop = new Shop();
        shop.shopRunner();
    }
}
