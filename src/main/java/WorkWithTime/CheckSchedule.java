package WorkWithTime;

import Bot.AssistantBot;
import Entities.User;
import Student.Student;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimerTask;

public class CheckSchedule extends TimerTask {

    private final AssistantBot bot = new AssistantBot();

    private final ArrayList<Student> students = new ArrayList<Student>();

    private final User user = new User();
    //здесь для проверки ставьте время большее на 1-2 минуты от текущего
//    private final String time = "16:10";

    {
        students.add(new Student(644026470, "10:40", "AI-182"));
        students.add(new Student(383625717, "16:28", "AI-182"));
        students.add(new Student(383625717, "12:40", "AI-182"));
    }

    @Override
    public void run() {
//        for (Student student : students) {
//            if (student.getNotificationTime().equals(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))) {
//                bot.scheduleConfirm(student);
//            }
//        }
    }
}
