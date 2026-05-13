package utility;

import model.ExerciseMember;
import model.Payment;
import model.Member;
import model.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PaymentSorterTest {

    @Test
    void sortByPayment() {
        // Create local variables for memmber
        ExerciseMember m1 = new ExerciseMember("Anton",9, 7, true);
        ExerciseMember m2 = new ExerciseMember("Celia",30, 8, false);
        ExerciseMember m3 = new ExerciseMember("Tatiana",73, 22, false);
        Payment A = new Payment(LocalDate.now(), PaymentStatus.PENDING,m1);
        Payment B = new Payment(LocalDate.now(), PaymentStatus.PENDING,m2);
        Payment C = new Payment(LocalDate.now(), PaymentStatus.OVERDUE,m3);
        ArrayList<Payment> testPaymentList = new ArrayList<>();
        testPaymentList.add(A);
        testPaymentList.add(B);
        testPaymentList.add(C);
        PaymentSorter.sortByPayment(testPaymentList);//PaymentSorter er den klasse hvor vi har metoden vi kalder og tester
        assertEquals(250.0, testPaymentList.get(0).getFee(),0.001);// delta (tolerance af decimal tal)fordi vi har double
        assertEquals(250.0, testPaymentList.get(1).getFee(),0.001);
        assertEquals(800.0, testPaymentList.get(2).getFee(),0.001);
    }

}
