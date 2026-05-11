package model;

public class CompetitiveMember extends Member {
    private Discipline discipline;

    public CompetitiveMember(String name, int age, int memberID, boolean activeMember, Discipline discipline) {
        super(name, age, memberID, activeMember);
        this.discipline = discipline;
    }
    public Discipline getDiscipline() {return discipline;}
    public void setDiscipline(Discipline discipline) {this.discipline = discipline;}


    @Override
    public String getMemberType() {
        if (!isActiveMember()) {
            return "PASSIV";
        } else if (isJunior()) {
            return "JUNIOR-KONKURRENCE";
        } else if (isSeniorOld()) {
            return "SENIOR KONKURRENCE-RABAT";
        } else {
            return "SENIOR-KONKURRENCE";
        }
    }
}
