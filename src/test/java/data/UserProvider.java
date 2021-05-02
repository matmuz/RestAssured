package data;

import com.github.javafaker.Faker;

public class UserProvider {

    private static final UserProvider USER = new UserProvider();
    private final String FIRSTNAME;
    private final String LASTNAME;

    private UserProvider() {
        Faker faker = new Faker();
        FIRSTNAME = faker.name()
                .firstName();
        LASTNAME = faker.name()
                .lastName();
    }

    public static UserProvider getUser(){
        return USER;
    }

    public String getFirstName() {
        return FIRSTNAME;
    }

    public String getLastName() {
        return LASTNAME;
    }
}