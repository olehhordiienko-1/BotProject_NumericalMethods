/*
 * Oleh Hordiienko
 *
 * Всі права захищені
 */
package org.telegram.bot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Даний клас є початком роботи всієї програми під назвою телеграмБот
 * Для вийнятків підключений логер log4j
 *
 * @version 11.0 27 Oct 2022
 * @author olehhordiienko
 */
public class App {
    /*
     * Спочатку відбувається виклик методу init() з final класу ApiContextInitializer
     * Далі виконується створення обʼєкту типу TelegramBotsApi
     * Потім створюється обʼєкт типу TelegramBot в конструкції try-catch-finally
     * Після ініціалізації обʼєкту відбувається виклик методу registerBot(param)
     * з параметром типу TelegramBot у обʼєкті final класу TelegramBotsApi
     * Якщо під час регістрації обʼєкту виникає помилка, відбувається логування позиції з помилкой
     * Лог [Log]: Bot was stopped - свідчить про закінчення роботи бота
     */

    // Обʼєкт типу Logger для подальшого логування вийнятків, log4j 1.2.17 version
    private static final Logger logger = Logger.getLogger(App.class);

    /**
     * Точка входу у класс App та початок роботи програми
     * Конкретна робота програми описана в документації до класу.
     * @param args Масив строкових аргументів
     */
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            // Створення обʼєкту типу TelegramBot для подальшого її використання
            TelegramBot bot = new TelegramBot();

            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            // Обробка вийнятку та відповідне логування
            logger.info("[Log]: Error at registration telegram bot");
        } finally {
            // Логування про завершення роботи програми
            logger.info("[Log]: Bot was stopped");
        }
    }
}
