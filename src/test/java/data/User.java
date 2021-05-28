package data;

import com.github.javafaker.Faker;

public class User {

    private static final User user = new User();
    private final String firstName;
    private final String lastName;

    private User() {
        Faker faker = new Faker();
        firstName = faker.name()
                .firstName();
        lastName = faker.name()
                .lastName();
    }

    public static User getUser(){
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}