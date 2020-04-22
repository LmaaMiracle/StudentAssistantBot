package bot;

import database.entity.User;
import database.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import state.BotState;

public class AssistantBot extends TelegramLongPollingBot {

    private final UserService userService = new UserService();

    private static AssistantBot bot;

    private AssistantBot() {}

    public static AssistantBot getInstance() {
        if (bot == null) {
            bot = new AssistantBot();
            new ScheduleController().start();
        }
        return bot;
    }

    public static final String lecturerNames = "" +
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
        if (!update.hasMessage() && !update.getMessage().hasText())
            return;

        Long chatId = update.getMessage().getChatId();
        User user = userService.findUserById(chatId);

        BotState state;
        if (user == null || update.getMessage().getText().equals(Command.RESTART)) {
            state = BotState.Start;
            state.setNewState(true);
            if (user == null) {
                user = new User(chatId, state);
            }
        } else {
            state = user.getBotState();
            state.handleInput(user, update.getMessage().getText());
            state = state.nextState();
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(state);
        }
        System.out.println(state.isNewState());
        if (state != null && state.isNewState()) {
            state.enter(user, update.getMessage());
        }
        user.setBotState(state);
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