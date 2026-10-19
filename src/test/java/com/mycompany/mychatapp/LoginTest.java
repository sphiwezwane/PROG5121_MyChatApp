/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// LoginTest checks username format, password complexity, cell number regex,
// registration messages, and login authentication with the assignment test data.
/**
 *
 * @author ST10534768
 */
public class LoginTest {

    // Builds a Login object with valid registration details used by several tests
    private Login createRegisteredUser() {
        Login login = new Login();
        login.setFirstName("Kyle");
        login.setLastName("Smith");
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");
        return login;
    }

    @Test
    public void testUsernameCorrectlyFormattedMessage() {
        Login login = createRegisteredUser();
        login.setEnteredUsername("kyl_1");
        login.setEnteredPassword("Ch&&sec@ke99!");

        assertEquals("Welcome Kyle, Smith it is great to see you again.", login.returnLoginStatus());
    }

    @Test
    public void testUsernameSuccessfullyCaptured() {
        Login login = new Login();
        login.setUsername("kyl_1");

        assertEquals("Username successfully captured.", login.usernameMessage());
    }

    @Test
    public void testUsernameIncorrectlyFormattedMessage() {
        Login login = new Login();
        login.setUsername("kyle!!!!!!!");

        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", login.usernameMessage());
    }

    @Test
    public void testPasswordMeetsComplexityMessage() {
        Login login = new Login();
        login.setPassword("Ch&&sec@ke99!");

        assertEquals("Password successfully captured.", login.passwordMessage());
    }

    @Test
    public void testPasswordDoesNotMeetComplexityMessage() {
        Login login = new Login();
        login.setPassword("password");

        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", login.passwordMessage());
    }

    @Test
    public void testCellPhoneCorrectlyFormattedMessage() {
        Login login = new Login();
        login.setCellPhoneNumber("+27838968976");

        assertEquals("Cell number successfully captured.", login.cellPhoneMessage());
    }

    @Test
    public void testCellPhoneIncorrectlyFormattedMessage() {
        Login login = new Login();
        login.setCellPhoneNumber("08966553");

        assertEquals("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.", login.cellPhoneMessage());
    }

    @Test
    public void testLoginSuccessful() {
        Login login = createRegisteredUser();
        login.setEnteredUsername("kyl_1");
        login.setEnteredPassword("Ch&&sec@ke99!");

        assertTrue(login.loginUser());
    }

    @Test
    public void testLoginFailed() {
        Login login = createRegisteredUser();
        login.setEnteredUsername("kyl_1");
        login.setEnteredPassword("wrongPass1!");

        assertFalse(login.loginUser());
    }

    @Test
    public void testLoginSuccessfulMessage() {
        Login login = createRegisteredUser();
        login.setEnteredUsername("kyl_1");
        login.setEnteredPassword("Ch&&sec@ke99!");

        assertEquals("Welcome Kyle, Smith it is great to see you again.", login.returnLoginStatus());
    }

    @Test
    public void testLoginFailedMessage() {
        Login login = createRegisteredUser();
        login.setEnteredUsername("kyl_1");
        login.setEnteredPassword("wrongPass1!");

        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus());
    }

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login();
        login.setUsername("kyl_1");

        assertTrue(login.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login();
        login.setUsername("kyle!!!!!!!");

        assertFalse(login.checkUserName());
    }

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        Login login = new Login();
        login.setPassword("Ch&&sec@ke99!");

        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        Login login = new Login();
        login.setPassword("password");

        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        Login login = new Login();
        login.setCellPhoneNumber("+27838968976");

        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        Login login = new Login();
        login.setCellPhoneNumber("08966553");

        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testRegisterUserUsernameIncorrect() {
        Login login = new Login();
        login.setUsername("kyle!!!!!!!");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");

        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", login.registerUser());
    }

    @Test
    public void testRegisterUserPasswordIncorrect() {
        Login login = new Login();
        login.setUsername("kyl_1");
        login.setPassword("password");
        login.setCellPhoneNumber("+27838968976");

        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", login.registerUser());
    }

    @Test
    public void testRegisterUserCellPhoneIncorrect() {
        Login login = new Login();
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellPhoneNumber("08966553");

        assertEquals("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.", login.registerUser());
    }

    @Test
    public void testRegisterUserSuccess() {
        Login login = createRegisteredUser();

        assertEquals("User registered successfully.", login.registerUser());
    }
}
