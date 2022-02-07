package model;

import java.util.ArrayList;
import java.util.Collection;

public class Driver {
    public static void main(String[] args) {
        Customer customer = new Customer("first","last","email@domain.com");
        System.out.println(customer);

        Collection<Customer> customers = new ArrayList<>();
        Customer me = new Customer("f","l","email@domain.com");
        Customer me2 = new Customer("F","L","Email@domain.com");
        Customer me3 = new Customer("f","l","Email@domain.com");

        System.out.println(me.equals(me2));
        System.out.println(me.hashCode());
        System.out.println(me2.hashCode());
        System.out.println(me3.hashCode());
    }
}
