package controller;
import model.Member;
import model.Payment;
import model.PaymentStatus;

import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Controller ansvarlig for håndtering af betalinger i systemet.
 * Håndterer oprettelse, registrering og hentning af betalinger.
 */
public class PaymentController {

    private final ArrayList<Payment> payments = new ArrayList<>();

    /**
     * Opretter en ny betaling for et medlem og tilføjer den til betalingslisten.
     * Betalingsstatus sættes som standard til PENDING.
     *
     * @param member  medlemmet tilknyttet betalingen
     * @param dueDate datoen betalingen forfalder
     * @return det nyoprettede Payment objekt
     */
    public Payment createPayment(Member member, LocalDate dueDate) {
        Payment payment = new Payment(dueDate, PaymentStatus.PENDING, member);
        payments.add(payment);
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
            payments.add(payment);
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
     * Henter alle betalinger der er i restance.
     * En betaling er i restance hvis den er PENDING og forfaldsdatoen er overskredet.
     *
     * @return en liste af betalinger i restance
     */
    public ArrayList<Payment> getOverduePayments() {
        ArrayList<Payment> overdue = new ArrayList<>();
        for (Payment p : payments) {
            if (p.isOverdue()) {
                overdue.add(p);
            }
        }
        return overdue;
    }

    /**
     * Henter alle betalinger i systemet.
     *
     * @return en liste af alle betalinger
     */
    public ArrayList<Payment> getAllPayments() {
        return payments;
    }
}