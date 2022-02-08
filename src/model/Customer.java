package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    static final String EMAIL_REGEX = "^(.+)@(.+).(.+)$";



    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        Pattern p = Pattern.compile(EMAIL_REGEX);
        if(!p.matcher(email).matches()){
            throw new IllegalArgumentException("email must format name@domain.com!!");
        }

        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return email.equalsIgnoreCase(customer.email);
    }

    @Override
    public int hashCode(){
        return Objects.hash(email);
    }

}
