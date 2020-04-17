package WorkWithTime;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Run {

    public static void run() {
        //а эта хуйня запускает метод с функционалом каждую минуту (потом поставим мб раз в 30 сек чекать,
        // ибо пока что если ставить раз в 30 сек то за минуту может придти 2 раза расписание
        // (это из-за костыльной реализации, когда будет инфа в бд, думаю это легко пофиксится, идея в голове уже есть))

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(new CheckSchedule(), 0, 60, TimeUnit.SECONDS);
    }

}
