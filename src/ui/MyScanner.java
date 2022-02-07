package ui;

import java.util.Scanner;

public class MyScanner {
    public static void main(String[] args) {
        String msg = "Do you want add other room ? (y, n)";
        MyScanner.getInput(msg);
        MyScanner.getInput(msg);
    }
    public static String getInput(String msg){
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        try{
            String userInput = scanner.nextLine();
            return userInput;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally
        {

        }
        return null;
    }
}
