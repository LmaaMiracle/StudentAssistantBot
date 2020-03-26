import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MainClass
{

    public static void main(String[] args) throws SchedulerException
    {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try
        {
            telegramBotsApi.registerBot(new StudentAssistantBot());

        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }

        //JobDetail job = JobBuilder.newJob(QuartzJobClass.class).build();

        //Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CornTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).build();

        //Scheduler sc = StdSchedulerFactory.getDefaultScheduler();
        // sc.start();
        //   sc.scheduleJob(job, trigger);
    }
}