package appDomain;

import implementations.MyStack;
import implementations.MyQueue;
import exceptions.EmptyQueueException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class XMLParser {
    private static final Pattern TAG_PATTERN = Pattern.compile(
        "<(!--.*?--|\\?.*?\\?|/?[^\\s>/]+.*?/?>)", 
        Pattern.DOTALL | Pattern.CASE_INSENSITIVE
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java XMLParser <sample2.xml>");
            return;
        }

        MyStack<String> tagStack = new MyStack<>();
        MyQueue<String> errorQueue = new MyQueue<>();
        MyQueue<String> extraTagsQueue = new MyQueue<>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                processLine(line, lineNumber, tagStack, errorQueue, extraTagsQueue);
                lineNumber++;
            }

            while (!tagStack.isEmpty()) {
                errorQueue.enqueue("Error at end: Unclosed tag <" + tagStack.pop() + ">");
            }

            printValidationResults(errorQueue, extraTagsQueue);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (EmptyQueueException e) {
            System.err.println("Queue is empty: " + e.getMessage());
        } catch (EmptyStackException e) {
            System.err.println("Stack is empty: " + e.getMessage());
        }
    }

    private static void processLine(String line, int lineNumber,
                                    MyStack<String> stack, MyQueue<String> errors,
                                    MyQueue<String> extras) throws EmptyQueueException {
        Matcher matcher = TAG_PATTERN.matcher(line);

        while (matcher.find()) {
            String fullTag = matcher.group(1).trim();

            // Skip comments and processing instructions
            if (fullTag.startsWith("!--") || fullTag.startsWith("?")) continue;

            boolean isClosing = fullTag.startsWith("/");
            boolean isSelfClosing = fullTag.endsWith("/") || fullTag.endsWith("/>");
            String tagName = extractTagName(fullTag);

            if (isSelfClosing) {
                continue; 
            } else if (isClosing) {
                validateClosingTag(tagName, lineNumber, stack, errors, extras);
            } else {
                stack.push(tagName);
            }
        }
    }

    private static String extractTagName(String tag) {
        return tag.replaceAll("^/|/?>$", "").split("\\s+")[0];
    }

    private static void validateClosingTag(String closingTag, int lineNumber,
                                           MyStack<String> stack, MyQueue<String> errors,
                                           MyQueue<String> extras) throws EmptyQueueException {
        if (stack.isEmpty()) {
            extras.enqueue("Line " + lineNumber + ": Extra closing tag </" + closingTag + ">");
            return;
        }

        String expected = stack.pop();
        if (!expected.equals(closingTag)) {
            errors.enqueue("Error at line " + lineNumber + ": Mismatched tag </" + closingTag
                         + "> (expected </" + expected + ">)");
            searchForMatch(closingTag, stack, extras); 
        }
    }

    private static void searchForMatch(String target, MyStack<String> stack,
                                       MyQueue<String> extras) throws EmptyQueueException {
        MyQueue<String> tempQueue = new MyQueue<>();
        boolean found = false;

        while (!stack.isEmpty()) {
            String current = stack.pop();
            tempQueue.enqueue(current);
            if (current.equals(target)) {
                found = true;
                break;
            }
        }

        if (!found) {
            extras.enqueue("</" + target + ">");
        }

        while (!tempQueue.isEmpty()) {
            stack.push(tempQueue.dequeue());
        }
    }

    private static void printValidationResults(MyQueue<String> errors, MyQueue<String> extras) throws EmptyQueueException {
        System.out.println("\nXML Validation Report:");
        System.out.println("======================");

        if (errors.isEmpty() && extras.isEmpty()) {
            System.out.println("Document is well-formed!");
            return;
        }

        System.out.println("Errors Found:");
        while (!errors.isEmpty()) {
            System.out.println("[ERROR] " + errors.dequeue());
        }

        System.out.println("\nExtra/Unmatched Tags:");
        while (!extras.isEmpty()) {
            System.out.println("[EXTRA] " + extras.dequeue());
        }
    }
}
