package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource resource = new AdminResource();
    private AdminResource(){}
    public static AdminResource getInstance(){
        return resource;
    }
    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);
    }

    public void addRoom(IRoom room){
        ReservationService.getInstance().addRoom(room);
    }

    public Collection<IRoom> getAllRooms(){
        return null;
    }

    public Collection<Customer> getAllCustomers(){
        return CustomerService.getInstance().getAllCustomer();

    }

    public Collection<Reservation> getAllReservations(){
        return ReservationService.getInstance().getAllReservations();
    }
}
