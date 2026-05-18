package utility;

import model.Member; // packages skal rettes til små bogstave

//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
/**
 * Utility-klasse til sortering af medlemslister.
 * <p>
 * Klassen indeholder statiske metoder, som kan sortere en liste af
 * klubbens Members, dvs. objekter efter forskellige kriterier som navn,
 * alder og kontingent.
 * </p>
 */
public class MemberSorter {
    /**
     * Sorterer medlemmer alfabetisk efter navn (A-Z).
     *
     * @param members listen af medlemmer, der skal sorteres
     */
    // Sorter efter navn (A-Z)
    public static void sortByName(ArrayList<Member> members) {
        members.sort(Comparator.comparing(Member::getName));
    }
    /**
     * Sorterer medlemmer efter alder med yngste medlem først.
     *
     * @param members listen af medlemmer, der skal sorteres
     */
    // Sorter efter alder (laveste først)
    public static void sortByAge(ArrayList<Member> members) {
        members.sort(Comparator.comparingInt(Member::getAge));
    }

    /**
     * Sorterer medlemmer efter kontingent med højeste beløb først.
     *
     * @param members listen af medlemmer, der skal sorteres
     */
    // Sorter efter kontingent (højeste beløb først)
    public static void sortByFee(ArrayList<Member> members) {
        members.sort(
                Comparator.comparingDouble(Member::getFee).reversed()
        );
    }

    /* Sorter efter restance (højeste beløb først+dato????) skal det være here eller i paymentsorter???
    public static void sortByOverdue(ArrayList<Member> members) {
        members.sort(
                Comparator.comparingDouble(Member::getOverdue).reversed()
        );
    }*/
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