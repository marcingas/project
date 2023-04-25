package pl.marcin.project.departments;

import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Shop {
//    public static void main(String[] args) {
//        Shop shop = new Shop();
//        Customer poul = new Customer(1,"Poul","Kowalski","Kraków");
//       List<Cup> cupOrder = new ArrayList<>();
//       cupOrder.add(new Cup(1,"blue","square", BigDecimal.valueOf(5)));
//       cupOrder.add(new Cup(2,"Orange","circle", BigDecimal.valueOf(9)));
//       cupOrder.add(new Cup(3,"yellow","square", BigDecimal.valueOf(6)));
//        System.out.println(shop.buyCup(new Purchase(poul,cupOrder,BigDecimal.valueOf(5.20))));
//
//    }

    CustomerRepository customerRepository;

    public String addCustomer(Customer customer) {
        customerRepository.saveCustomer(customer);
        return "Customer " + customer.getId() + " saved";
    }

    public String updateCustomer(int customerId, Customer customer) {
        customerRepository.updateCustomer(customerId, customer);
        return "Customer with id " + customerId + " updated";
    }

    public String deleteCustomer(int customerId) {
        customerRepository.deleteCustomer(customerId);
        return "Customer with id " + customerId + " deleted";
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomer(int customerId) {
        return customerRepository.findCustomer(customerId);
    }
    public Purchase buyCup(Purchase purchase){
        purchase.getCustomer().getPurchaseHistory().add(purchase);
       return purchase;
    }

}
