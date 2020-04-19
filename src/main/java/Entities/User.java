package Entities;

import State.BotState;

public class User {

    private String chatId;
    private BotState botState;
    private String scheduleTime;

    public User() {
    }

    public User(String chatId, BotState botState, String scheduleTime) {
        this.chatId = chatId;
        this.botState = botState;
        this.scheduleTime = scheduleTime;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
}
