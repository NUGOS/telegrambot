package com.example.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;

public class TextMessageBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return TelegramContains.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return TelegramContains.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleMessageUpdate( update );
        }
        if (update.hasCallbackQuery()) {
            handleCallbackQuery( update );
        }
        ;
    }

    private void handleMessageUpdate(Update update) {
        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        sendText( chatId, "Вы написали: " + messageText + true);
    }

    private void sendText(Long chatId, String s) {
    }

    private void sendText(Long chatId, String text, boolean appendKeyboard) {
        //create a object that contains the information to send back the message
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId( Long.toString( chatId ) ); //who should get the message? the sender from which we got the message...
        sendMessageRequest.setText( text );
        sendMessageRequest.setReplyMarkup( createdUpLowKeyboard() );
        try {
            sendApiMethod( sendMessageRequest );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboard createdUpLowKeyboard() {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();


        keyboard.setKeyboard(
                Collections.singletonList(
                        Arrays.asList( InlineKeyboardButton.builder().text( "low" ).callbackData( "make low" ).build(),
                                InlineKeyboardButton.builder().text( "up" ).callbackData( "make up" ).build()
                        )
                )
        );
        return keyboard;
    }

    private void handleCallbackQuery(Update update) {
        String callbackQuery = update.getCallbackQuery().getData();
        String text = update.getCallbackQuery().getMessage().getText();
        Long chatId = update.getCallbackQuery().getFrom().getId();
        sendText( chatId, "Вы нажали на кнопку " + callbackQuery + "текст под кнопкой " + text );

    }

}
