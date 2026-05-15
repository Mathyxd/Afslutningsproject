package model;

import java.util.ArrayList;


//-------------Samlede betalingsoversigt på tværs af medlemmer---------------------------
/**
 * Repræsenterer en samlet oversigt over alle betalinger i systemet.
 * Bruges af kassereren til at få overblik over indbetalinger og restancer,
 * samt se betalingshistorik for et specifikt medlem.
 */
public class Payments {
    private ArrayList<Payment> payments;

    /**
     * Opretter en ny samlet betalingsoversigt.
     */
    public Payments() {
        this.payments = new ArrayList<>();
    }

    // Getter
    public ArrayList<Payment> getPayments() { return payments; }

    /**
     * Tilføjer en betaling til oversigten.
     *
     * @param payment betalingen der skal tilføjes
     */
    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    /**
     * Fjerner en betaling fra oversigten.
     *
     * @param payment betalingen der skal fjernes
     */
    public void removePayment(Payment payment) {
        payments.remove(payment);
    }

    //-------------ArrayListe/oversigt over et medlems betalinghistorik------------------
    /**
     * Henter alle betalinger tilknyttet et specifikt medlem.
     * Giver kassereren overblik over et enkelt medlems betalingshistorik.
     *
     * @param member medlemmet hvis betalinger hentes
     * @return liste af betalinger tilknyttet medlemmet
     */
    public ArrayList<Payment> getPaymentsForMember(Member member) {
        ArrayList<Payment> memberPayments = new ArrayList<>();
        for (Payment payment : payments) {
            if (payment.getMember().equals(member)) {
                memberPayments.add(payment);
            }
        }
        return memberPayments;
    }

    /**
     * Henter alle betalinger der er i restance på tværs af alle medlemmer.
     *
     * @return liste af betalinger i restance
     */
    public ArrayList<Payment> getOverduePayments() {
        ArrayList<Payment> overduePayments = new ArrayList<>();
        for (Payment payment : payments) {
            if (payment.isOverdue()) {
                overduePayments.add(payment);
            }
        }
        return overduePayments;
    }
}