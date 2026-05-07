package Model;

public class ExerciseMember extends Member {
    public ExerciseMember(String name, int age, int memberID, boolean activeMember) {
        super(name, age, memberID, activeMember);
    }
    @Override
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
}
