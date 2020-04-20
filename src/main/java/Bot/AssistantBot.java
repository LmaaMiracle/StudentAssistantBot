package Bot;

import Entities.User;
import Services.UserService;
import State.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssistantBot extends TelegramLongPollingBot {

    private final UserService userService = new UserService();

    public static final ArrayList<User> listOfUsers = new ArrayList<>();

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


    //чтобы протестить для себя нужно в setChatID добавить ваш тг-айди, получить его можно в этом боте: @userinfobot
    //айдишники: Дима - 644026470, Саша - 383625717, Кирилл - 391582879

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

        Long chatId = message.getChatId();

        /*boolean isNotInList = true;

        User user = null;

        for (User user1 : listOfUsers) {
            if (user1.getChatId() == message.getChatId()) {
                isNotInList = false;
                user = user1;
                break;
            }
        }

        if(isNotInList) {
            user = new User();
            user.setChatId(message.getChatId());
            user.setBotState(BotState.Default);
            listOfUsers.add(user);
        }
        BotState state = user.getBotState();*/

        User user = userService.findUserById(chatId);

        if (user == null) {
            user = new User(chatId, BotState.Default);
        }

        BotState state = user.getBotState();


        if (message.hasText()) {
            if (message.getText().equals("/start")) {
                mainButtonsHolder(message, "Вас приветствует бот-помощник, который помогает улучшить организацию" +
                        " учбеного процесса как студентов, так и преподователей. На клавиатуре выберете касту, " +
                        "к котороый вы относитесь. ");
            }
            if (message.getText().equals("/register_time")) {
                try {
                    execute(new SendMessage().setChatId(chatId).setText("Введите время:"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                user.setBotState(state.nextState());
                userService.saveOrUpdateUser(user);
            } else if (state == BotState.EnterTime) {
                user.setScheduleTime(message.getText());
                user.setBotState(state.nextState());
                try {
                    execute(new SendMessage().setChatId(chatId).setText("Уведомление придет в " + user.getScheduleTime()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                userService.saveOrUpdateUser(user);
            }
//            Timer timer = new Timer();

//            timer.scheduleAtFixedRate(new WorkWithTime.CheckSchedule(), 0, 5000);

            switch (message.getText()) {
                case "Преподаватель": {
//                    password.update(update);
                    teacherButtonsHolder(message);
                    break;
                }
                case "Студент": {
                    studentButtonHolder(message);
                    break;
                }
                case "Расписание звонков": {
                    try {
                        execute(new SendPhoto()
                                .setChatId(message.getChatId().toString())
                                .setCaption("@ONPUStudentAssistantBot")
                                .setPhoto("https://i.imgur.com/dhW3riD.png"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                //препод
                case "Расписание": {
                    try {
                        execute(new SendPhoto()
                                .setChatId(message.getChatId().toString())
                                .setCaption("@ONPUStudentAssistantBot")
                                .setPhoto("https://i.imgur.com/3sQvSQ8.png"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                //студент
                case "Расписание пар": {
                    try {
                        execute(new SendPhoto()
                                .setChatId(message.getChatId().toString())
                                .setCaption("@ONPUStudentAssistantBot")
                                .setPhoto("https://i.imgur.com/khEWk4K.png"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "Имена преподавателей": {
                    try {
                        execute(new SendMessage()
                                .setChatId(message.getChatId().toString())
                                .setText(lecturerNames));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "Назад": {
                    mainButtonsHolder(message, "Что выберешь?");
                    break;
                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "Student Assistant";
    }

    @Override
    public String getBotToken() {
        return "977643237:AAHWqxWnlzziPo2QSrLGhgzgO93ywV8eFN4";
    }

    public void teacherButtonsHolder(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ButtonsSettings.getSettings(replyKeyboardMarkup);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton("Расписание"));
        keyboard.add(new KeyboardRow());
        keyboard.get(1).add(new KeyboardButton("Расписание звонков"));
        keyboard.add(new KeyboardRow());
        keyboard.get(2).add(new KeyboardButton("Назад"));

        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());

        try {
            execute(sendMessage.setText("Вам доступны следующие функции: "));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void studentButtonHolder(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ButtonsSettings.getSettings(replyKeyboardMarkup);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton("Расписание пар"));
        keyboard.add(new KeyboardRow());
        keyboard.get(1).add(new KeyboardButton("Расписание звонков"));
        keyboard.add(new KeyboardRow());
        keyboard.get(2).add(new KeyboardButton("Имена преподавателей"));
        keyboard.add(new KeyboardRow());
        keyboard.get(3).add(new KeyboardButton("Назад"));

        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());

        try {
            execute(sendMessage.setText("Вы можете выбрать следующее действие: "));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void mainButtonsHolder(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ButtonsSettings.getSettings(replyKeyboardMarkup);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton("Преподаватель"));
        keyboard.get(0).add(new KeyboardButton("Студент"));

        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}


