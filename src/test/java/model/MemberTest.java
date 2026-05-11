package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void isActiveMember() {
        assertTrue(new ExerciseMember("Sebastian", 40, 5029, true).isActiveMember());
        assertFalse(new ExerciseMember("Sebastian", 40, 5029, false).isActiveMember());
    }

    @Test
    void setActiveMember() {
        ExerciseMember member = new ExerciseMember("Sebastian", 40, 5029, true);
        member.setActiveMember(false);
        assertFalse(member.isActiveMember());
    }

    //Tester aldersgrænsen ud fra om de er junior eller ej
    @Test
    void isJunior() {
        assertTrue(new ExerciseMember("Konrad", 17, 1508, true).isJunior());
        assertFalse(new ExerciseMember("Konrad", 18, 1508, false).isJunior());
    }

    @Test
    void isSeniorOld() {
        assertTrue(new ExerciseMember("Hans", 61, 67, true).isSeniorOld());
        assertFalse(new ExerciseMember("Hans", 60, 67, false).isSeniorOld());
    }

    @Test
    void calculateFee() {
    }

    @Test
    void getMemberType() {
    }

}