package main;

import bot.AssistantBot;
import workWithTime.Run;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import workWithTime.ScheduleController;

public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(AssistantBot.getInstance());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}