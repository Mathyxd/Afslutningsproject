package utility;

import model.ExerciseMember;
import model.Member;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MemberSorterTest {

    @Test
    void sortByName() {
        // MemberSorter memberSorter = new MemberSorter();
        ExerciseMember A = new ExerciseMember("Anton",34, 7, false);
        ExerciseMember B = new ExerciseMember("Celia",30, 8, false);
        ExerciseMember C = new ExerciseMember("Tatiana",45, 22, false);
        ArrayList<Member> testArrayList = new ArrayList<>();
        testArrayList.add(B);
        testArrayList.add(A);
        testArrayList.add(C);
        MemberSorter.sortByName(testArrayList);//MemberSorter er den klasse hvor vi har metoden vi kalder og tester
        assertEquals("Celia", testArrayList.get(1).getName());
    }

    @Test
    void sortByAge() {
        ExerciseMember A = new ExerciseMember("Anton",34, 7, false);
        ExerciseMember B = new ExerciseMember("Celia",30, 8, false);
        ExerciseMember C = new ExerciseMember("Tatiana",45, 22, false);
        ArrayList<Member> testArrayList = new ArrayList<>();
        testArrayList.add(B);
        testArrayList.add(A);
        testArrayList.add(C);
        MemberSorter.sortByName(testArrayList);
        assertEquals(30, testArrayList.get(1).getAge());
    }

    @Test
    void sortByFee() {
    }
}