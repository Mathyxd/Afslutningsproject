package service;

import controller.MemberController;
import controller.PaymentController;
import model.Member;
import model.Payment;
import model.PaymentStatus;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileHandlerPayments implements FileHandler {

    private static final String FILE_PATH = "csv/payments.csv";
    private static final String DELIMITER = ";";
    private PaymentController paymentController;
    private MemberController memberController;

    public FileHandlerPayments(PaymentController paymentController, MemberController memberController) {
        this.paymentController = paymentController;
        this.memberController = memberController;
    }

    // Læser alle betalinger fra CSV-filen ved opstart
    public void loadFromFile() {
        long startTime = System.nanoTime();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("payments.csv ikke fundet – starter med tom liste.");
            return;
        }

        int loaded = 0;
        int errors = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                // Spring header-linjen over
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.isBlank()) continue;

                try {
                    Payment payment = parseLine(line);
                    paymentController.getAllPayments().add(payment);
                    loaded++;
                } catch (Exception e) {
                    System.out.println("Fejl på linje: \"" + line + "\" -> " + e.getMessage());
                    errors++;
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke indlæse payments.csv: " + e.getMessage());
        }

        long elapsed = System.nanoTime() - startTime;
        System.out.printf("Loaded %d betalinger (%d fejl) på %.2f ms (buffered)%n", loaded, errors, elapsed / 1_000_000.0);
    }

    // Omdanner én CSV-linje til et Payment-objekt
    private Payment parseLine(String line) {
        String[] parts = line.split(DELIMITER, -1);

        if (parts.length < 4) {
            throw new IllegalArgumentException("For få kolonner (" + parts.length + ")");
        }

        int memberID         = Integer.parseInt(parts[0].trim());
        PaymentStatus status = PaymentStatus.valueOf(parts[1].trim().toUpperCase());
        LocalDate dueDate    = LocalDate.parse(parts[2].trim());

        // Slå medlemmet op — fee beregnes automatisk i Payment konstruktøren
        Member member = memberController.findByID(memberID);
        if (member == null) {
            throw new IllegalArgumentException("Ingen medlem fundet med ID: " + memberID);
        }

        Payment payment = new Payment(dueDate, status, member);

        // dayPaid er kun sat hvis betalingen er gennemført
        if (!parts[3].isBlank()) {
            payment.setDayPaid(LocalDate.parse(parts[3].trim()));
        }

        return payment;
    }

    // Gemmer alle betalinger til CSV-filen
    public void saveToFile() {
        long startTime = System.nanoTime();

        File file = new File(FILE_PATH);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        ArrayList<Payment> payments = paymentController.getAllPayments();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Skriv header-linjen først
            writer.write("memberID" + DELIMITER + "status" + DELIMITER + "dueDate" + DELIMITER + "dayPaid");
            writer.newLine();

            // Skriv én betaling per linje
            for (Payment p : payments) {
                writer.write(formatPayment(p));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke gemme payments.csv: " + e.getMessage());
        }

        long elapsed = System.nanoTime() - startTime;
        System.out.printf("Gemt %d betalinger på %.2f ms (buffered)%n", payments.size(), elapsed / 1_000_000.0);
    }

    // Omdanner et Payment-objekt til en CSV-linje
    private String formatPayment(Payment p) {
        // dayPaid kan være null hvis betalingen ikke er betalt endnu
        String dayPaid = (p.getDayPaid() != null) ? p.getDayPaid().toString() : "";

        return p.getMember().getMemberID() + DELIMITER
                + p.getStatus()            + DELIMITER
                + p.getDueDate()           + DELIMITER
                + dayPaid;
    }
}