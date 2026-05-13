package controller;

import model.Member;
import model.Payment;
import model.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PaymentControllerTest {

    private PaymentController controller;
    private Member member;

    @BeforeEach
    void setUp() {
        controller = new PaymentController();
        member = new Member("Anders Jensen", 25, 1, true) {
            @Override
            public String getMemberType() {
                return "Senior";
            }
        };
    }

    // --- createPayment ---
    // Testes da det er primær måde at oprette betalinger på

    @Test
    void createPaymentAddsToList() {
        controller.createPayment(member, LocalDate.now().plusDays(30));
        assertEquals(1, controller.getAllPayments().size());
    }

    @Test
    void createPaymentHasPendingStatus() {
        Payment payment = controller.createPayment(member, LocalDate.now().plusDays(30));
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
    }

    @Test
    void createPaymentHasCorrectMember() {
        Payment payment = controller.createPayment(member, LocalDate.now().plusDays(30));
        assertEquals(member, payment.getMember());
    }

    // --- createInstallmentPayments ---
    // Testes grundigt da den indeholder kompleks logik med datoer og beløb

    @Test
    void createInstallmentPaymentsCreatesThreePayments() {
        controller.createInstallmentPayments(member, LocalDate.now());
        assertEquals(3, controller.getAllPayments().size());
    }

    @Test
    void createInstallmentPaymentsHaveCorrectDueDates() {
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        controller.createInstallmentPayments(member, startDate);

        ArrayList<Payment> installments = controller.getAllPayments();
        assertEquals(LocalDate.of(2026, 1, 1), installments.get(0).getDueDate());
        assertEquals(LocalDate.of(2026, 5, 1), installments.get(1).getDueDate());
        assertEquals(LocalDate.of(2026, 9, 1), installments.get(2).getDueDate());
    }

    @Test
    void createInstallmentPaymentsHaveCorrectFee() {
        controller.createInstallmentPayments(member, LocalDate.now());
        double expectedFee = 1500.0 / 3;

        for (Payment payment : controller.getAllPayments()) {
            assertEquals(expectedFee, payment.getFee());
        }
    }

    // --- registerPayment ---
    // Testes da den opdaterer status og dayPaid samtidigt

    @Test
    void registerPaymentSetsStatusToPaid() {
        Payment payment = controller.createPayment(member, LocalDate.now().plusDays(30));
        controller.registerPayment(payment);
        assertEquals(PaymentStatus.PAID, payment.getStatus());
    }

    @Test
    void registerPaymentSetsDayPaidToToday() {
        Payment payment = controller.createPayment(member, LocalDate.now().plusDays(30));
        controller.registerPayment(payment);
        assertEquals(LocalDate.now(), payment.getDayPaid());
    }

    // --- getOverduePayments ---
    // Testes grundigt da kassereren er afhængig af denne oversigt

    @Test
    void getOverduePaymentsReturnsOnlyOverdue() {
        controller.createPayment(member, LocalDate.now().minusDays(1));
        controller.createPayment(member, LocalDate.now().plusDays(30));
        assertEquals(1, controller.getOverduePayments().size());
    }

    @Test
    void getOverduePaymentsIsEmptyWhenAllPaid() {
        Payment payment = controller.createPayment(member, LocalDate.now().minusDays(1));
        controller.registerPayment(payment);
        assertEquals(0, controller.getOverduePayments().size());
    }

    @Test
    void getOverduePaymentsIsEmptyWhenNoPayments() {
        assertEquals(0, controller.getOverduePayments().size());
    }

    // --- getAllPayments ---
    // Testes da kassereren bruger den til fuld betalingsoversigt

    @Test
    void getAllPaymentsIsEmptyInitially() {
        assertEquals(0, controller.getAllPayments().size());
    }

    @Test
    void getAllPaymentsReturnsAllPayments() {
        controller.createPayment(member, LocalDate.now().plusDays(30));
        controller.createPayment(member, LocalDate.now().plusDays(60));
        assertEquals(2, controller.getAllPayments().size());
    }
}