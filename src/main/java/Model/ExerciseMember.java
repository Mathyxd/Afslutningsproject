package Model;

public class ExerciseMember extends Member {
    public ExerciseMember(String name, int age, int memberID, boolean activeMember) {
        super(name, age, memberID, activeMember);
    }
    @Override
    public String getMemberType() {
        if (!isActiveMember()) {
            return "PASSIV";
        } else if (isJunior()) {
            return "Junior_MOTIONIST";
        } else if (isSeniorOld()) {
            return "SENIOR_MOTIONIST_RABAT";
        } else {
            return "SENIOR_MOTIONIST";
        }

    }
}

