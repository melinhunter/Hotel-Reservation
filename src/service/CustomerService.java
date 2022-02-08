package service;

import model.Customer;

import java.util.LinkedList;
import java.util.Collection;

public class CustomerService {
    private Collection<Customer> customers = new LinkedList<Customer>();
    private static CustomerService service = null;
    private CustomerService(){

    }

    public static CustomerService getInstance(){
        if(service == null){
            service = new CustomerService();
        }
        return service;
    }
    public void addCustomer(String email, String firstName, String lastName){
        if(getCustomer(email) == null){
            Customer customer = new Customer(firstName, lastName, email);
            customers.add(customer);
        }
        else {
            System.out.println("Customer email is already exists!!");
        }
    }

    public Customer getCustomer(String email){
        if(email == null) return null;
        for(Customer customer : customers){
            if(email.equalsIgnoreCase(customer.getEmail())){
                return customer;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomer(){
        return customers;
    }
}
