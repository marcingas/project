package pl.marcin.project.repository;

import pl.marcin.project.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRepositoryListBased implements PurchaseRepository{
    private List<Purchase> purchases = new ArrayList<>();
}
