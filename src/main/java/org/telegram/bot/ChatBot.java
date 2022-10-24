/*
 * Oleh Hordiienko
 *
 * Всі права захищені
 */
package org.telegram.bot;

import com.github.javafaker.Faker;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Даний клас є тілом та безпосередньо можливими функціями боту
 * Всі поля класу статичні
 * Тобто може бути створений лише 1 екземпляр під час роботи
 *
 * @version 11.0 27 Oct 2022
 * @author olehhordiienko
 */
public class ChatBot {
    /* Поле для зберігання випадково згенерованої дати */
    private static String randomDate;

    /* Поле для зберігання безпосередньо імені бота */
    private static final String BOT_NAME = "RandomDateOrPersonBot";

    /* обʼєкт типу Faker для подальшого генерування випадкової особи */
    private static final Faker faker = new Faker();

    /**
     * Робота методу полягає у створенні правильної випадкової дати
     * Для зберігання дати та синхронізації днів із роком використовується клас GregorianCalender
     * Для генерації чисел у обраному проміжку використовується метод randBetween()
     * Його робота досить чітко описана безпосередньо у документаціїї до цього методу
     *
     */
    private static void generateDate() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        gregorianCalendar.set(Calendar.YEAR,
                randBetween(1900, 2010));

        gregorianCalendar.set(Calendar.DAY_OF_YEAR,
                randBetween(1, gregorianCalendar.getActualMaximum(Calendar.DAY_OF_YEAR)));

        randomDate = gregorianCalendar.get(Calendar.YEAR) +
                "." + (gregorianCalendar.get(Calendar.MONTH) + 1) +
                "." + gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Метод випадково генерує число на проміжку від start до end
     * Для реалізації використано класс Math зі стандартної бібліотеки
     * Алгоритм для пошуку на проміжку був використаний з:
     * <a href="https://www.geeksforgeeks.org/java-math-random-method-examples/">...</a>
     *
     * @param start Число, від якого починається проміжок
     * @param end Число до якого закінчується проміжок
     * @return випадково згенероване значення на проміжку від start до end
     */
    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    /**
     * Метод є основним двигуном Бота
     * Безпосередньо в цьому методі йде перевірка обраної команди користувачем
     * Та дані про випадково згенеровану дату чи особу передаються для подальшої обробки
     *
     * @param message текст з смс, надісланого користувачем у Telegram
     * @return відповідно до команди, обраної користувачем повертається обраний текст
     */
    public static String process(String message) {
        /*
         * В конструкції if-else відбувається перевірка надісланого тексту користувачем
         * Спочаку перевірка на команду /start
         * Потім на привітання, на /date та на /person
         * Прошу звернути увагу на роботу кейса із перевіркою на команду /person,
         * тобто на кейс з методом isPerson().
         */
        if (isStart(message)) {
            return "Привіт друже, хочеш згенерувати випадкову особу чи дату?"
                    + "\n"
                    + " Нужбо тисни /date або /person";
        } else if (isHelloMessage(message)) {
            // привітання із користувачем та повернення імʼя боту
            return "Вітаю, я - " + BOT_NAME;
        } else if (isDateMessage(message)) {
            // безпосередньо виклик методу generateDate(), повний опис зазначений у документації методу
            generateDate();
            return randomDate;
        } else if (isPerson(message)) {
            /* Для генерації випадкового імені використувається імпортована бібліотека
             * Посилання на бібліотеку:
             * https://github.com/DiUS/java-faker
             */
            return faker.name().fullName();
        }

        return "Друже, будь ласка, введи команду /date чи /person";
    }

    /**
     * Даний метод перевіряє, чи є смс від користувача командою /date
     *
     * @param message безпосередньо текст смс, надісланого користувачем
     * @return true - якщо текст смс є командою /date, false - якщо ні
     */
    private static boolean isDateMessage(String message) {
        return message.equalsIgnoreCase("/date");
    }

    /**
     * Даний метод перевіряє, чи є смс від користувача командою /person
     *
     * @param message безпосередньо текст смс, надісланого користувачем
     * @return true - якщо текст смс є командою /person, false - якщо ні
     */
    private static boolean isPerson(String message) {
        return message.equalsIgnoreCase("/person");
    }

    /**
     * Даний метод перевіряє, чи є смс від користувача командою /start
     *
     * @param message безпосередньо текст смс, надісланого користувачем
     * @return true - якщо текст смс є командою /start, false - якщо ні
     */
    private static boolean isStart(String message) {
        return message.equalsIgnoreCase("/start");
    }

    /**
     * Даний метод перевіряє, чи є смс від користувача привітанням
     *
     * @param message безпосередньо текст смс, надісланого користувачем
     * @return true - якщо текст смс є  привітанням, false - якщо ні
     */
    private static boolean isHelloMessage(String message) {
        return message.toLowerCase().contains("привіт") ||
                message.toLowerCase().contains("вітаю");
    }
}
