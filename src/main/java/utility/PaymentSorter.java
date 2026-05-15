package utility;

import model.Payment;
import model.Payments;

import java.util.Collections;
import java.util.Comparator;

public class PaymentSorter {

    // Sorterer forventede indbetalinger efter beløb
    // static fordi de returnerer ikke noget, metoderne skal indkaldes i controller
    public static void sortByPayment(Payments payments) {
        Collections.sort(payments.getPayments(),
                Comparator.comparingDouble(Payment::getFee));
    }

    // Sorterer medlemmer i restance efter beløb
    public static void sortOverdueByPayment(Payments payments) {
        Collections.sort(payments.getOverduePayments(),
                Comparator.comparingDouble(Payment::getFee));
    }

    // Sorterer medlemmer i restance efter navn
    public static void sortOverdueByName(Payments payments) {
        Collections.sort(payments.getOverduePayments(),
                Comparator.comparing(Payment::getMember));
    }
//    // Sorterer medlemmer i restance efter navn
//    public static void sortOverdueByName(ArrayList<Payment> members) {
//        Collections.sort(members,
//                Comparator.comparing(Payment::getMember));
//    }
//    // Sorterer betalinger efter medlemsnavn ifølge chatten
//    public static void sortOverdueByName(ArrayList<Payment> payments) {
//        Collections.sort(payments,
//                Comparator.comparing(payment ->
//                        payment.getMember().getName()));

    // Sorterer efter dato (ældste først) - Extra, står ikke i opgaven
    public static void sortByDate(Payments payments) {
        Collections.sort(payments.getPayments(),
                Comparator.comparing(Payment::getDueDate));
    }
    // .reversed() kan tilføjes for omvendt rækkefølge fx højeste beløb først
}