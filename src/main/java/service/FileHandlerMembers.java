package service;
import model.CompetitiveMember;
import model.Discipline;
import model.ExerciseMember;
import model.Member;

import java.io.*;
import java.util.ArrayList;

public class FileHandlerMembers implements FileHandler {
    private static String FILE_PATH = "src/main/java/csv/members.csv";
    private ArrayList<Member> members;

    public FileHandlerMembers(ArrayList<Member> members) {
        this.members = members;
    }
    @Override
    public void loadToFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = reader.readLine()) !=null) {
                if (line.isBlank() || line.startsWith("#")) continue;
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                int memberID = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                int age = Integer.parseInt(parts[2].trim());
                boolean active = Boolean.parseBoolean(parts[3].trim());
                String type = parts[4].trim().toUpperCase();

                Member member;
                if (type.equals("KONKURRENCE") && parts.length >= 6) {
                    Discipline discipline = Discipline.valueOf(parts[5].trim().toUpperCase());
                    member = new CompetitiveMember(name, age, memberID, active, discipline);
                } else {
                    member = new ExerciseMember(name, age memberID, active);
                }

                member.setMemberID(memberID);
                members.add(member);
            }
            System.out.println("Loaded " + members.size() + " medlemmer fra " + FILE_PATH);
        }
    }
}