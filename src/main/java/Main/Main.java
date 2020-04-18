package Main;

import Bot.AssistantBot;
import WorkWithTime.Run;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        SpringApplication.run(Main.class, args);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new AssistantBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        Run.run();
    }
}
