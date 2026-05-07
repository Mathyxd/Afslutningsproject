package Model;

public abstract class Member {
    private String name;
    private int age;
    private int memberID;
    private boolean activeMember;
    private double fee;

    public Member(String name, int age, int memberID, boolean activeMember) {
        this.name = name;
        this.age = age;
        this.memberID = memberID;
        this.activeMember = activeMember;
        this.fee = fee;
    }
    public String getName() {return name;}
    public int getAge() {return age;}
    public int getMemberID() {return memberID;}
    public double getFee() {return fee;}
    public void setName(String name) {this.name = name;}
    public void setAge(int age) {this.age = age;}
    public void setMemberID(int memberID) {this.memberID = memberID;}
    public void setFee(double fee) {this.fee = fee;}

    public boolean isActiveMember() { return activeMember;}

    public void setActiveMember (boolean activeMember) {this.activeMember = activeMember;}

    public boolean isJunior() { return age < 18;}

    public boolean isSeniorOld() { return age > 60;}


    public abstract double calculateFee();
}
