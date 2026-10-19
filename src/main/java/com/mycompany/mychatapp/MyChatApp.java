/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mychatapp;

import java.util.Scanner;

// MyChatApp is the console application that handles user registration,
// login authentication, and interactive message sending via QuickChat.
/**
 *
 * @author ST10534768
 */
public class MyChatApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login login = new Login();

        System.out.println("MyChatApp registration");
        System.out.println();

        System.out.print("Enter your first name: ");
        login.setFirstName(input.nextLine());

        System.out.print("Enter your last name: ");
        login.setLastName(input.nextLine());

        // Username must contain an underscore and be no more than five characters long
        boolean usernameValid = false;
        while (usernameValid == false) {
            System.out.print("Enter a username: ");
            login.setUsername(input.nextLine());

            if (login.checkUserName() == true) {
                System.out.println(login.usernameMessage());
                usernameValid = true;
            } else {
                System.out.println(login.usernameMessage());
            }
        }

        // Password must be eight characters long and include a capital letter, a number, and a special character
        boolean passwordValid = false;
        while (passwordValid == false) {
            System.out.print("Enter a password: ");
            login.setPassword(input.nextLine());

            if (login.checkPasswordComplexity() == true) {
                System.out.println(login.passwordMessage());
                passwordValid = true;
            } else {
                System.out.println(login.passwordMessage());
            }
        }

        // Cell number must include the +27 country code and a number no more than ten digits long
        boolean cellValid = false;
        while (cellValid == false) {
            System.out.print("Enter a South African cell phone number (for example +27838968976): ");
            login.setCellPhoneNumber(input.nextLine());

            if (login.checkCellPhoneNumber() == true) {
                System.out.println(login.cellPhoneMessage());
                cellValid = true;
            } else {
                System.out.println(login.cellPhoneMessage());
            }
        }

        System.out.println();
        System.out.println(login.registerUser());
        System.out.println();
        System.out.println("MyChatApp login");
        System.out.println();

        // Decision structure that verifies authentication before allowing access
        boolean loggedIn = false;
        while (loggedIn == false) {
            System.out.print("Enter your username: ");
            login.setEnteredUsername(input.nextLine());

            System.out.print("Enter your password: ");
            login.setEnteredPassword(input.nextLine());

            if (login.loginUser() == true) {
                System.out.println(login.returnLoginStatus());
                loggedIn = true;
            } else {
                System.out.println(login.returnLoginStatus());
            }
        }

        // Welcome message displayed once login is successful
        System.out.println();
        System.out.println("Welcome to QuickChat.");

        // While loop for numeric main menu running until the user chooses to quit
        boolean running = true;
        while (running == true) {
            System.out.println();
            System.out.println("Please select an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter your choice: ");

            String menuChoice = input.nextLine().trim();

            if (menuChoice.equals("1")) {
                // Ask user how many messages they wish to enter
                System.out.print("How many messages would you like to enter? ");
                int totalMessages = 0;
                try {
                    totalMessages = Integer.parseInt(input.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number entered. Returning to menu.");
                    continue;
                }

                // For loop allowing the user to enter only the set number of messages
                for (int i = 0; i < totalMessages; i++) {
                    System.out.println();
                    System.out.println("--- Entering Message " + (i + 1) + " of " + totalMessages + " ---");

                    Message currentMessage = new Message();
                    currentMessage.setMessageNumber(i);

                    // Generate ten-digit message ID using string manipulation and loop counter
                    currentMessage.generateMessageID(i);
                    System.out.println(currentMessage.printMessageID());

                    // Enter and validate recipient cell number
                    boolean recipientValid = false;
                    while (recipientValid == false) {
                        System.out.print("Enter recipient cell number (+27 followed by digits): ");
                        currentMessage.setRecipient(input.nextLine());

                        if (currentMessage.isRecipientCellValid() == true) {
                            System.out.println(currentMessage.checkRecipientCell());
                            recipientValid = true;
                        } else {
                            System.out.println(currentMessage.checkRecipientCell());
                        }
                    }

                    // Enter and validate message text (less than 250 characters)
                    boolean lengthValid = false;
                    while (lengthValid == false) {
                        System.out.print("Enter message text (under 250 characters): ");
                        currentMessage.setMessageText(input.nextLine());

                        if (currentMessage.isMessageLengthValid() == true) {
                            System.out.println(currentMessage.checkMessageLength());
                            lengthValid = true;
                        } else {
                            System.out.println(currentMessage.checkMessageLength());
                        }
                    }

                    // Autogenerate message hash
                    currentMessage.createMessageHash();

                    // Prompt user for sending choice
                    System.out.println();
                    System.out.println("Choose message action:");
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Enter option (1-3): ");

                    int actionChoice = 0;
                    try {
                        actionChoice = Integer.parseInt(input.nextLine().trim());
                    } catch (NumberFormatException e) {
                        actionChoice = 0;
                    }

                    String actionResult = currentMessage.SentMessage(actionChoice);
                    System.out.println(actionResult);

                    // If sent, display full details in order: Message ID, Message Hash, Recipient, Message
                    if (actionChoice == 1) {
                        System.out.println();
                        System.out.println("--- Sent Message Details ---");
                        System.out.println(currentMessage.printMessages());
                    } else if (actionChoice == 3) {
                        // Store message to JSON file
                        currentMessage.storeMessage();
                    }
                }

                // Display accumulated total sent messages
                System.out.println();
                System.out.println("Total messages sent: " + Message.getTotalMessagesSent());

            } else if (menuChoice.equals("2")) {
                // Feature still in development
                System.out.println("Coming Soon.");
            } else if (menuChoice.equals("3")) {
                // Quits application
                System.out.println("Thank you for using QuickChat. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option selected. Please choose 1, 2, or 3.");
            }
        }

        input.close();
    }
}
