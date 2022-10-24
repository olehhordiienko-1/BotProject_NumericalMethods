package org.telegram.bot;

import com.github.javafaker.Faker;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class ChatBot {
    private static String randomDate;
    private static String randomFullName;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        String botAnswer = process(message);
        System.out.println(botAnswer);
    }

    private static void generateDate() {

        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        int year = randBetween(1900, 2010);

        gregorianCalendar.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(1, gregorianCalendar.getActualMaximum(Calendar.DAY_OF_YEAR));

        gregorianCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        randomDate = gregorianCalendar.get(Calendar.YEAR) + "." + (gregorianCalendar.get(Calendar.MONTH) + 1) + "." + gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static void generateFullName() {
        Faker faker = new Faker();
        randomFullName = faker.name().fullName();
    }

    public static String process(String message) {
        if (isHelloMessage(message)) {
            String botName = "Random Date or Person Bot";
            return "Вітаю, я - " + botName;
        } else if (isDateMessage(message)) {
            generateDate();
            return randomDate;
        } else if (isPerson(message)) {
            generateFullName();
            return randomFullName;
        }

        return "Enter /date or /person";
    }

    private static boolean isDateMessage(String message) {
        return message.equalsIgnoreCase("/date");
    }

    private static boolean isPerson(String message) {
        return message.equalsIgnoreCase("/person");
    }

    private static boolean isHelloMessage(String message) {
        message = message.toLowerCase();

        String helloWord1 = "привіт";
        String helloWord2 = "вітаю";

        return message.contains(helloWord1) || message.contains(helloWord2);
    }
}
