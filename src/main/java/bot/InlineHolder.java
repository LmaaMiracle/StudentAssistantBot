package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineHolder {

    private InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    private List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    private List<InlineKeyboardButton> rowInline = new ArrayList<>();


    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        rowInline.add(new InlineKeyboardButton("ℹ️ Info").setCallbackData("info"));
//        this.rowInline.add(new InlineKeyboardButton("⏭ Skip").setCallbackData("skip"));

        rowsInline.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }
}
