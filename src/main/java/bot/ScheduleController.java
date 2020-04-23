package bot;

import database.entity.Member;
import database.entity.User;
import database.service.UserService;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleController extends TimerTask {

    private final AssistantBot bot = AssistantBot.getInstance();
    private final UserService userService = new UserService();
    private final ZoneId kievZoneId = ZoneId.of("Europe/Kiev");


    @Override
    public void run() {
        List<User> userList = userService.findAllUsers();

        for (User user : userList) {
            if (!(user instanceof Member)) {
                continue;
            }
            Member member = (Member) user;
            if (LocalTime.now(kievZoneId).format(DateTimeFormatter.ofPattern("HH:mm")).equals(member.getScheduleTime())) {
                bot.sendSchedule(user);
            }
        }
    }

    public void start() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(this, 0, 60, TimeUnit.SECONDS);
    }
}
