package WorkWithTime;

import Bot.AssistantBot;
import Entities.User;
import Services.UserService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;

public class CheckSchedule extends TimerTask {

    private final AssistantBot bot = new AssistantBot();
    private final UserService userService = new UserService();

    /*private final User user = new User();
    //здесь для проверки ставьте время большее на 1-2 минуты от текущего
//    private final String time = "16:10";

    {
        students.add(new Student(644026470, "10:40", "AI-182"));
        students.add(new Student(383625717, "16:28", "AI-182"));
        students.add(new Student(383625717, "12:40", "AI-182"));
    }*/

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
