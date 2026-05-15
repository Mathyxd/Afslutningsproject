package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Dækker: oprettelse af betalinger, ratebetaling, registrering, sortering og hentning af restancer
class PaymentControllerTest {

    private PaymentController controller;
    private Member member;
    private Member juniorMember;

    @BeforeEach
    void setUp() {
        controller = new PaymentController();
        member = new Member("Anders Jensen", 25, 1, true) {
            @Override
            public String getMemberType() {
                return "Senior";
            }
        };
        juniorMember = new Member("Sofie Hansen", 15, 2, true) {
            @Override
            public String getMemberType() {
                return "Junior";
            }
        };
    }

    // --- createPayment ---

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

    // --- getOverduePaymentsSortedByFee ---

    @Test
    void getOverduePaymentsSortedByFeeReturnsSortedList() {
        // Senior betaler mere end junior - junior skal komme først
        controller.createPayment(member, LocalDate.now().minusDays(1));
        controller.createPayment(juniorMember, LocalDate.now().minusDays(1));

        ArrayList<Payment> sorted = controller.getOverduePaymentsSortedByFee();
        assertTrue(sorted.get(0).getFee() <= sorted.get(1).getFee());
    }

    // --- getOverduePaymentsSortedByName ---

    @Test
    void getOverduePaymentsSortedByNameReturnsSortedList() {
        // Anders kommer før Sofie alfabetisk
        controller.createPayment(member, LocalDate.now().minusDays(1));
        controller.createPayment(juniorMember, LocalDate.now().minusDays(1));

        ArrayList<Payment> sorted = controller.getOverduePaymentsSortedByName();
        assertTrue(sorted.get(0).getMember().getName()
                .compareTo(sorted.get(1).getMember().getName()) <= 0);
    }

    // --- getAllPaymentsSortedByFee ---

    @Test
    void getAllPaymentsSortedByFeeReturnsSortedList() {
        // Senior betaler mere end junior - junior skal komme først
        controller.createPayment(member, LocalDate.now().plusDays(30));
        controller.createPayment(juniorMember, LocalDate.now().plusDays(30));

        ArrayList<Payment> sorted = controller.getAllPaymentsSortedByFee();
        assertTrue(sorted.get(0).getFee() <= sorted.get(1).getFee());
    }

    // --- getAllPaymentsSortedByDate ---

    @Test
    void getAllPaymentsSortedByDateReturnsSortedList() {
        // Ældste dato skal komme først
        controller.createPayment(member, LocalDate.now().plusDays(60));
        controller.createPayment(juniorMember, LocalDate.now().plusDays(30));

        ArrayList<Payment> sorted = controller.getAllPaymentsSortedByDate();
        assertTrue(sorted.get(0).getDueDate().isBefore(sorted.get(1).getDueDate()));
    }

    // --- getPaymentsForMember ---

    @Test
    void getPaymentsForMemberReturnsOnlyMembersPayments() {
        controller.createPayment(member, LocalDate.now().plusDays(30));
        controller.createPayment(juniorMember, LocalDate.now().plusDays(30));

        assertEquals(1, controller.getPaymentsForMember(member).size());
        assertEquals(1, controller.getPaymentsForMember(juniorMember).size());
    }

    @Test
    void getPaymentsForMemberReturnsEmptyListWhenNoPayments() {
        assertEquals(0, controller.getPaymentsForMember(member).size());
    }

    // --- getAllPayments ---

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