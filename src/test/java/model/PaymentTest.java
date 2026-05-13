package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Member member;
    private Payment payment;

    @BeforeEach
    void setUp() {
        member = new Member("Anders Jensen", 25, 1, true) {
            @Override
            public String getMemberType() {
                return "Senior";
            }
        };
        payment = new Payment(LocalDate.now().minusDays(1), PaymentStatus.PENDING, member);
    }

    // --- isOverdue ---
    // Testes grundigt da det er kernelogik i Payment klassen

    @Test
    void isOverdueWhenPendingAndDatePassed() {
        // PENDING + overskredet dato = overdue
        assertTrue(payment.isOverdue());
    }

    @Test
    void isNotOverdueWhenPaid() {
        // PAID + overskredet dato = ikke overdue
        payment.setDayPaid(LocalDate.now());
        assertFalse(payment.isOverdue());
    }

    @Test
    void isNotOverdueWhenDateIsInFuture() {
        // PENDING + fremtidig dato = ikke overdue
        Payment futurePayment = new Payment(LocalDate.now().plusDays(1), PaymentStatus.PENDING, member);
        assertFalse(futurePayment.isOverdue());
    }

    // --- Fee beregning ---
    // Testes grundigt da det er vigtig forretningslogik

    @Test
    void seniorMemberFeeIsCorrect() {
        assertEquals(1500, payment.getFee());
    }

    @Test
    void juniorMemberFeeIsCorrect() {
        Member juniorMember = new Member("Sofie Hansen", 15, 2, true) {
            @Override
            public String getMemberType() {
                return "Junior";
            }
        };
        Payment juniorPayment = new Payment(LocalDate.now().plusDays(30), PaymentStatus.PENDING, juniorMember);
        assertEquals(800, juniorPayment.getFee());
    }

    @Test
    void passiveMemberFeeIsCorrect() {
        Member passiveMember = new Member("Lars Nielsen", 40, 3, false) {
            @Override
            public String getMemberType() {
                return "Passive";
            }
        };
        Payment passivePayment = new Payment(LocalDate.now().plusDays(30), PaymentStatus.PENDING, passiveMember);
        assertEquals(250, passivePayment.getFee());
    }

    @Test
    void seniorOver60GetsDiscountFee() {
        Member seniorMember = new Member("Bent Olsen", 65, 4, true) {
            @Override
            public String getMemberType() {
                return "Senior";
            }
        };
        Payment seniorPayment = new Payment(LocalDate.now().plusDays(30), PaymentStatus.PENDING, seniorMember);
        assertEquals(1500 * 0.75, seniorPayment.getFee());
    }

    // --- setDayPaid ---
    // Testes da den indeholder logik der opdaterer status automatisk

    @Test
    void dayPaidIsNullOnCreation() {
        assertNull(payment.getDayPaid());
    }

    @Test
    void setDayPaidAlsoSetsStatusToPaid() {
        payment.setDayPaid(LocalDate.now());
        assertEquals(LocalDate.now(), payment.getDayPaid());
        assertEquals(PaymentStatus.PAID, payment.getStatus());
    }

    // --- Status ---
    // Testes da startstatus er vigtig for overdue logikken

    @Test
    void statusIsPendingOnCreation() {
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
    }

    // --- setFee ---
    // Testes da den bruges i createInstallmentPayments til at overskrive beløbet

    @Test
    void setFeeUpdatesCorrectly() {
        payment.setFee(500);
        assertEquals(500, payment.getFee());
    }
}