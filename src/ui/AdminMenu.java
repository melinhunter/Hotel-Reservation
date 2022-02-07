package ui;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.regex.Pattern;

public class AdminMenu {
    private static final String INPUT_MSG = "Please select a number for the menu option";
    public static void main(String[] args) {
        boolean exit = false;
        while(!exit) {
            displayMenu();
            String inputStr = MyScanner.getInput(INPUT_MSG);
            switch(inputStr){
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addARoom();
                    break;
                case "5":
                    exit = true;
                    break;
            }
        }
    }

    private static void addARoom() {
        String roomNumber = null;
        Double price = null;
        RoomType roomType = null;
        IRoom room;
        String roomNumberRegex = "^[1-9]\\d{2}$";
        Pattern roomNumberPattern = Pattern.compile(roomNumberRegex);
        while(roomNumber == null){
            String input = MyScanner.getInput("Room Number ? (100~999)");
            if(roomNumberPattern.matcher(input).matches()){
                roomNumber = input;
            }
        }
        String priceRegex = "^[0-9.]*$";
        Pattern pricePattern = Pattern.compile(priceRegex);

        while(price == null){
            String input = MyScanner.getInput("Price per night?");
            if(pricePattern.matcher(input).matches()){
                price = Double.parseDouble(input);
            }
        }

        while(roomType == null){
            String input = MyScanner.getInput("Single(s) or Double(d)");
            if(input.length() == 1){
                char inputChar = input.charAt(0);
                if(inputChar == 's' || inputChar == 'S'){
                    roomType = RoomType.SINGLE;
                }
                else if(inputChar == 'd' || inputChar == 'D'){
                    roomType = RoomType.DOUBLE;
                }
            }
        }
        if(price == 0){
            room = new FreeRoom(roomNumber, roomType);
        }
        else {
            room = new Room(roomNumber, price, roomType);
        }
        ReservationService.service.addRoom(room);
        boolean addOther = false;
        while(true){
            String input = MyScanner.getInput("Do you want add other room ? (Y/N)");
            if(input.length() == 1){
                char inputChar = input.charAt(0);
                if(inputChar == 'Y' || inputChar == 'y'){
                    addOther = true;
                    break;
                }
                if(inputChar == 'N' || inputChar == 'n'){
                    addOther = false;
                    break;
                }
            }
        }
        if(addOther){
            addARoom();
        }
    }

    private static void seeAllReservations() {
        ReservationService service = ReservationService.service;
        for(Reservation reservation : service.getAllReservations()){
            System.out.println(reservation);
        }
    }

    private static void seeAllRooms() {
        ReservationService service = ReservationService.service;
        for(IRoom room : service.getAllRooms()){
            System.out.println(room);
        }
    }

    private static void seeAllCustomers() {
        CustomerService service = CustomerService.service;
        for(Customer customer : service.getAllCustomer()){
            System.out.println(customer);
        }
    }

    public static void displayMenu(){
        System.out.println("-----------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("-----------------------------");
    }
}
