package State;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface PassState {
    void updateState(Password info, Update update);
}
