package user;

import com.github.javafaker.Faker;

public final class User {

    private static User user;
    private final String firstName;
    private final String lastName;

    private User() {
        Faker faker = new Faker();
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
    }

    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}