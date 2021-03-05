package data;

import com.github.javafaker.Faker;

public class UserProvider {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Faker faker = new Faker();

    public UserProvider() {
        this.firstName = faker.name()
                .firstName();
        this.lastName = faker.name()
                .lastName();
        this.username = firstName + lastName;
        this.password = faker.phoneNumber()
                .toString();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}