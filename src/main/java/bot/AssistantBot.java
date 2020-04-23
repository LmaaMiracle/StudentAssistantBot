package bot;

import database.entity.Guest;
import database.entity.User;
import database.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import state.BotState;

public class AssistantBot extends TelegramLongPollingBot {

    private static AssistantBot bot;

    private final UserService userService = new UserService();

    private AssistantBot() {
    }

    public static AssistantBot getInstance() {
        if (bot == null) {
            bot = new AssistantBot();
            new ScheduleController().start();
        }
        return bot;
    }

    public static final String lecturerNames =
            "WEB:\n" +
                    "       • лекц. — Защёлкин Константин Вячеславович\n" +
                    "       • лаб. — Шпиньковский Александр Анатольевич\n" +
                    "Теория вероятности:\n" +
                    "       • лекц. — Юрченко Михаил Александрович\n" +
                    "       • практ. — Ищенко Алеся Владимировна\n" +
                    "Численные методы:\n" +
                    "       • лекц. — Панькина Анна Сергеевна\n" +
                    "       • лаб. — Петросюк Денис Валериевич\n" +
                    "ООП:\n" +
                    "       • лекц., лаб. — Годовиченко Николай Анатолиевич\n" +
                    "ТКП:\n" +
                    "       • лекц., лаб — Галчёнков Олег Николаевич\n" +
                    "Теория коммуникации:\n" +
                    "       • лекц., практ. — Чурсин Николай Николаевич\n" +
                    "Экономика и бизнес:\n" +
                    "       • лекц., практ. — Журан Елена Анатолиевна\n" +
                    "Философия:\n" +
                    "       • лекц., практ. — Рыбка Наталья Николаевна";


    public void sendSchedule(User user) {
        try {
            execute(new SendPhoto()
                    .setChatId(user.getChatId())
                    .setCaption("@ONPUStudentAssistantBot")
                    .setPhoto("https://i.imgur.com/khEWk4K.png"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message == null || !message.hasText()) {
            return;
        }

        String messageText = message.getText();
        long chatId = message.getChatId();
        User user = userService.findUserByChatId(chatId);


        BotState botState;
        if (messageText.equals(Command.START) || messageText.equals(Command.RESTART)) {
            if (user != null) {
                userService.deleteUser(user);
            }
            botState = BotState.Start;
            botState.setResponseNeeded(true);

            user = new Guest();
            user.setChatId(chatId);
            user.setBotState(botState);
        } else {
            botState = user.getBotState();
            botState.handleInput(user, messageText);
            botState = botState.nextState();
        }

        if (botState.isResponseNeeded()) {
            botState.sendResponse(message);
        }
        user.setBotState(botState);
        System.out.println("Next state: " + botState);
        userService.saveOrUpdateUser(user);
    }

    @Override
    public String getBotUsername() {
        return "Student Assistant";
    }

    @Override
    public String getBotToken() {
        return "977643237:AAHWqxWnlzziPo2QSrLGhgzgO93ywV8eFN4";
    }
}