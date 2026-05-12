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
        assertEquals(250, new ExerciseMember("Barney", 30,0,false).calculateFee());
        assertEquals(800, new ExerciseMember("Barney", 17,0,true).calculateFee());
        assertEquals(1500 * 0.75, new ExerciseMember("Barney", 61,0,true).calculateFee());
        assertEquals(1500, new ExerciseMember("Barney", 30,0,true).calculateFee());

    }

    @Test
    void getMemberType() {
        assertEquals("PASSIV",new ExerciseMember("Jarl", 35, 1, false).getMemberType());
        assertEquals("Junior_MOTIONIST",new ExerciseMember("Jarl", 15, 1, true).getMemberType());
        assertEquals("SENIOR_MOTIONIST_RABAT",new ExerciseMember("Jarl", 99, 1, true).getMemberType());
        assertEquals("SENIOR_MOTIONIST",new ExerciseMember("Jarl", 25, 1, true).getMemberType());
    }

}