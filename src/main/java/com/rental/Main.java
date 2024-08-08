package com.rental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        RentalService service = new RentalService();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            Date checkoutDate = dateFormat.parse("07/02/20");

            RentalAgreement agreement = service.checkout("LADW", 3, 10, checkoutDate);
            agreement.printAgreement();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
