package com.android.flexability;

public class User {
    private final int id;
    private final String username;
    private final String password;
    private final String category;

    public User(int id, String username, String password, String category){
        this.id = id;
        this.username = username;
        this.password = password;
        this.category = category;
    }

    public String getUsername(){return this.username;}

    public String getPassword(){return this.password;}

    public String getCategory(){return this.category;}

    public int getId(){return this.id;}
}
