package model;

import static controller.MemberController.generateID;
import static validation.InputValidator.*;

public abstract class Member implements Comparable<Member> {
    // attributor
    private String name;
    private int age;
    private int memberID;
    private boolean activeMember;
    private double fee;

    /**
     * Sammenligner dette medlem med et andet medlem alfabetisk efter navn.
     * Bruges til sortering af betalinger efter medlemsnavn i PaymentSorter.
     *
     * @param other medlemmet der sammenlignes med
     * @return et negativt tal hvis dette members navn kommer før det andet, ->
     * ...nul hvis navnene er ens,
     * ...et positivt tal hvis dette members navn kommer efter det andet
     */
    @Override
    public int compareTo (Member other){
        return this.name.compareTo(other.name);
    }

    //konstruktør
    public Member(String name, int age, int memberID, boolean activeMember) {
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
        this.memberID = generateID();
        this.activeMember = activeMember;
        this.fee = calculateFee();
    }

    //getter og setter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getMemberID() {
        return memberID;
    }

    public double getFee() {
        return fee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    //metoder
    public boolean isActiveMember() {
        return activeMember;
    }

    public void setActiveMember(boolean activeMember) {
        this.activeMember = activeMember;
    }

    public boolean isJunior() {
        return age < 18;
    }

    public boolean isSeniorOld() {
        return age > 60;
    }

    // boolean som beregner fee for spillere
    public double calculateFee() {
        if (!isActiveMember()) {
            return 250;
        } else if (isJunior()) {
            return 800;
        } else if (isSeniorOld()) {
            return 1500 * 0.75;
        } else {
            return 1500;
        }
    }

    public abstract String getMemberType();

    @Override
    public String toString() {
        return memberID + " - " + name + " - " + age + " år - " + getMemberType() + " - " + fee + " kr.";
    }
}

