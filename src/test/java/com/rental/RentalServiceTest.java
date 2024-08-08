package com.rental;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
    private final RentalService service = new RentalService();

    @Test
    void testCheckoutInvalidDiscount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.checkout("JAKR", 5, 101, dateFormat.parse("09/03/15"));
        });
        assertEquals("Discount percent must be between 0 and 100.", exception.getMessage());
    }

    @Test
    void testCheckoutLADW() throws ParseException {
        Date checkoutDate = dateFormat.parse("07/02/20");
        RentalAgreement agreement = service.checkout("LADW", 3, 10, checkoutDate);

        assertNotNull(agreement);
        assertEquals("LADW", agreement.getTool().getCode());
        assertEquals("Ladder", agreement.getTool().getType());
        assertEquals("Werner", agreement.getTool().getBrand());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(dateFormat.parse("07/05/20"), agreement.getDueDate());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(5.97, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(10, agreement.getDiscountPercent());
        assertEquals(0.59, agreement.getDiscountAmount(), 0.01);
        assertEquals(5.37, agreement.getFinalCharge(), 0.01);
    }

    @Test
    void testCheckoutCHNS() throws ParseException {
        Date checkoutDate = dateFormat.parse("07/02/15");
        RentalAgreement agreement = service.checkout("CHNS", 5, 25, checkoutDate);

        assertNotNull(agreement);
        assertEquals("CHNS", agreement.getTool().getCode());
        assertEquals("Chainsaw", agreement.getTool().getType());
        assertEquals("Stihl", agreement.getTool().getBrand());
        assertEquals(5, agreement.getRentalDays());
        assertEquals(dateFormat.parse("07/07/15"), agreement.getDueDate());
        assertEquals(4, agreement.getChargeDays());
        assertEquals(5.96, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(25, agreement.getDiscountPercent());
        assertEquals(1.49, agreement.getDiscountAmount(), 0.01);
        assertEquals(4.47, agreement.getFinalCharge(), 0.01);
    }

    @Test
    void testCheckoutJAKD() throws ParseException {
        Date checkoutDate = dateFormat.parse("09/03/15");
        RentalAgreement agreement = service.checkout("JAKD", 6, 0, checkoutDate);

        assertNotNull(agreement);
        assertEquals("JAKD", agreement.getTool().getCode());
        assertEquals("Jackhammer", agreement.getTool().getType());
        assertEquals("DeWalt", agreement.getTool().getBrand());
        assertEquals(6, agreement.getRentalDays());
        assertEquals(dateFormat.parse("09/09/15"), agreement.getDueDate());
        assertEquals(4, agreement.getChargeDays());
        assertEquals(11.96, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(0.00, agreement.getDiscountAmount(), 0.01);
        assertEquals(11.96, agreement.getFinalCharge(), 0.01);
    }

    @Test
    void testCheckoutJAKR1() throws ParseException {
        Date checkoutDate = dateFormat.parse("07/02/15");
        RentalAgreement agreement = service.checkout("JAKR", 9, 0, checkoutDate);

        assertNotNull(agreement);
        assertEquals("JAKR", agreement.getTool().getCode());
        assertEquals("Jackhammer", agreement.getTool().getType());
        assertEquals("Ridgid", agreement.getTool().getBrand());
        assertEquals(9, agreement.getRentalDays());
        assertEquals(dateFormat.parse("07/11/15"), agreement.getDueDate());
        assertEquals(6, agreement.getChargeDays());
        assertEquals(17.94, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(0.00, agreement.getDiscountAmount(), 0.01);
        assertEquals(17.94, agreement.getFinalCharge(), 0.01);
    }

    @Test
    void testCheckoutJAKR2() throws ParseException {
        Date checkoutDate = dateFormat.parse("07/02/20");
        RentalAgreement agreement = service.checkout("JAKR", 4, 50, checkoutDate);

        assertNotNull(agreement);
        assertEquals("JAKR", agreement.getTool().getCode());
        assertEquals("Jackhammer", agreement.getTool().getType());
        assertEquals("Ridgid", agreement.getTool().getBrand());
        assertEquals(4, agreement.getRentalDays());
        assertEquals(dateFormat.parse("07/06/20"), agreement.getDueDate());
        assertEquals(2, agreement.getChargeDays());
        assertEquals(5.98, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(50, agreement.getDiscountPercent());
        assertEquals(2.99, agreement.getDiscountAmount(), 0.01);
        assertEquals(2.99, agreement.getFinalCharge(), 0.01);
    }
}
