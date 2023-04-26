package pl.marcin.project.controller;

import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.service.CustomerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    public static void main(String[] args) {
        CustomerService shop = new CustomerService();
        Customer jas = new Customer(1,"Jaś","Kowalski","Kraków");
        List<Cup> cupOrder = new ArrayList<>();
        cupOrder.add(new Cup(1,"blue","square", BigDecimal.valueOf(5)));
        cupOrder.add(new Cup(2,"Orange","circle", BigDecimal.valueOf(9)));
        cupOrder.add(new Cup(3,"yellow","square", BigDecimal.valueOf(6)));
        System.out.println(shop.buyCup(new Purchase(jas,cupOrder)));

    }
}
