package Entities;

import State.BotState;

public class User {

    private long chatId;
    private BotState botState;
    private String scheduleTime;

    public User() {
    }

    public User(long chatId, BotState botState, String scheduleTime) {
        this.chatId = chatId;
        this.botState = botState;
        this.scheduleTime = scheduleTime;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
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
