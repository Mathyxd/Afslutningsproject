package model;

import java.util.EnumSet;

public class CompetitiveMember extends Member {
    // private Discipline discipline;
    private EnumSet<Discipline> disciplines;

    public CompetitiveMember(String name, int age, int memberID, boolean activeMember, EnumSet<Discipline> disciplines) {
        super(name, age, memberID, activeMember);
        this.disciplines = disciplines;
    }
    public EnumSet<Discipline> getDisciplines() {return disciplines;}
    public void setDisciplines(EnumSet<Discipline> disciplines) {this.disciplines = disciplines;}


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
