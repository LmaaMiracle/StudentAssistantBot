package state;

import bot.AssistantBot;
import bot.ButtonsHolder;
import bot.Command;
import database.entity.Group;
import database.entity.Member;
import database.entity.Student;
import database.entity.User;
import database.service.GroupService;
import database.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public enum BotState {

    Start {
        private BotState next;

        @Override
        public void sendResponse(Message message) {
            sendMessage(new ButtonsHolder().
                    setMainMenuKeyboard(message).
                    setText("Вас приветствует бот-помощник, который помогает улучшить организацию " +
                            "учебного процесса как студентов, так и преподователей. На клавиатуре выберете касту, " +
                            "к котороый вы относитесь."));
        }

        @Override
        public void handleInput(User user, String text) {
            switch (text) {
                case Command.STUDENT:
                    next = BotState.StudentRegistration;
                    next.responseNeeded = true;
                    break;
                case Command.LECTURER:
                    next = BotState.LecturerRegistration;
                    next.responseNeeded = true;
                    break;
                default:
                    sendMessage(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Start;
                    next.responseNeeded = false;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    StudentRegistration {
        private BotState next;
        private boolean correctInput;

        @Override
        public void sendResponse(Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите группу"));
        }

        @Override
        public void handleInput(User user, String text) {
            UserService userService = getUserService();
            GroupService groupService = getGroupService();
            if (groupService.getGroupNameSet().contains(text)) {
                next = BotState.Student;
                next.responseNeeded = true;

                userService.deleteUser(user);
                Student student = new Student();
                student.setBotState(user.getBotState());
                student.setChatId(user.getChatId());
                student.setGroup(groupService.findGroupByName(text));
                userService.saveUser(student);
            } else {
                next = BotState.StudentRegistration;
                next.responseNeeded = false;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    LecturerRegistration {
        private BotState next;
        private boolean correctInput;

        @Override
        public void sendResponse(Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите фамилию"));
        }

        @Override
        public void handleInput(User user, String text) {
            // Сюда записывать фамилию и тут его заносить в бд
            correctInput = true;
            if (correctInput) {
                next = BotState.Lecturer;
                next.responseNeeded = true;
            } else {
                next = BotState.StudentRegistration;
                next.responseNeeded = false;
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
        public void sendResponse(Message message) {
            sendMessage(new ButtonsHolder().
                    setStudentKeyboard(message).
                    setText("Вы студент группы Ар-шо-каво. Вы можете делать это: "));
        }

        @Override
        public void handleInput(User user, String text) {
            Student student = (Student) user;
            switch (text) {
                case Command.SEND_GROUP_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto(student.getGroup().getScheduleUrl()));
                    next = BotState.Student;
                    next.responseNeeded = false;
                    break;
                case Command.SEND_CALL_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/dhW3riD.png"));
                    next = BotState.Student;
                    next.responseNeeded = false;
                    break;
                case Command.LECTURER_LIST:
                    sendMessage(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText(AssistantBot.lecturerNames));
                    next = BotState.Student;
                    next.responseNeeded = false;
                    break;
                case Command.REGISTER_TIME:
                    next = BotState.EnterTime;
                    next.responseNeeded = true;
                    break;
                default:
                    sendMessage(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Student;
                    next.responseNeeded = false;
            }
        }

        @Override
        public BotState nextState() {
            return next == null ? this : next;
        }
    },

    Lecturer {
        private BotState next;

        @Override
        public void sendResponse(Message message) {
            sendMessage(new ButtonsHolder().
                    setLecturerKeyboard(message).
                    setText("Вы препод. Вы можете делать это: "));
        }

        @Override
        public void handleInput(User user, String text) {
            switch (text) {
                case Command.LECTURER_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/3sQvSQ8.png"));
                    next = BotState.Lecturer;
                    next.responseNeeded = false;
                    break;
                case Command.SEND_CALL_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/dhW3riD.png"));
                    next = BotState.Lecturer;
                    next.responseNeeded = false;
                    break;
                case Command.REGISTER_TIME:
                    next = BotState.EnterTime;
                    next.responseNeeded = true;
                    break;
                default:
                    sendMessage(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Lecturer;
                    next.responseNeeded = false;
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
        public void sendResponse(Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите время"));
        }

        @Override
        public void handleInput(User user, String text) {
            if (!(user instanceof Member)) {
                return;
            }
            Member member = (Member) user;;
            correctInput = true;
            if (correctInput) {
                member.setScheduleTime(text);
                next = BotState.Start;
                next.responseNeeded = true;
            } else {
                next = BotState.Start;
                next.responseNeeded = true;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    };

    private final AssistantBot bot = AssistantBot.getInstance();
    private final UserService userService = new UserService();
    private final GroupService groupService = new GroupService();
    protected boolean responseNeeded;

    BotState() {
        this.responseNeeded = true;
    }

    public boolean isResponseNeeded() {
        return responseNeeded;
    }

    public void setResponseNeeded(boolean responseNeeded) {
        this.responseNeeded = responseNeeded;
    }

    protected UserService getUserService() {
        return userService;
    }

    protected GroupService getGroupService() {
        return groupService;
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


    public abstract void handleInput(User user, String text);

    public abstract void sendResponse(Message message);

    public abstract BotState nextState();
}
