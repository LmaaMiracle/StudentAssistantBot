package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class ButtonsHolder {

    private void setSettings(ReplyKeyboardMarkup replyKeyboardMarkup) {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    public ReplyKeyboardMarkup setMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        setSettings(replyKeyboardMarkup);

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.LECTURER));
        keyboard.get(0).add(new KeyboardButton(Command.STUDENT));

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup setStudentKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        setSettings(replyKeyboardMarkup);

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.GET_GROUP_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.GET_CALL_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(1).add(new KeyboardButton(Command.GET_LECTURER_LIST));
        keyboard.add(new KeyboardRow());
        keyboard.get(2).add(new KeyboardButton(Command.REGISTER_TIME));
        keyboard.add(new KeyboardRow());
        keyboard.get(3 ).add(new KeyboardButton(Command.GET_HELP));


        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup setLecturerKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        setSettings(replyKeyboardMarkup);

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.GET_LECTURER_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(1).add(new KeyboardButton(Command.GET_CALL_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(2).add(new KeyboardButton(Command.REGISTER_TIME));

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}