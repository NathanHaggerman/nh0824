package com.rental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt for Tool Code
            System.out.print("Enter Tool Code: ");
            String toolCode = scanner.nextLine();

            // Prompt for Rental Days
            System.out.print("Enter Rental Days: ");
            int rentalDays = Integer.parseInt(scanner.nextLine());

            // Prompt for Discount Percent
            System.out.print("Enter Discount Percent: ");
            int discountPercent = Integer.parseInt(scanner.nextLine());

            // Prompt for Checkout Date
            System.out.print("Enter Checkout Date (MM/dd/yy): ");
            String checkoutDateStr = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            Date checkoutDate = dateFormat.parse(checkoutDateStr);

            // Call checkout method
            RentalAgreement agreement = service.checkout(toolCode, rentalDays, discountPercent, checkoutDate);
            agreement.printAgreement();
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use MM/dd/yy.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter valid integers for rental days and discount percent.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
