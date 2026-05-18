package ui;

import controller.MemberController;
import controller.PaymentController;
import model.*;
import utility.Colors;

import java.time.LocalDate;
import java.util.ArrayList;

public class TreasureUI implements InterfaceUI {
    static UserInput userInput = new UserInput();
    static PaymentController paymentController = new PaymentController();
    static MemberController memberController = new MemberController();


    @Override
    public void runProgram() {

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== KASSERER MENU ===");
            System.out.println("1. Se alle forventede indbetalinger");
            System.out.println("2. Se medlemmer i restance");
            System.out.println("3. Registrer betaling");
            System.out.println("4. Opret ratebetaling for medlem");
            System.out.println("5. Se betalinger for specifikt medlem");
            System.out.println("6. Forlad menu");

            int input = userInput.inputInt(6);

            switch (input) {
                case 1:
                    showAllPayments();
                    break;
                case 2:
                    showOverduePayments();
                    break;
                case 3:
                    registerPayment();
                    break;
                case 4:
                    createInstallmentPayments();
                    break;
                case 5:
                    showPaymentsForMember();
                    break;
                case 6:
                    isRunning = false;
                    break;
            }
        }
    }

    /* Viser alle forventede indbetalinger sorteret efter beløb eller dato */
    public static void showAllPayments() {
        System.out.println("\n=== FORVENTEDE INDBETALINGER ===");
        System.out.println("Sorter efter: 1. Beløb  2. Dato");
        int input = userInput.inputInt(2);

        ArrayList<Payment> payments;
        if (input == 1) {
            payments = paymentController.getAllPaymentsSortedByFee();
        } else {
            payments = paymentController.getAllPaymentsSortedByDate();
        }

        if (payments.isEmpty()) {
            System.out.println(Colors.YELLOW + "Ingen betalinger registreret." + Colors.RESET);
            return;
        }

        printPaymentList(payments);
        printPaymentSummary(payments);
    }

    /* Viser alle medlemmer i restance sorteret efter beløb eller navn */
    public static void showOverduePayments() {
        System.out.println("\n=== MEDLEMMER I RESTANCE ===");
        System.out.println("Sorter efter: 1. Beløb  2. Navn");
        int input = userInput.inputInt(2);

        ArrayList<Payment> overduePayments;
        if (input == 1) {
            overduePayments = paymentController.getOverduePaymentsSortedByFee();
        } else {
            overduePayments = paymentController.getOverduePaymentsSortedByName();
        }

        if (overduePayments.isEmpty()) {
            System.out.println(Colors.GREEN + "Ingen medlemmer i restance." + Colors.RESET);
            return;
        }

        printPaymentList(overduePayments);
        printPaymentSummary(overduePayments);
    }

    /* Registrerer en betaling som betalt */
    public static void registerPayment() {
        System.out.println("\n=== REGISTRER BETALING ===");
        Member member = findMember();
        if (member == null) return;

        ArrayList<Payment> memberPayments = paymentController.getPaymentsForMember(member);

        if (memberPayments.isEmpty()) {
            System.out.println(Colors.YELLOW + "Ingen betalinger fundet for " +
                    member.getName() + Colors.RESET);
            return;
        }

        // Vis kun ubetalte betalinger
        ArrayList<Payment> pendingPayments = new ArrayList<>();
        for (Payment p : memberPayments) {
            if (p.getStatus() == PaymentStatus.PENDING) {
                pendingPayments.add(p);
            }
        }

        if (pendingPayments.isEmpty()) {
            System.out.println(Colors.GREEN + member.getName() +
                    " har ingen udestående betalinger." + Colors.RESET);
            return;
        }

        System.out.println("\nVælg betaling der skal registreres som betalt:");
        for (int i = 0; i < pendingPayments.size(); i++) {
            Payment p = pendingPayments.get(i);
            String color = p.isOverdue() ? Colors.RED : Colors.YELLOW;
            System.out.println((i + 1) + ". " + color +
                    "Beløb: " + String.format("%.2f kr.", p.getFee()) +
                    " | Forfaldsdato: " + p.getDueDate() +
                    (p.isOverdue() ? " | RESTANCE" : "") +
                    Colors.RESET);
        }

        int choice = userInput.inputInt(pendingPayments.size());
        Payment payment = pendingPayments.get(choice - 1);

        System.out.println("Bekræft betaling på " +
                String.format("%.2f kr.", payment.getFee()) +
                " for " + member.getName() + "?");
        boolean confirm = userInput.inputBool();

        if (confirm) {
            paymentController.registerPayment(payment);
            System.out.println(Colors.GREEN + "Betaling registreret for " +
                    member.getName() + " den " + payment.getDayPaid() + Colors.RESET);
        } else {
            System.out.println(Colors.YELLOW + "Betaling ikke registreret." + Colors.RESET);
        }
    }

    /* Opretter ratebetaling for et medlem */
    public static void createInstallmentPayments() {
        System.out.println("\n=== OPRET RATEBETALING ===");
        Member member = findMember();
        if (member == null) return;

        System.out.println("Indtast indmeldelsesdato (antal dage siden indmeldelse):");
        int days = userInput.inputInt(365);
        LocalDate joinDate = LocalDate.now().minusDays(days);

        System.out.println("Opret 3 rater for " + member.getName() +
                " med start " + joinDate + "?");
        boolean confirm = userInput.inputBool();

        if (confirm) {
            paymentController.createInstallmentPayments(member, joinDate);
            System.out.println(Colors.GREEN + "3 rater oprettet for " + member.getName() +
                    " á " + String.format("%.2f kr.", member.calculateFee() / 3) +
                    Colors.RESET);
        } else {
            System.out.println(Colors.YELLOW + "Ratebetaling ikke oprettet." + Colors.RESET);
        }
    }

    /* Viser alle betalinger for et specifikt medlem */
    public static void showPaymentsForMember() {
        System.out.println("\n=== BETALINGER FOR MEDLEM ===");
        Member member = findMember();
        if (member == null) return;

        ArrayList<Payment> memberPayments = paymentController.getPaymentsForMember(member);

        if (memberPayments.isEmpty()) {
            System.out.println(Colors.YELLOW + "Ingen betalinger fundet for " +
                    member.getName() + Colors.RESET);
            return;
        }

        System.out.println("\nBetalinger for " + member.getName() + ":");
        printPaymentList(memberPayments);
        printPaymentSummary(memberPayments);
    }

    /* Hjælpemetode til at printe en betalingsliste med farvekodning */
    private static void printPaymentList(ArrayList<Payment> payments) {
        System.out.println("\n" + "─".repeat(75));
        System.out.printf("%-20s %-15s %-12s %-15s %-10s%n",
                "Navn", "Beløb", "Forfaldsdato", "Betalingsdato", "Status");
        System.out.println("─".repeat(75));

        for (Payment p : payments) {
            // Farvekodning baseret på status
            String color;
            if (p.getStatus() == PaymentStatus.PAID) {
                color = Colors.GREEN;   // Betalt = grøn
            } else if (p.isOverdue()) {
                color = Colors.RED;     // Restance = rød
            } else {
                color = Colors.YELLOW;  // Afventer = gul
            }

            System.out.printf("%s%-20s %-15s %-12s %-15s %-10s%s%n",
                    color,
                    p.getMember().getName(),
                    String.format("%.2f kr.", p.getFee()),
                    p.getDueDate(),
                    p.getDayPaid() != null ? p.getDayPaid().toString() : "Ikke betalt",
                    p.getStatus(),
                    Colors.RESET);
        }
        System.out.println("─".repeat(75));
    }

    /* Hjælpemetode til at printe opsummering af betalinger */
    private static void printPaymentSummary(ArrayList<Payment> payments) {
        double total = 0;
        int overdueCount = 0;
        int paidCount = 0;

        for (Payment p : payments) {
            total += p.getFee();
            if (p.isOverdue()) overdueCount++;
            if (p.getStatus() == PaymentStatus.PAID) paidCount++;
        }

        System.out.printf("Total: %.2f kr. | Antal: %d | %sBetalt: %d%s | %sRestance: %d%s%n",
                total,
                payments.size(),
                Colors.GREEN, paidCount, Colors.RESET,
                Colors.RED, overdueCount, Colors.RESET);
    }

    /* Hjælpemetode til at finde et medlem via ID eller navn */
    private static Member findMember() {
        System.out.println("Søg via: 1. ID  2. Navn");
        int input = userInput.inputInt(2);

        Member member;
        if (input == 1) {
            System.out.println("Indtast medlems-ID:");
            int id = userInput.inputInt(9999);
            member = memberController.findByID(id);
        } else {
            System.out.println("Indtast navn:");
            String name = userInput.inputString();
            member = memberController.findByName(name);
        }

        if (member == null) {
            System.out.println(Colors.RED + "Ingen medlem fundet." + Colors.RESET);
            return null;
        }

        return member;
    }
}