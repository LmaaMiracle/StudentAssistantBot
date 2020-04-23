package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class ButtonsHolder {

    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final List<KeyboardRow> keyboard = new ArrayList<>();

    private void setSettings(boolean oneTimeKeyboard) {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(oneTimeKeyboard);
    }

    public ReplyKeyboardMarkup setMainMenuKeyboard() {
        setSettings(true);
        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.LECTURER));
        keyboard.get(0).add(new KeyboardButton(Command.STUDENT));

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup setStudentKeyboard() {
        setSettings(false);

        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.GET_GROUP_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(0).add(new KeyboardButton(Command.GET_CALL_SCHEDULE));
        keyboard.add(new KeyboardRow());
        keyboard.get(1).add(new KeyboardButton(Command.GET_LECTURER_LIST));
        keyboard.add(new KeyboardRow());
        keyboard.get(2).add(new KeyboardButton(Command.REGISTER_TIME));
        keyboard.add(new KeyboardRow());
        keyboard.get(3  ).add(new KeyboardButton(Command.GET_HELP));


        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup setLecturerKeyboard() {
        setSettings(false);

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

