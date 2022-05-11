/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pidev.entities;

/**
 *
 * @author eya
 */
public final class UserSession {
    
    private static UserSession instance;
    public static int userID;
    public static String userName;
    public static String userRole;

    public UserSession(int id, String userName, String role) {
        UserSession.userID = id;
        UserSession.userName = userName;
        UserSession.userRole = role;
    }
    
    
    public UserSession(String userName, String role) {
        UserSession.userName = userName;
        UserSession.userRole = role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        UserSession.userID = userID;
    }
    
    
    
    public static UserSession getInstance(int id,String userName, String role) {
        if(instance == null) {
            instance = new UserSession(id,userName, role);
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void cleanUserSession() {
        userName = "";
        userRole = null;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + userName + '\'' +
                ", role=" + userRole +
                '}';
    }
    
}
