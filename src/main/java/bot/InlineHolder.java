package bot;

import com.google.inject.internal.cglib.core.$ReflectUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineHolder {

    private InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    private List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    private List<InlineKeyboardButton> rowInline = new ArrayList<>();


    public InlineKeyboardMarkup getStartInlineKeyboard() {
        rowInline.add(new InlineKeyboardButton("ℹ️ Info").setCallbackData("info"));

        rowsInline.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getGroupInlineKeyboard() {
        rowInline.add(new InlineKeyboardButton("Просмотреть группы").setCallbackData("groups"));

        rowsInline.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getTimeInputInstructionInlineKeyboard() {
        rowInline.add(new InlineKeyboardButton("\uD83D\uDD5C Инструкция").setCallbackData("time_input"));

        rowsInline.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }
}
