package State;

import org.telegram.telegrambots.meta.api.objects.Update;

public class Password {
    private PassState currentState;
    private String password;

    public Password(PassState passState) {
        super();
        this.currentState = passState;

        if (currentState == null) {
            this.currentState = RequestToEnterPass.instance();
        }
    }

    public void update(Update update) {
        currentState.updateState(this, update);
    }

    public PassState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(PassState currentState) {
        this.currentState = currentState;
    }

}
