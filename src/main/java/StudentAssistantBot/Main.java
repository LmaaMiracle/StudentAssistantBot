<<<<<<< HEAD:src/main/java/Spring/Main.java
package Spring;
=======
package StudentAssistantBot;
>>>>>>> 5d6a211063c63c1062dbc85d4f17f9d769651558:src/main/java/StudentAssistantBot/Main.java

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
<<<<<<< HEAD:src/main/java/Spring/Main.java

        SpringApplication.run(Main.class, args);

=======
        SpringApplication.run(Main.class, args);
>>>>>>> 5d6a211063c63c1062dbc85d4f17f9d769651558:src/main/java/StudentAssistantBot/Main.java
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new AssistantBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Run.run();
    }
}
