package controller;

import model.CompetitiveMember;
import model.Discipline;
import model.Member;
import model.ExerciseMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MemberControllerTest {

        // En frisk MemberController oprettes før HVER enkelt test
        // Vi behøver ikke at lave MemberController = new MemberCortroller i hver test.
        //  + så testene ikke påvirker hinanden

    private MemberController controller;

        @BeforeEach
        void setUp() {
            controller = new MemberController();
        }

    @Test
    void addMember() {
            Member m0 = new ExerciseMember("Anna Jensen", 16, 10001, true);
            Member m1 = new ExerciseMember("Anna Jensen", 17, 10002, false);
            Member m = new CompetitiveMember("Mathias Jensen", 24, 10003, true, Discipline.MIX_DOUBLE);
            controller.addMember(m0);
            controller.addMember(m1);
            controller.addMember(m);

            assertEquals(3, controller.size()); // Listen skal indeholde præcis 3 medlem efter tilføjelse - testen fejler hvis du laver 2 medlemmer
        }
        // Tester at medlemmer bliver tilføjet korrekt til listen

    @Test
    void removeMemberByID() {

    }

    @Test
    void removeMemberByName() {
    }

    @Test
    void findByID() {
    }

    @Test
    void findByName() {
    }

    @Test
    void sortByName() {
    }

    @Test
    void sortByAge() {
    }

    @Test
    void printAllMembers() {
    }

    @Test
    void generateID() {
    }

    @Test
    void getAll() {
    }

    @Test
    void size() {
    }
}