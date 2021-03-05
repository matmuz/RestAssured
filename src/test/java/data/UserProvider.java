package data;

import com.github.javafaker.Faker;

public class UserProvider {

    private String firstName;
    private String lastName;
    private Faker faker = new Faker();

    public UserProvider() {
        this.firstName = faker.name()
                .firstName();
        this.lastName = faker.name()
                .lastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}