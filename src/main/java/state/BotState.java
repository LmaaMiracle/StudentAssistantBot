package state;

import bot.AssistantBot;
import bot.ButtonsHolder;
import bot.Command;
import database.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public enum BotState {

    Start{
        private BotState next;

        @Override
        public void enter(User user, Message message) {
            sendMessage(new ButtonsHolder().
                    mainButtonsHolder(message).
                    setText("Вас приветствует бот-помощник, который помогает улучшить организацию" +
                            " учбеного процесса как студентов, так и преподователей. На клавиатуре выберете касту, " +
                            "к котороый вы относитесь. "));
        }

        @Override
        public void handleInput(User user, String text) {
            switch (text) {
                case Command.STUDENT:
                    next = BotState.StudentRegistration;
                    next.newState = true;
                    break;
                case Command.TEACHER:
                    next = BotState.TeacherRegistration;
                    next.newState = true;
                    break;
                default:
                    sendMessage(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Start;
                    next.newState = false;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    StudentRegistration{
        private BotState next;
        private boolean correctInput;

        @Override
        public void enter(User user, Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите группу"));
        }

        @Override
        public void handleInput(User user, String text) {
            // Сюда записывать группу
            correctInput = true;
            if (correctInput) {
                next = BotState.Student;
                next.newState = true;
            } else {
                next = BotState.StudentRegistration;
                next.newState = false;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    TeacherRegistration{
        private BotState next;
        private boolean correctInput;

        @Override
        public void enter(User user, Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите фамилию"));
        }

        @Override
        public void handleInput(User user, String text) {
            // Сюда записывать фамилию и тут его заносить в бд
            correctInput = true;
            if (correctInput) {
                next = BotState.Teacher;
                next.newState = true;
            } else {
                next = BotState.StudentRegistration;
                next.newState = false;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Student {
        private BotState next;

        @Override
        public void enter(User user, Message message) {
            sendMessage(new ButtonsHolder().
                    studentButtonHolder(message).
                    setText("Вы студент группы Ар-шо-каво. Вы можете делать это: "));
        }

        @Override
        public void handleInput(User user, String text) {
            switch (text) {
                case Command.STUDENT_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/khEWk4K.png"));
                    next = BotState.Student;
                    next.newState = false;
                    break;
                case Command.CALL_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/dhW3riD.png"));
                    next = BotState.Student;
                    next.newState = false;
                    break;
                case Command.TEACHER_LIST:
                    sendMessage(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText(AssistantBot.lecturerNames));
                    next = BotState.Student;
                    next.newState = false;
                    break;
                case Command.REGISTER_TIME:
                    next = BotState.EnterTime;
                    next.newState = true;
                    break;
                default:
                    sendMessage(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Student;
                    next.newState = false;
            }
        }

        @Override
        public BotState nextState() {
            return next == null ? this : next;
        }
    },

    Teacher {
        private BotState next;

        @Override
        public void enter(User user, Message message) {
            sendMessage(new ButtonsHolder().
                    teacherButtonsHolder(message).
                    setText("Вы препод. Вы можете делать это: "));
        }

        @Override
        public void handleInput(User user, String text) {
            switch (text) {
                case Command.TEACHER_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/3sQvSQ8.png"));
                    next = BotState.Teacher;
                    next.newState = false;
                    break;
                case Command.CALL_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/dhW3riD.png"));
                    next = BotState.Teacher;
                    next.newState = false;
                    break;
                case Command.REGISTER_TIME:
                    next = BotState.EnterTime;
                    next.newState = true;
                    break;
                default:
                    sendMessage(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Teacher;
                    next.newState = false;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    EnterTime {
        private BotState next;
        private boolean correctInput;

        @Override
        public void enter(User user, Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите время"));
        }

        @Override
        public void handleInput(User user, String text) {
            correctInput = true;
            if (correctInput) {
                user.setScheduleTime(text);
                next = BotState.Start;
                next.newState = true;
            } else {
                next = BotState.Start;
                next.newState = true;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    };

    private final AssistantBot bot = AssistantBot.getInstance();
    protected boolean newState;

    BotState() {
        this.newState = true;
    }

    public void setNewState(boolean newState) {
        this.newState = newState;
    }

    protected void sendMessage(SendMessage sendMessage) {
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void sendMessage(SendPhoto sendPhoto) {
        try {
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public  boolean isNewState() {
        return newState;
    }

    public void handleInput(User user, String text){}

    public abstract void enter(User user, Message message);
    public abstract BotState nextState();
}
