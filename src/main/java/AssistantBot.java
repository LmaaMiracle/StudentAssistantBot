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

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        String chatID = update.getMessage().getChatId().toString();

        if (message != null && message.hasText()) {
            if (message.getText().equals("/start")) {
                buttonHolder(message, "Here's your keyboard!");
            } else {
                if (message.getText().equals("Расписание преподавателя")) {
                    Path path = Paths.get("C:\\Users\\1\\IdeaProjects\\PureBot\\src\\main\\static\\HodovychenkoSchedule.PNG");
                    File file = new File(path.toString());
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(message.getChatId().toString());
                    sendPhoto.setCaption("@ONPUStudentAssistantBot");
                    sendPhoto.setPhoto(file);

                    try {
                        execute(sendPhoto);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
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

        KeyboardRow firstRow = new KeyboardRow();
        List<KeyboardRow> keyboard = new ArrayList<>();

        firstRow.add(new KeyboardButton("Расписание преподавателя"));
        keyboard.add(firstRow);

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
