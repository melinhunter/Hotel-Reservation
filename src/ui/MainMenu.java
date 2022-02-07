package ui;

import service.CustomerService;

import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
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
        CustomerService service = CustomerService.service;
        service.addCustomer(email, firstName, lastName);

    }

    private static void seeMyReservations() {
    }

    private static void findAndReserveARoom() {
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
