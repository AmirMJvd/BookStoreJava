package model;

public class SharedData {
    private static SharedData instance = new SharedData();

    private String username;

    private SharedData() {}

    public static SharedData getInstance() {
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
