package Model;

public class CompetitiveMember extends Member {
    private Discipline discipline;

    public CompetitiveMember(String name, int age, int memberID, boolean activeMember, Discipline discipline) {
        super(name, age, memberID, activeMember);
        this.discipline = discipline;
    }
}
