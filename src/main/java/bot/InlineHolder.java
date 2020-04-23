package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineHolder {

    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private SendMessage sendMessage;
    private List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    private List<InlineKeyboardButton> rowInline = new ArrayList<>();

    private void inlineHolder() {
        inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage = new SendMessage();
    }

    public void startInlineKeyBoard(Message message) {
        inlineHolder();

        this.rowInline.add(new InlineKeyboardButton("ℹ️ Info").setCallbackData("info"));
//        this.rowInline.add(new InlineKeyboardButton("⏭ Skip").setCallbackData("skip"));

        this.rowsInline.add(rowInline);
        this.inlineKeyboardMarkup.setKeyboard(rowsInline);

        sendMessage.setChatId(message.getChatId());
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup(Message message) {
        startInlineKeyBoard(message);
        return inlineKeyboardMarkup;
    }
}
