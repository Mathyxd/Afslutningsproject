package Model;

public class CompetitiveMember extends Member {
    private Discipline discipline;

    public CompetitiveMember(String name, int age, int memberID, boolean activeMember, Discipline discipline) {
        super(name, age, memberID, activeMember);
        this.discipline = discipline;
    }

    @Override
    public String getMemberType() {
        if (!isActiveMember()) {
            return "PASSIV";
        } else if (isJunior()) {
            return "JUNIOR_KONKURRENCE";
        } else if (isSeniorOld()) {
            return "SENIOR_KONKURRENCE_RABAT";
        } else {
            return "SENIOR_KONKURRENCE";
        }
    }
}
