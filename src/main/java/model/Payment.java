package model;

import java.time.LocalDate;

public class Payment {
    private double fee;
    private LocalDate dueDate;
    private LocalDate dayPaid; // Bør sættes når der betales. Nok af en metode i PaymentController
    private PaymentStatus status;
    private Member member;

    public Payment(LocalDate dueDate, PaymentStatus status, Member member){
        this.fee = member.calculateFee();
        this.dueDate = dueDate;
        this.status = status;
        this.member = member;
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

    public PaymentStatus status(){
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
    }

    public void setStatus(PaymentStatus status){
        this.status = status;
    }

    public void setMember(Member member){
        this.member = member;
    }
}