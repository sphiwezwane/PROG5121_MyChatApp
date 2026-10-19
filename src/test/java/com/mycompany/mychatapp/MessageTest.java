/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// MessageTest checks message length validation, recipient cell formatting,
// message hash generation, message ID creation, message action statuses,
// total messages counter, and JSON storage using the assignment test data.
/**
 *
 * @author ST10534768
 */
public class MessageTest {

    @BeforeEach
    public void setUp() {
        // Reset static counter before each test
        Message.resetTotalMessagesSent();
    }

    // Helper method to create a Message instance with Test Case 1 data
    private Message createTestCase1Message() {
        Message message = new Message();
        message.setMessageID("0012345678");
        message.setMessageNumber(0);
        message.setRecipient("+27718693002");
        message.setMessageText("Hi Mike, can you join us for dinner tonight?");
        return message;
    }

    // Helper method to create a Message instance with Test Case 2 data
    private Message createTestCase2Message() {
        Message message = new Message();
        message.setMessageID("0187654321");
        message.setMessageNumber(1);
        message.setRecipient("08575975889");
        message.setMessageText("Hi Keegan, did you receive the payment?");
        return message;
    }

    // Test: The message should not be more than 250 Characters (Success)
    @Test
    public void testMessageLengthSuccess() {
        Message message = createTestCase1Message();
        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    // Test: The message should not be more than 250 Characters (Failure exceeding limit)
    @Test
    public void testMessageLengthFailure() {
        Message message = new Message();
        // Create a message string that is 260 characters long (exceeds 250 by 10)
        String longText = "a".repeat(260);
        message.setMessageText(longText);

        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", message.checkMessageLength());
    }

    // Test: The recipient number is correctly formatted (Success with international code)
    @Test
    public void testRecipientNumberCorrectlyFormatted() {
        Message message = new Message();
        message.setRecipient("+27718693002");

        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell());
    }

    // Test: The recipient number is incorrectly formatted (Failure missing international code)
    @Test
    public void testRecipientNumberIncorrectlyFormatted() {
        Message message = new Message();
        message.setRecipient("08575975889");

        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", message.checkRecipientCell());
    }

    // Test: Message hash is correct for Test Case 1 (00:0:HITONIGHT)
    @Test
    public void testMessageHashCorrectTestCase1() {
        Message message = createTestCase1Message();
        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    // Test: Remainder of message hashes tested in a loop
    @Test
    public void testMessageHashesInLoop() {
        String[] testIDs = {"0012345678", "0187654321"};
        int[] testNumbers = {0, 1};
        String[] testTexts = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?"
        };
        String[] expectedHashes = {
            "00:0:HITONIGHT",
            "01:1:HIPAYMENT"
        };

        for (int i = 0; i < testIDs.length; i++) {
            Message message = new Message();
            message.setMessageID(testIDs[i]);
            message.setMessageNumber(testNumbers[i]);
            message.setMessageText(testTexts[i]);

            assertEquals(expectedHashes[i], message.createMessageHash());
        }
    }

    // Test: Message ID is created and displays confirmation
    @Test
    public void testMessageIDIsCreated() {
        Message message = new Message();
        message.setMessageID("0012345678");

        assertEquals("Message ID generated: 0012345678", message.printMessageID());
    }

    // Test: checkMessageID returns true for valid length (<= 10 characters)
    @Test
    public void testCheckMessageIDValid() {
        Message message = new Message();
        message.generateMessageID(0);

        assertTrue(message.checkMessageID());
    }

    // Test: checkMessageID returns false if ID exceeds 10 characters
    @Test
    public void testCheckMessageIDInvalid() {
        Message message = new Message();
        message.setMessageID("123456789012"); // 12 characters

        assertFalse(message.checkMessageID());
    }

    // Test: MessageSent - Option 1: Send Message
    @Test
    public void testMessageSentOptionSend() {
        Message message = createTestCase1Message();
        assertEquals("Message successfully sent.", message.SentMessage(1));
    }

    // Test: MessageSent - Option 2: Disregard Message
    @Test
    public void testMessageSentOptionDisregard() {
        Message message = createTestCase2Message();
        assertEquals("Press 0 to delete the message.", message.SentMessage(2));
    }

    // Test: MessageSent - Option 3: Store Message
    @Test
    public void testMessageSentOptionStore() {
        Message message = createTestCase1Message();
        assertEquals("Message successfully stored.", message.SentMessage(3));
    }

    // Test: Total number of sent messages accumulated
    @Test
    public void testReturnTotalMessagesSent() {
        Message msg1 = createTestCase1Message();
        Message msg2 = createTestCase2Message();

        // msg1 is sent, msg2 is discarded
        msg1.SentMessage(1);
        msg2.SentMessage(2);

        assertEquals(1, msg1.returnTotalMessagess());
        assertEquals(1, msg1.returnTotalMessages());
    }

    // Test: printMessages returns details in order: Message ID, Message Hash, Recipient, Message
    @Test
    public void testPrintMessagesOrder() {
        Message message = createTestCase1Message();
        String details = message.printMessages();

        assertTrue(details.contains("Message ID: 0012345678"));
        assertTrue(details.contains("Message Hash: 00:0:HITONIGHT"));
        assertTrue(details.contains("Recipient: +27718693002"));
        assertTrue(details.contains("Message: Hi Mike, can you join us for dinner tonight?"));
    }

    // Test: Research storeMessage saves message into JSON file
    @Test
    public void testStoreMessageJSON() {
        Message message = createTestCase1Message();
        String testJsonFile = "test_messages.json";

        File file = new File(testJsonFile);
        if (file.exists()) {
            file.delete();
        }

        boolean stored = message.storeMessage(testJsonFile);
        assertTrue(stored);
        assertTrue(file.exists());

        // Clean up test file
        file.delete();
    }
}
