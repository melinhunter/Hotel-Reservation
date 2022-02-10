package ui;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    public static void main(String[] args) {
        boolean exit = false;
        String userInput = null;

            //exit = true;

            try {
                String msg = "Please select a number for the menu option";
                while(!exit) {
                    displayMenu();
                    userInput = MyScanner.getInput(msg);
                    System.out.println("User Input: " + userInput);
                    switch (userInput){
                        case "1":
                            //Find and reserve a room
                            findAndReserveARoom();
                            break;
                        case "2":
                            //See my reservations"
                            seeMyReservations();
                            break;
                        case "3":
                            //Create a account
                            createAccount();
                            break;
                        case "4":
                            runAdminMenu();
                            break;
                        case "5":
                            System.out.println("5");
                            exit = true;
                            break;
                    }
                }

            } catch (Exception ex) {

                System.err.println(ex.getLocalizedMessage());
                exit = true;
            } finally {

            }

    }

    private static void createAccount() {
        String firstName = null;
        String lastName = null;
        String email = null;

        while(firstName == null){
            String input = MyScanner.getInput("first name:");
            firstName = input;
        }

        while(lastName == null){
            String input = MyScanner.getInput("last name:");
            lastName = input;
        }
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        while(email == null){
            String input = MyScanner.getInput("email:(ex:name@domain.com");
            if(emailPattern.matcher(input).matches()){
                email = input;
            }
        }
        hotelResource.createACustomer(email, firstName, lastName);

    }

    private static Customer getInputCustomerEmail(){
        Customer customer = null;
        while(customer == null){
            String msg = "Please enter a valid customer email: [0 to exit] ";
            String input = MyScanner.getInput(msg);
            if(input.equals("0")){
                return null;
            }
            customer = hotelResource.getCustomer(input);
        }
        return customer;
    }

    private static void seeMyReservations(){
        Customer customer = getInputCustomerEmail();
        if(customer == null){
            System.out.println("Customer not found.");
        }
        else{
            seeCustomerReservations(customer.getEmail());
        }
    }

    private static void seeCustomerReservations(String email) {
        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
        for(Reservation reservation : reservations){
            System.out.println(reservation);
        }
    }


    public static final String DATE_FORMATE = "MM/dd/yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMATE);

    private static Date transInputToDate(String dateString){
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    private static void findAndReserveARoom() {
        Date checkInDate = null;
        Date checkOutDate = null;
        Date recommendCheckInDate = null;
        Date recommendCheckOutDate = null;
        boolean checkDateErr = false;
        while(checkInDate == null){
            String msg = "check in date (MM/dd/yyyy):";
            String input = MyScanner.getInput(msg);
            checkInDate = transInputToDate(input);
        }

        while(checkOutDate == null){
            String msg = "check out date (MM/dd/yyyy):";
            String input = MyScanner.getInput(msg);
            checkOutDate = transInputToDate(input);
            if(checkInDate.after(checkOutDate)){
                System.out.println(" check in date can't after check out date!!");
                checkDateErr = false;
            }
        }
        if(checkDateErr){
            findAndReserveARoom();
            return;
        }
        Collection<IRoom> rooms = HotelResource.findRooms(checkInDate, checkOutDate);
        Collection<IRoom> recommandRooms = null;
        String wantToBookRoom = null;
        if(rooms == null || rooms.isEmpty()){
            recommendCheckInDate = getRecommendDate(checkInDate);
            recommendCheckOutDate = getRecommendDate(checkOutDate);
            recommandRooms = HotelResource.findRooms(recommendCheckInDate, recommendCheckOutDate);
            System.out.println("There are no rooms available for the date you selected.");
            if(recommandRooms !=  null && !recommandRooms.isEmpty()) {
                System.out.println("Rooms available for alternative dates, check in on " + recommendCheckInDate + " and check out on " + recommendCheckOutDate);
                displayRooms(recommandRooms);

                while (wantToBookRoom == null) {
                    String input = MyScanner.getInput("Do you like the room in the recommended date?? (Y/N)");
                    if (input.length() == 1) {
                        char inputChar = input.charAt(0);
                        if (inputChar == 'Y' || inputChar == 'y') {
                            wantToBookRoom = "true";
                            break;
                        }
                        if (inputChar == 'N' || inputChar == 'n') {
                            wantToBookRoom = "false";
                            break;
                        }
                    }
                }

                if(wantToBookRoom.equalsIgnoreCase("true")){
                    checkInDate = recommendCheckInDate;
                    checkOutDate = recommendCheckOutDate;
                    rooms = recommandRooms;
                }
            }
            else{
                return;
            }
        }
        else{
            displayRooms(rooms);
            while(wantToBookRoom == null) {
                String input = MyScanner.getInput("Do you like any one of list room(s) ? (Y/N)");
                if(input.length() == 1){
                    char inputChar = input.charAt(0);
                    if(inputChar == 'Y' || inputChar == 'y'){
                        wantToBookRoom = "true";
                        break;
                    }
                    if(inputChar == 'N' || inputChar == 'n'){
                        wantToBookRoom = "false";
                        break;
                    }
                }
            }
        }
        Map<String , IRoom> map = new HashMap<>();
        for(IRoom room:rooms){
            map.put(room.getRoomNumber(), room);
        }
        if(wantToBookRoom.equalsIgnoreCase("true")){
            Customer customer = null;
            IRoom room = null;
            while(room == null){
                String msg = "Reserve rom number:";
                String input = MyScanner.getInput(msg);
                room = map.get(input);
                if(room != null){
                    System.out.println(room);
                }
            }
            customer = getInputCustomerEmail();
            if(customer == null){
                System.out.println("Customer not found.");
            }
            else {
                hotelResource.reserveARoom(customer, room, checkInDate, checkOutDate);
                if("Y".equalsIgnoreCase(MyScanner.getInput("see all customer reservations ? (y/Y to see)"))){
                    seeCustomerReservations(customer.getEmail());
                }
            }
        }
    }

    private static void displayRooms(Collection<IRoom> rooms){
        for(IRoom room : rooms){
            System.out.println(rooms);
        }
    }

    private static Date getRecommendDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 7);
        return c.getTime();
    }
    public static void displayMenu(){
        System.out.println("Hotel Reservation Application");
        System.out.println("-----------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create a account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("-----------------------------");
    }

    private static void runAdminMenu(){
        AdminMenu.main(null);
    }
}
