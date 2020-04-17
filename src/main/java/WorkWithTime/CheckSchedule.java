package WorkWithTime;

import Bot.AssistantBot;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class CheckSchedule extends TimerTask {

    private final AssistantBot bot = new AssistantBot();

    //здесь для проверки ставьте время большее на 1-2 минуты от текущего
    private String time = "19:36";

    public void getTime() {
        System.out.println(time.equals(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))); // это для дебага, пока не трогайте

        if (time.equals(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))) {
            bot.scheduleConfirm();
        }
    }

    //метод который как раз таки отвечает за функционал всей этой хуйни
    @Override
    public void run() {
        getTime();
    }
}


//    private String localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
/*
    private void check() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(new WorkWithTime.CheckSchedule(), 60, 100_000, TimeUnit.SECONDS);
    }*/