package workWithTime;

import bot.AssistantBot;
import entity.User;
import service.UserService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;

public class CheckSchedule extends TimerTask {

    private final AssistantBot bot = new AssistantBot();
    private final UserService userService = new UserService();

    @Override
    public void run() {

        List<User> userList = userService.findAllUsers();

        for (User user : userList) {
            if (LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).equals(user.getScheduleTime())) {
                bot.sendSchedule(user);
            }
        }
    }
}
