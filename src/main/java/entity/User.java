package entity;

import state.BotState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "bot_state")
    @Enumerated(EnumType.STRING)
    private BotState botState;

    @Column(name = "schedule_time")
    private String scheduleTime;


    public User(long chatId, BotState botState) {
        this.chatId = chatId;
        this.botState = botState;
    }

    /*@Override
    public boolean equals(Object o) {

    }*/
}