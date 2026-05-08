package Model;

import Validation.InputValidator;

import static Validation.InputValidator.*;

public class Member {
    String name;
    int age;

    public Member(String name, int age){
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
    }
}
