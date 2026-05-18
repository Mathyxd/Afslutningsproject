package utility;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PaymentSorterTest {

    @Test
    void sortByPayment() {
        // Create local variables for member
        ExerciseMember m1 = new ExerciseMember("Anton", 9, 7, true);
        ExerciseMember m2 = new ExerciseMember("Celia", 30, 8, false);
        ExerciseMember m3 = new ExerciseMember("Tatiana", 73, 22, false);

        Payment A = new Payment(LocalDate.now(), PaymentStatus.PENDING, m1);
        Payment B = new Payment(LocalDate.now(), PaymentStatus.PENDING, m2);
        Payment C = new Payment(LocalDate.now(), PaymentStatus.OVERDUE, m3);

        // Brug Payments objekt i stedet for ArrayList
        Payments testPaymentList = new Payments();
        testPaymentList.addPayment(A);
        testPaymentList.addPayment(B);
        testPaymentList.addPayment(C);

        PaymentSorter.sortByPayment(testPaymentList);

        // Hent listen via getPayments()
        assertEquals(250.0, testPaymentList.getPayments().get(0).getFee(), 0.001);
        assertEquals(250.0, testPaymentList.getPayments().get(1).getFee(), 0.001);
        assertEquals(800.0, testPaymentList.getPayments().get(2).getFee(), 0.001);
    }
}