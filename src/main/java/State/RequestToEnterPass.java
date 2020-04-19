package State;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.pengrad.telegrambot.TelegramBot;

public class RequestToEnterPass implements PassState {
    //singleton
    private static final RequestToEnterPass instance = new RequestToEnterPass();

    private TelegramBot bot;

    private RequestToEnterPass() {
    }

    public static RequestToEnterPass instance() {
        return instance;
    }

    @Override
    public void updateState(Password info, Update update) {
        Message message = update.getMessage();

        long chatID = message.getChatId();

        SendMessage sendMessage = new SendMessage();

        bot.execute(new com.pengrad.telegrambot.request.SendMessage(chatID, "Подтвердите, что вы преподаватель." +
                " Введите пароль:"));
    }
}
