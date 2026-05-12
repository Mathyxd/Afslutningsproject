package model;

import static validation.InputValidator.*;

public abstract class Member {
    // attributor
    private String name;
    private int age;
    private int memberID;
    private boolean activeMember;
    private double fee;

    //konstruktør
    public Member(String name, int age, int memberID, boolean activeMember) {
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
        this.memberID = memberID;
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

    public void setFee(double fee) {
        this.fee = fee;
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

