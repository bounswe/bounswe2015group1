package bounswegroup1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String username;

    private String password;

    public User(){

    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @JsonProperty
    public String getUsername(){
        return username;
    }

    @JsonProperty
    public String getPassword(){
        return password;
    }
}
