package utility;
import model.Payment;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class PaymentSorter {

    // Sorterer forventede indbetalinger efter beløb
    //static fordi de returner ikke noget, metodene skal indkaldes i controler
    public static void sortByPayment(ArrayList<Payment> payments) {
        Collections.sort(payments,
                Comparator.comparingDouble(Payment::getExpectedPayment));// :: kaldes en method reference i Java.
    }

    // Sorterer medlemmer i restance efter beløb
    public static void sortOverdueByPayment(ArrayList<Payment> payments) {
        Collections.sort(payments,
                Comparator.comparingDouble(Payment::getExpectedPayment));
    }

    // Sorterer medlemmer i restance efter navn
    public static void sortOverdueByName(ArrayList<Payment> members) {
        Collections.sort(members,
                Comparator.comparing(Payment::getName));
    }

    //  Sortér efter dato (ældste først) Extra , står ikke i opgaven
    public static void sortByDate(ArrayList<Payment> payments) {
        Collections.sort(payments,
                Comparator.comparing(Payment::getPaymentDate));
//  .reversed().reverses the order, so highest fee comes first
    }
}