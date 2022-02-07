package api;

import model.Customer;
import model.IRoom;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static AdminResource resource = new AdminResource();

    public Customer getCustomer(String email){
        return null;
    }

    public void addRoom(List<IRoom> rooms){

    }

    public Collection<IRoom> getAllRooms(){
        return null;
    }

    public Collection<Customer> getAllCustomers(){
        return null;
    }

    public void displayAllReservations(){

    }
}
