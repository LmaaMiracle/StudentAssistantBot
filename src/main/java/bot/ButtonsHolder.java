package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class ButtonsHolder {

    private SendMessage sendMessage;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private List<KeyboardRow> keyboard = new ArrayList<>();

    private void buttonHolder() {
        sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        this.setSettings();
    }

    private void setSettings() {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    public SendMessage teacherButtonsHolder(Message message) {
        buttonHolder();

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.TEACHER_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(1).add(new KeyboardButton(Command.CALL_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(2).add(new KeyboardButton(Command.REGISTER_TIME));

        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(message.getChatId().toString());

        return sendMessage;
    }


    public SendMessage studentButtonHolder(Message message) {
        buttonHolder();

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.STUDENT_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(1).add(new KeyboardButton(Command.CALL_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(2).add(new KeyboardButton(Command.TEACHER_LIST));
        keyboard.add(new KeyboardRow());
        keyboard.get(3).add(new KeyboardButton(Command.REGISTER_TIME));

        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(message.getChatId().toString());

        return sendMessage;
    }

    public SendMessage mainButtonsHolder(Message message) {
        buttonHolder();

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.TEACHER));
        keyboard.get(0).add(new KeyboardButton(Command.STUDENT));

        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(message.getChatId().toString());

        return sendMessage;
    }
}

