package service;
import controller.MemberController;
import controller.PaymentController;
import model.Member;
import model.Payment;
import model.PaymentStatus;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class FileHandlerPayments implements FileHandler {
    private static final String FILE_PATH = "src/main/java/csv/payments/payments.csv";
    private static final String DELIMITER = ";";
    private PaymentController paymentController;
    private MemberController memberController;

    public FileHandlerPayments(PaymentController paymentController, MemberController memberController) {
        this.paymentController = paymentController;
        this.memberController = memberController;
    }
    @Override
    public void loadFromFile() throws IOException {
        long startTime = System.nanoTime();

        File file = new File(FILE_PATH);
        if(!file.exists()) {
            System.out.println("payments.csv ikke fundet - starter med tom liste.");
            return;
        }
        int loaded = 0;
        int errors = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
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
        }
        long elapsed = System.nanoTime() - startTime;
        System.out.printf("Loaded %d betalinger (%d fejl) på %.2f ms (buffered)%n", loaded, errors, elapsed / 1_000_000.0);
    }

    private Payment parseLine(String line) {
        String[] parts = line.split(DELIMITER, -1);

        if (parts.length < 5) {
            throw new IllegalArgumentException("For få kolonner (" + parts.length + ")");

        }
        int memberID = Integer.parseInt(parts[0].trim());
        PaymentStatus status = PaymentStatus.valueOf(parts[1].trim().toUpperCase());
        LocalDate dueDate = LocalDate.parse(parts [2].trim());

        Member member = memberController.findByID(memberID);
        if (member == null) {
            throw new IllegalArgumentException("Ingen medlem fundet med ID: " + memberID);
        }
        Payment payment = new Payment(dueDate, status, member);

        if (!parts[4].isBlank()) {
            payment.setDayPaid(LocalDate.parse(parts[4].trim()));
        }
        return payment;
    }
}
