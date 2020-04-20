package workWithTime;

import bot.AssistantBot;
import entity.User;
import service.UserService;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;

public class CheckSchedule extends TimerTask {

    private final AssistantBot bot = new AssistantBot();
    private final UserService userService = new UserService();

    private final ZoneId kievZoneId = ZoneId.of("Europe/Kiev");


    @Override
    public void run() {
        List<User> userList = userService.findAllUsers();

        System.out.println(userList + " СМОТРИ СЮДА БЛЯЯЯЯЯЯЯЯЯТЬ");

        for (User user : userList) {
            if (LocalTime.now(kievZoneId).format(DateTimeFormatter.ofPattern("HH:mm")).equals(user.getScheduleTime())) {
                bot.sendSchedule(user);
            }
        }
    }
}
