package data;

import com.github.javafaker.Faker;

public class User {

    private static final User USER = new User();
    private final String FIRSTNAME;
    private final String LASTNAME;

    private User() {
        Faker faker = new Faker();
        FIRSTNAME = faker.name()
                .firstName();
        LASTNAME = faker.name()
                .lastName();
    }

    public static User getUser(){
        return USER;
    }

    public String getFirstName() {
        return FIRSTNAME;
    }

    public String getLastName() {
        return LASTNAME;
    }
}