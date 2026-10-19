/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

// Message handles the creation, validation, hashing, status selection,
// and JSON storage of chat messages.
/**
 *
 * @author ST10534768
 */
public class Message {

    // Attributes for tracking message details
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String messageStatus;

    // Static counter to accumulate total sent messages across instances
    private static int totalMessagesSent = 0;

    // Cell phone regular expression to ensure the recipient number contains
    // the international country code (+27) followed by no more than ten digits.
    // Adapted from: Baeldung. 2024. How to Validate Phone Numbers in Java.
    // Available at: https://www.baeldung.com/java-regex-validate-phone-numbers
    // [Accessed 1 September 2026].
    // Pattern syntax from: Oracle. n.d. Regular Expressions. The Java Tutorials.
    // Available at: https://docs.oracle.com/javase/tutorial/essential/regex/
    // [Accessed 1 September 2026].
    private static final Pattern RECIPIENT_CELL_PATTERN = Pattern.compile("^\\+27[0-9]{1,10}$");

    // Default constructor
    public Message() {
    }

    // Parameterized constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
    }

    // Full constructor including message ID
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
    }

    // Getters and setters
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    // Generates a ten-digit message ID using loop counter and substring string manipulation
    public String generateMessageID(int counter) {
        String prefix = String.format("%02d", counter);
        long randomDigits = (long) (Math.random() * 90000000L) + 10000000L;
        String rawID = prefix + randomDigits;
        // String manipulation using substring to ensure exactly ten characters
        this.messageID = rawID.substring(0, 10);
        return this.messageID;
    }

    // Returns formatted string displaying generated message ID
    public String printMessageID() {
        return "Message ID generated: " + messageID;
    }

    // This method ensures that the message ID is not more than ten characters
    public boolean checkMessageID() {
        if (messageID == null) {
            return false;
        }

        if (messageID.length() <= 10 && messageID.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Returns true if the recipient number matches the international format
    public boolean isRecipientCellValid() {
        if (recipient == null) {
            return false;
        }

        if (RECIPIENT_CELL_PATTERN.matcher(recipient).matches()) {
            return true;
        } else {
            return false;
        }
    }

    // This method ensures that the recipient cell number is no more than ten characters long and starts with a code
    public String checkRecipientCell() {
        if (isRecipientCellValid() == true) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Checks if the message does not exceed 250 characters
    public boolean isMessageLengthValid() {
        if (messageText == null) {
            return false;
        }

        if (messageText.length() <= 250) {
            return true;
        } else {
            return false;
        }
    }

    // Returns success message or specifies by how many characters the message exceeds 250
    public String checkMessageLength() {
        if (messageText == null) {
            return "Please enter a message of less than 250 characters.";
        }

        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // This method creates and returns the Message Hash
    public String createMessageHash() {
        if (messageID == null || messageText == null) {
            return "";
        }

        // Extract first two characters of message ID using substring
        String idPrefix;
        if (messageID.length() >= 2) {
            idPrefix = messageID.substring(0, 2);
        } else {
            idPrefix = messageID;
        }

        // Split message into words using whitespace
        String[] words = messageText.trim().split("\\s+");
        String firstWord = "";
        String lastWord = "";

        // Identify first alphanumeric word
        for (int i = 0; i < words.length; i++) {
            String clean = words[i].replaceAll("[^a-zA-Z0-9]", "");
            if (clean.length() > 0) {
                firstWord = clean;
                break;
            }
        }

        // Identify last alphanumeric word
        for (int i = words.length - 1; i >= 0; i--) {
            String clean = words[i].replaceAll("[^a-zA-Z0-9]", "");
            if (clean.length() > 0) {
                lastWord = clean;
                break;
            }
        }

        // Combine into all-caps hash
        String hash = (idPrefix + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        this.messageHash = hash;
        return hash;
    }

    // This method allows the user to choose if they want to send, store, or disregard the message
    public String SentMessage(int choice) {
        if (choice == 1) {
            this.messageStatus = "Sent";
            totalMessagesSent++;
            return "Message successfully sent.";
        } else if (choice == 2) {
            this.messageStatus = "Disregard";
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            this.messageStatus = "Stored";
            return "Message successfully stored.";
        } else {
            return "Invalid option selected.";
        }
    }

    // Standard Java method name alias
    public String sentMessage(int choice) {
        return SentMessage(choice);
    }

    // Overloaded method allowing text choice selection
    public String SentMessage(String choice) {
        if (choice == null) {
            return "Invalid option selected.";
        }

        String lowerChoice = choice.trim().toLowerCase();
        if (lowerChoice.equals("1") || lowerChoice.contains("send")) {
            return SentMessage(1);
        } else if (lowerChoice.equals("2") || lowerChoice.contains("disregard") || lowerChoice.contains("discard")) {
            return SentMessage(2);
        } else if (lowerChoice.equals("3") || lowerChoice.contains("store")) {
            return SentMessage(3);
        } else {
            return "Invalid option selected.";
        }
    }

    // Standard Java method name alias for string overload
    public String sentMessage(String choice) {
        return SentMessage(choice);
    }

    // This method returns all the message details in the required order:
    // Message ID, Message Hash, Recipient, Message.
    public String printMessages() {
        String hash = (messageHash != null) ? messageHash : createMessageHash();
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + hash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }

    // This method returns the total number of messages sent
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // Alias matching standard English spelling
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Returns static total count of sent messages
    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }

    // Resets static message counter (useful for clean unit testing)
    public static void resetTotalMessagesSent() {
        totalMessagesSent = 0;
    }

    // Research method to store the messages in JSON format.
    // Adapted from: Oracle. 2024. Reading, Writing, and Creating Files. The Java Tutorials.
    // Available at: https://docs.oracle.com/javase/tutorial/essential/io/file.html
    // [Accessed 19 October 2026].
    // JSON formatting structure adapted from: ECMA International. 2017. The JSON Data Interchange Syntax (Standard ECMA-404 2nd Edition).
    // Available at: https://www.ecma-international.org/publications-and-standards/standards/ecma-404/
    // [Accessed 19 October 2026].
    public boolean storeMessage(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            filePath = "messages.json";
        }

        try {
            String hash = (messageHash != null) ? messageHash : createMessageHash();
            String safeText = (messageText != null) ? messageText.replace("\\", "\\\\").replace("\"", "\\\"") : "";

            String jsonEntry = "  {\n"
                    + "    \"messageID\": \"" + (messageID != null ? messageID : "") + "\",\n"
                    + "    \"messageHash\": \"" + hash + "\",\n"
                    + "    \"recipient\": \"" + (recipient != null ? recipient : "") + "\",\n"
                    + "    \"message\": \"" + safeText + "\"\n"
                    + "  }";

            File file = new File(filePath);

            if (!file.exists() || file.length() == 0) {
                try (FileWriter writer = new FileWriter(file, false)) {
                    writer.write("[\n" + jsonEntry + "\n]");
                }
            } else {
                String existing = new String(java.nio.file.Files.readAllBytes(file.toPath()), java.nio.charset.StandardCharsets.UTF_8).trim();
                if (existing.endsWith("]")) {
                    String base = existing.substring(0, existing.lastIndexOf("]")).trim();
                    if (base.endsWith(",")) {
                        base = base.substring(0, base.length() - 1).trim();
                    }
                    String combined;
                    if (base.equals("[")) {
                        combined = "[\n" + jsonEntry + "\n]";
                    } else {
                        combined = base + ",\n" + jsonEntry + "\n]";
                    }
                    try (FileWriter writer = new FileWriter(file, false)) {
                        writer.write(combined);
                    }
                } else {
                    try (FileWriter writer = new FileWriter(file, false)) {
                        writer.write("[\n" + jsonEntry + "\n]");
                    }
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error storing message in JSON: " + e.getMessage());
            return false;
        }
    }

    // Default overload storing to messages.json
    public boolean storeMessage() {
        return storeMessage("messages.json");
    }
}
