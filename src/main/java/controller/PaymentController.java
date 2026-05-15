package controller;

import model.*;
import utility.PaymentSorter;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controller ansvarlig for håndtering af betalinger i systemet.
 * Håndterer oprettelse, registrering, sortering og hentning af betalinger.
 */
public class PaymentController {

    private final Payments payments = new Payments();

    /**
     * Opretter en ny betaling for et medlem og tilføjer den til betalingsoversigten.
     * Betalingsstatus sættes som standard til PENDING.
     *
     * @param member  medlemmet tilknyttet betalingen
     * @param dueDate datoen betalingen forfalder
     * @return det nyoprettede Payment objekt
     */
    public Payment createPayment(Member member, LocalDate dueDate) {
        Payment payment = new Payment(dueDate, PaymentStatus.PENDING, member);
        payments.addPayment(payment);
        return payment;
    }

    /**
     * Opretter betalinger fordelt på 3 rater fra medlemmets indmeldelsesdato.
     * Hver rate forfalder hver 4. måned på samme dag i måneden.
     * Kontingentet beregnes automatisk og fordeles ligeligt.
     *
     * @param member   medlemmet der oprettes betalinger for
     * @param joinDate datoen medlemmet meldte sig ind
     */
    public void createInstallmentPayments(Member member, LocalDate joinDate) {
        int installments = 3;
        double installmentFee = member.calculateFee() / installments;

        for (int i = 0; i < installments; i++) {
            LocalDate dueDate = joinDate.plusMonths(4 * i);
            Payment payment = new Payment(dueDate, PaymentStatus.PENDING, member);
            payment.setFee(installmentFee);
            payments.addPayment(payment);
        }
    }

    /**
     * Registrerer en betaling som betalt ved at sætte betalingsdatoen til dags dato.
     * Status opdateres automatisk til PAID via setDayPaid().
     *
     * @param payment betalingen der skal registreres som betalt
     */
    public void registerPayment(Payment payment) {
        payment.setDayPaid(LocalDate.now());
    }

    /**
     * Henter alle betalinger i restance sorteret efter beløb.
     *
     * @return en sorteret liste af betalinger i restance
     */
    public ArrayList<Payment> getOverduePaymentsSortedByFee() {
        PaymentSorter.sortOverdueByPayment(payments);
        return payments.getOverduePayments();
    }

    /**
     * Henter alle betalinger i restance sorteret efter navn.
     *
     * @return en sorteret liste af betalinger i restance
     */
    public ArrayList<Payment> getOverduePaymentsSortedByName() {
        PaymentSorter.sortOverdueByName(payments);
        return payments.getOverduePayments();
    }

    /**
     * Henter alle betalinger sorteret efter beløb.
     *
     * @return en sorteret liste af alle betalinger
     */
    public ArrayList<Payment> getAllPaymentsSortedByFee() {
        PaymentSorter.sortByPayment(payments);
        return payments.getPayments();
    }

    /**
     * Henter alle betalinger sorteret efter dato.
     *
     * @return en sorteret liste af alle betalinger efter dato
     */
    public ArrayList<Payment> getAllPaymentsSortedByDate() {
        PaymentSorter.sortByDate(payments);
        return payments.getPayments();
    }

    /**
     * Henter alle betalinger tilknyttet et specifikt medlem.
     *
     * @param member medlemmet hvis betalinger hentes
     * @return en liste af betalinger tilknyttet medlemmet
     */
    public ArrayList<Payment> getPaymentsForMember(Member member) {
        return payments.getPaymentsForMember(member);
    }

    /**
     * Henter alle betalinger i restance.
     *
     * @return en liste af betalinger i restance
     */
    public ArrayList<Payment> getOverduePayments() {
        return payments.getOverduePayments();
    }

    /**
     * Henter alle betalinger i systemet.
     *
     * @return en liste af alle betalinger
     */
    public ArrayList<Payment> getAllPayments() {
        return payments.getPayments();
    }
}