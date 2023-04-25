package pl.marcin.project.model;


import java.util.List;


public class Customer {

    private int id;

    private String name;

    private String surname;

    private String address;
    private List<Purchase> purchaseHistory;

    public Customer(int id, String name, String surname, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public List<Purchase> getPurchaseHistory() {
        return purchaseHistory;
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\nsurname: " + surname;
    }
}
