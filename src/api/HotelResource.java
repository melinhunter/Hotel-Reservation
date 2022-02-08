package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource resource = new HotelResource();
    private HotelResource(){}
    public static HotelResource getInstance(){
        return resource;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return ReservationService.getInstance().findRooms(checkInDate, checkOutDate);
    }

    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);

    }

    public void createACustomer(String email, String firstName, String lastName){
        CustomerService.getInstance().addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber){
        return ReservationService.getInstance().getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);
        return ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);
        return ReservationService.getInstance().getCustomersReservation(customer);
    }

    public Reservation reserveARoom(Customer customer , IRoom room, Date checkInDate, Date checkOutDate) {
        return ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
    }

}
