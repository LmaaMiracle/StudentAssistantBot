package Main;

import DAO.UsersDAOImpl;
import Entities.User;
import State.BotState;

public class Main {
    public static void main(String[] args) {

        /*ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new AssistantBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Run.run();*/

        UsersDAOImpl usersDAO = new UsersDAOImpl();

        usersDAO.save(new User(21, BotState.Default, "10:30"));
        usersDAO.save(new User(22, BotState.EnterTime, "13:45"));
    }
}
