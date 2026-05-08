package utility;

import model.Member; // packages skal rettes til små bogstave

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class MemberSorter {

    // Sorter efter navn (A-Z)
    public static void sortByName(ArrayList<Member> members) {
        members.sort(Comparator.comparing(Member::getName));
    }

    // Sorter efter alder (laveste først)
    public static void sortByAge(ArrayList<Member> members) {
        members.sort(Comparator.comparingInt(Member::getAge));
    }

    // Sorter efter kontingent (højeste beløb først)
    public static void sortByFee(ArrayList<Member> members) {
        members.sort(
                Comparator.comparingDouble(Member::getFee).reversed()
        );
    }

    // Sorter efter restance (højeste beløb først+dato????)
    public static void sortByOverdue(ArrayList<Member> members) {
        members.sort(
                Comparator.comparingDouble(Member::getOverdue).reversed()
        );
    }
}

//Main
//  ↓
//MemberController
//  ↓
//MemberService?? er det Members klasse?? kun arraylist?
//  ↓
//List<Member>
//  ↓
//MemberSorter sorterer listen