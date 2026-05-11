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
            Member m0 = new ExerciseMember("Anna Jensen", 16, 0, true);
            Member m1 = new ExerciseMember("Anna Jensen", 17, 0, false);
            Member m = new CompetitiveMember("Mathias Jensen", 24, 0, true, Discipline.MIX_DOUBLE);
            controller.addMember(m0);
            controller.addMember(m1);
            controller.addMember(m);

            assertEquals(3, controller.size()); // Listen skal indeholde præcis 3 medlem efter tilføjelse - testen fejler hvis du laver 2 medlemmer
        }
        // Tester at medlemmer bliver tilføjet korrekt til listen

    @Test
    void removeMemberByID() { // Tester at et eksisterende medlem kan fjernes via sit ID
        Member m = new ExerciseMember("Anna Jensen", 16, 1000, true);
        controller.addMember(m);
        int id = m.getMemberID();

        controller.removeMemberByID(id);

        assertEquals(0, controller.size());  // Listen skal være tom efter fjernelse
    }

    @Test
        // Tester at et ugyldigt ID ikke kaster en exception eller crasher programmet
    void removeMemberByID_invalidID() {
        assertDoesNotThrow(() -> controller.removeMemberByID(9999));
    }

    @Test
    void removeMemberByName() {
        // Tester at et eksisterende medlem kan fjernes via sit navn
        controller.addMember(new ExerciseMember("Anna Jensen", 16, 1000, true));

        controller.removeMemberByName("Anna Jensen");

        // Listen skal være tom efter fjernelse
        assertEquals(0, controller.size());
    }

    @Test
    void findByID() {
        Member m = new ExerciseMember("Peter Madsen", 34, 1000, false);
        controller.addMember(m);
        int id = m.getMemberID();

        Member found = controller.findByID(id);

        // Det fundne medlem skal være det samme som det tilføjede
        assertNotNull(found);
        assertEquals("Peter Madsen", found.getName());
    }

    @Test
    void findByID_notFound() { // Tester at findByID returnerer null når ID ikke findes
        Member found = controller.findByID(9999);
        assertNull(found);
    }

    @Test
    void findByName() {
        controller.addMember(new ExerciseMember("Lone Christensen", 67, 2000, false));

        Member found = controller.findByName("Lone Christensen");

        assertNotNull(found);
        assertEquals("Lone Christensen", found.getName());
    }

    @Test
    void findByName_caseInsensitive() { // Tester at findByName ignorerer store/små bogstaver
        controller.addMember(new ExerciseMember("Lone Christensen", 67, 2000, false));

        Member found = controller.findByName("lone christensen");

        assertNotNull(found);
    }

    @Test
    void printAllMembers() {
        assertDoesNotThrow(() -> controller.printAllMembers());

    // Tester at printAllMembers ikke kaster en exception når der er medlemmer
        controller.addMember(new ExerciseMember("Anna", 34, 0, true));
        assertDoesNotThrow(() -> controller.printAllMembers());
    }

/*  Senere tests måske?

    @Test
    void generateID() {
    }

    @Test  -
    void getAll() {
    }

    @Test  - For at se at programmet korrekt viser hvor mange medlemmer der er i klubben
    void size() {
    }*/
}