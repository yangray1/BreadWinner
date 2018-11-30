package com.example.crystalyip.csc301.Model;

public class Profile {
    private int clientId;
    private String password;
    private String fName;
    private String lName;
    private String about;
    public Profile(int cliendId, String password, String fName, String lName, String about){
        this.clientId=cliendId;
        this.password=password;
        this.fName=fName;
        this.lName=lName;
        this.about=about;
    }


    public int getClientId(){
        return this.clientId;
    }

    public String getPassword(){
        return this.password;
    }

    public String getfName(){
        return this.fName;
    }

    public String getlName(){
        return this.lName;
    }

    public String getAbout(){
        return this.about;
    }
}
