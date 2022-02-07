package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ReservationService {
    public static ReservationService service = new ReservationService();
    private Collection<Reservation> reservations = new ArrayList<Reservation>();
    private Collection<IRoom> rooms = new ArrayList<IRoom>();


    public void addRoom(IRoom room){
        if(! rooms.contains(room)){
            rooms.add(room);
        }
    }

    public IRoom getARoom(String roomId){
        for(IRoom room: rooms) {
            if(room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation r = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(r);
        return r;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> rooms = new ArrayList<>();
        for(Reservation r : reservations){
            if(checkOutDate.after(r.getCheckInDate())
                    && r.getCheckOutDate().after(checkInDate)){
                rooms.add(r.getRoom());
            }
        }
        return rooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        Collection<Reservation> col = new ArrayList<>();
        for(Reservation r : reservations) {
            if(customer.equals(r.getCustomer())){
                col.add(r);
            }
        }
        return col;
    }

    public void printAllReservation(){
        for(Reservation r : reservations){
            System.out.println(r);
        }
    }

    public Collection<IRoom> getAllRooms(){
        return rooms;
    }

    public Collection<Reservation> getAllReservations(){
        return reservations;
    }
}
