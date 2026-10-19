/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.util.regex.Pattern;

// Login stores one user's registration details and checks username format,
// password complexity, South African cell numbers, and login authentication.
/**
 *
 * @author ST10534768
 */
public class Login {

    // Details stored when the user registers
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // Details typed in when the user tries to log in
    private String enteredUsername;
    private String enteredPassword;

    // Cell phone regular expression compiled once for reuse.
    // Adapted from: Baeldung. 2024. How to Validate Phone Numbers in Java.
    // Available at: https://www.baeldung.com/java-regex-validate-phone-numbers
    // [Accessed 1 September 2026].
    // Pattern syntax from: Oracle. n.d. Regular Expressions. The Java Tutorials.
    // Available at: https://docs.oracle.com/javase/tutorial/essential/regex/
    // [Accessed 1 September 2026].
    // The pattern requires the South African country code (+27) followed by
    // a subscriber number that is no more than ten digits long.
    private static final Pattern SOUTH_AFRICAN_CELL_PATTERN = Pattern.compile("^\\+27[0-9]{1,10}$");

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public void setEnteredUsername(String enteredUsername) {
        this.enteredUsername = enteredUsername;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }

    // Returns true when the username contains an underscore and is no more than five characters long
    public boolean checkUserName() {
        if (username == null) {
            return false;
        }

        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    // Returns true when the password is at least eight characters and has a capital letter, a number, and a special character
    public boolean checkPasswordComplexity() {
        if (password == null) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                hasCapital = true;
            } else if (Character.isDigit(currentChar)) {
                hasNumber = true;
            } else if (Character.isLetterOrDigit(currentChar) == false) {
                hasSpecial = true;
            }
        }

        if (hasCapital == true && hasNumber == true && hasSpecial == true) {
            return true;
        } else {
            return false;
        }
    }

    // Returns true when the cell number matches the attributed South African regex
    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber == null) {
            return false;
        }

        if (SOUTH_AFRICAN_CELL_PATTERN.matcher(cellPhoneNumber).matches()) {
            return true;
        } else {
            return false;
        }
    }

    // Returns the username confirmation or error message
    public String usernameMessage() {
        if (checkUserName() == true) {
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
    }

    // Returns the password confirmation or error message
    public String passwordMessage() {
        if (checkPasswordComplexity() == true) {
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
    }

    // Returns the cell phone confirmation or error message
    public String cellPhoneMessage() {
        if (checkCellPhoneNumber() == true) {
            return "Cell number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
    }

    // Returns registration messaging for a bad username, a bad password, a bad cell number, or a successful registration
    public String registerUser() {
        if (checkUserName() == false) {
            return usernameMessage();
        }

        if (checkPasswordComplexity() == false) {
            return passwordMessage();
        }

        if (checkCellPhoneNumber() == false) {
            return cellPhoneMessage();
        }

        return "User registered successfully.";
    }

    // Returns true only when the login username and password match the registered details
    public boolean loginUser() {
        if (username == null || password == null || enteredUsername == null || enteredPassword == null) {
            return false;
        }

        if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
            return true;
        } else {
            return false;
        }
    }

    // Returns the login confirmation message or the login error message
    public String returnLoginStatus() {
        if (loginUser() == true) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
