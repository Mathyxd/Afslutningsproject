package model;

import java.time.LocalDate;

//JavaDOC - eksempel på hvordan det kan opsættes og benyttes
/**
 * Repræsenterer en betaling tilknyttet et medlem.
 * Håndterer kontingent, forfaldsdato, betalingsstatus og restancelogik.
 */
public class Payment {
    private double fee;
    private LocalDate dueDate;
    private LocalDate dayPaid; // Bør sættes når der betales. Nok af en metode i PaymentController
    private PaymentStatus status;
    private Member member;

    /**
     * Opretter en ny betaling for et medlem.
     * Kontingentet beregnes automatisk ud fra medlemmets type.
     * Bemærk: dayPaid sættes ikke i konstruktøren, da den kun sættes når betalingen er gennemført.
     *
     * @param dueDate forfaldsdatoen for betalingen
     * @param status  den indledende betalingsstatus
     * @param member  medlemmet tilknyttet betalingen
     */

    public Payment(LocalDate dueDate, PaymentStatus status, Member member){
        this.fee = member.calculateFee();
        this.dueDate = dueDate;
        this.status = status;
        this.member = member;
    }

    /**
     * Tjekker om betalingen er i restance.
     * En betaling er i restance hvis status er PENDING
     * og forfaldsdatoen er overskredet.
     *
     * @return true hvis betalingen er i restance, false ellers
     */
    public boolean isOverdue() {
        return status == PaymentStatus.PENDING && LocalDate.now().isAfter(dueDate);
    }

    // getters

    public double getFee() {
        return fee;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getDayPaid(){
        return dayPaid;
    }

    public PaymentStatus getStatus(){
        return status;
    }

    public Member getMember() {
        return member;
    }


    // setters

    public void setFee(double fee){
        this.fee = fee;
    }

    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }

    // Bør nok sættes op til at genererer det pågældende tidspunkt, der bliver betalt
    public void setDayPaid(LocalDate dayPaid){
        this.dayPaid = dayPaid;
        this.status = PaymentStatus.PAID; //Når der betales vil det måske give mening at vi sætter dem begge?
    }

    public void setStatus(PaymentStatus status){
        this.status = status;
    }

    public void setMember(Member member){
        this.member = member;
    }
}