import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class AssistantBot extends TelegramLongPollingBot {

    public static final String lecturerNames = "" +
            "WEB:\n" +
            "       лекц. — Защёлкин Константин Вячеславович\n" +
            "       лаб. — Шпиньковский Александр Анатольевич\n" +
            "Теория вероятности:\n" +
            "       лекц. — Юрченко Михаил Александрович\n" +
            "       практ. — Ищенко Алеся Владимировна\n" +
            "Численные методы:\n" +
            "       лекц. — Панькина Анна Сергеевна\n" +
            "       лаб. — Петросюк Денис Валериевич\n" +
            "ООП:\n" +
            "       лекц., лаб. — Годовиченко Николай Анатолиевич\n" +
            "ТКП:\n" +
            "       лекц., лаб — Галчёнков Олег Николаевич\n" +
            "Теория коммуникации:\n" +
            "       лекц., практ. — Чурсин Николай Николаевич\n" +
            "Экономика и бизнес:\n" +
            "       лекц., практ. — Журан Елена Анатолиевна\n" +
            "Философия:\n" +
            "       лекц., практ. — Рыбка Наталья Николаевна";

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        String chatID = update.getMessage().getChatId().toString();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    buttonHolder(message, "Here's your keyboard!");
                    break;
                case "Расписание преподавателя": {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(message.getChatId().toString());
                    sendPhoto.setCaption("@ONPUStudentAssistantBot");
                    sendPhoto.setPhoto("https://i.imgur.com/3sQvSQ8.png");

                    try {
                        execute(sendPhoto);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "Расписание студента": {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(message.getChatId().toString());
                    sendPhoto.setCaption("@ONPUStudentAssistantBot");
                    sendPhoto.setPhoto("https://i.imgur.com/khEWk4K.png");

                    try {
                        execute(sendPhoto);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "Имена преподавателей": {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId().toString());
                    sendMessage.setText(lecturerNames);

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
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

    public void buttonHolder(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ButtonsSettings.getSettings(replyKeyboardMarkup);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton("Расписание преподавателя"));
        keyboard.add(new KeyboardRow());
        keyboard.get(1).add(new KeyboardButton("Расписание студента"));
        keyboard.add(new KeyboardRow());
        keyboard.get(2).add(new KeyboardButton("Имена преподавателей"));

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
