package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.sql.Array;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Date;

public class ReservationService {
    private Collection<Reservation> reservations = new LinkedList<Reservation>();
    private Collection<IRoom> rooms = new LinkedList<IRoom>();

    private static ReservationService service = null;
    private ReservationService(){

    }


    public static ReservationService getInstance(){
        if(service == null){
            service = new ReservationService();
        }
        return service;
    }

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
        Collection<String> reservedRooms = findReservedRooms(checkInDate, checkOutDate)
        Collection<IRoom> rooms = new LinkedList<>();
        for(IRoom room : getAllRooms()){
            if(reservedRooms.contains(room.getRoomNumber())){
                continue;
            }
            rooms.add(room);
        }
        return rooms;
    }

    Collection<String> findReservedRooms(Date checkInDate, Date checkOutDate){
        Collection<String> reservedRooms = new LinkedList<>();
        for(Reservation r : reservations){
            if(checkOutDate.after(r.getCheckInDate())
                    && r.getCheckOutDate().after(checkInDate)){
                reservedRooms.add(r.getRoom().getRoomNumber());
            }
        }
        return reservedRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        Collection<Reservation> col = new LinkedList<>();
        for(Reservation r : reservations) {
            if(customer.equals(r.getCustomer())){
                col.add(r);
            }
        }
        return col;
    }

//    public static void printAllReservation(){
//        for(Reservation r : reservations){
//            System.out.println(r);
//        }
//    }

    public Collection<IRoom> getAllRooms(){
        return rooms;
    }

    public Collection<Reservation> getAllReservations(){
        return reservations;
    }
}
