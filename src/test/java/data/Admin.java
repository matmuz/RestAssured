package data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Admin {

    private String username;
    private String password;

    public static Admin get(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filename), Admin.class);
    }

    @JsonProperty("username")
    public String getUsername() {
        return this.username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return this.password;
    }
}
