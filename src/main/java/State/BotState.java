package State;

import Bot.AssistantBot;

public enum BotState {

    Default {
        @Override
        public void enter(AssistantBot bot) {

        }

        @Override
        public BotState nextState() {
            return EnterTime;
        }
    },

    EnterTime {
        @Override
        public void enter(AssistantBot bot) {

        }

        @Override
        public void handleInput(AssistantBot bot) {

        }

        @Override
        public BotState nextState() {
            return Default;
        }
    };

    private static BotState[] states;
    private final boolean inputNeeded;

    BotState() {
        this.inputNeeded = true;
    }

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static BotState getInitialState() {
        return byId(0);
    }

    public static BotState byId(int id) {
        if (states == null) {
            states = BotState.values();
        }
        return states[id];
    }

    protected void sendMessage(AssistantBot bot, String text) {

    }

    public  boolean isInputNeeded() {
        return inputNeeded;
    }

    public void handleInput(AssistantBot bot){}

    public abstract void enter(AssistantBot bot);
    public abstract BotState nextState();
}
