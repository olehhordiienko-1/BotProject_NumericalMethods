/*
 * Oleh Hordiienko
 *
 * Всі права захищені
 */
package org.telegram.bot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Клас TelegramBot є підкласом типу TelegramLongPollingBot
 * Із цього класу безпосередньо виконується відправка тексту користувачу
 * Та відправка на обротку тексту, відправленного користувачем
 * Клас перевизначає методи супер класу
 * Для вийнятків підключений логер log4j
 *
 * @version 11.0 27 Oct 2022
 * @author olehhordiienko
 */
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger logger = Logger.getLogger(TelegramBot.class);

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            String response = ChatBot.process(message);

            sendText(update.getMessage().getChatId(), response);
        }
    }

    private void sendText(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.info("[Log]: Error at a running method execute() in method sendText()");
        }
    }

    @Override
    public String getBotUsername() {
        return "RandomDateOrPersonBot";
    }

    @Override
    public String getBotToken() {
        return "5774242881:AAEL_lYGosxaOgAc-wHjvke7bSG9fNlkRuE";
    }
}