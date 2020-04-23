package database.entity;

import lombok.*;
import state.BotState;

import javax.persistence.*;


@MappedSuperclass
@Data
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "bot_state")
    @Enumerated(EnumType.STRING)
    private BotState botState;
}

