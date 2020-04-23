package state;

import bot.AssistantBot;
import bot.ButtonsHolder;
import bot.Command;
import bot.InlineHolder;
import database.entity.Member;
import database.entity.Student;
import database.entity.User;
import database.service.GroupService;
import database.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Pattern;

public enum BotState {

    Start {
        private BotState next;

        @Override
        public void handleInput(User user, Update update) {
            if (update.hasCallbackQuery()) {
                if (update.getCallbackQuery().getData().equals("info")) {
                    next = BotState.Info;
                    next.responseNeeded = true;
                }
                return;
            }
            sendMessage(new SendMessage().
                    setChatId(update.getMessage().getChatId()).
                    setText("Сперва прочтите info!"));
            next = BotState.Start;
            next.responseNeeded = false;
        }

        @Override
        public void sendResponse(Message message) {
            sendMessage(new SendMessage().setChatId(message.getChatId()).setText(
                    "\nДобро пожаловать \uD83D\uDE42\n" +
                            "Я был создан с целью помочь человечеству улучшить учебный процесс.\n" +
                            "Я буду полезен как преподавателям, так и студентам. Моя главная задача — помочь и не сломаться \uD83D\uDC7E.\n" +
                            "Я ещё развиваюсь и поэтому жду твои пожелания и замечания!\n\n" +
                            "Оставить отзыв, просмотреть контактную информацию, а также ознакомиться с инструкцией можно выбрав кнопку ℹ️ Info.\n ")
                    .setReplyMarkup(new InlineHolder().getInlineKeyboardMarkup()));
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Info {
        private BotState next;

        @Override
        public void handleInput(User user, Update update) {
            switch (update.getMessage().getText()) {
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
        public void sendResponse(Message message) {
            String answer = "Я был создан тремя обычными студентами, они обо мне-то и заботятся. " +
                    "Всё что есть во мне — их рук дело.\n" +
                    "Если хотите дать свой фидбек пишите кому-то из них:\n" +
                    "Александр Ярчук: @lmaa19\nДмитрий Шматков: @Dima_Sh_2001\n" +
                    "Кирилл Беляев: @arShoKaBo\n" +
                    "Почта бота: student_ass@gmail.com\n\n" +
                    "Немного о функционале: \n" +
                    "▪️Есть кнопки с разного рода инфой. Это расписание пар/расписание звонков/список преподавателей" +
                    "твоей группы и т.д., c этим не должно быть проблем.\n" +
                    "▪️Есть главная фича — это рассылка твоего расписания пар в указанное тобой время. \n" +
                    "Для этого выбери кнопку \"Время уведомления\", после чего получишь просьбу о вводе " +
                    "времени в формате \"HH:mm\", отправь мне удобное тебе время, и я не забуду тебе напомнить " +
                    "о твоих парах!\n\n" +
                    "Чтобы начать работу с ботом выбери на клавиатуре с кнопками ниже кто ты, и далее тебе " +
                    "придётся пройти небольшую аутентификацию.";

            sendMessage(new EditMessageText()
                    .setChatId(message.getChatId())
                    .setMessageId(message.getMessageId())
                    .setText(answer));

            sendMessage(new SendMessage().
                    setText("Удачи! Я с тобой :)").
                    setChatId(message.getChatId()).
                    enableMarkdown(true).
                    setReplyMarkup(new ButtonsHolder().setMainMenuKeyboard()));

        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    StudentRegistration {
        private BotState next;

        @Override
        public void handleInput(User user, Update update) {

            UserService userService = getUserService();
            GroupService groupService = getGroupService();

            if (groupService.getGroupNameSet().contains(update.getMessage().getText())) {
                next = BotState.Student;
                next.responseNeeded = true;

                userService.deleteUser(user);
                Student student = new Student();

                student.setBotState(user.getBotState());
                student.setChatId(user.getChatId());
                student.setGroup(groupService.findGroupByName(update.getMessage().getText()));

                userService.saveUser(student);

            } else {
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId());
                message.setText("Группы с таким названием не существует. Попробуйте снова");
                sendMessage(message);
                next = BotState.StudentRegistration;
                next.responseNeeded = true;
            }
        }

        @Override
        public void sendResponse(Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите название группы (например АИ-182)"));
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
        public void handleInput(User user, Update update) {
            // Сюда записывать фамилию и тут его заносить в бд
            correctInput = true;
            if (correctInput) {
                next = BotState.Lecturer;
                next.responseNeeded = true;
            } else {
                next = BotState.LecturerRegistration;
                next.responseNeeded = false;
            }
        }

        @Override
        public void sendResponse(Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите фамилию"));
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Student {
        private BotState next;

        @Override
        public void handleInput(User user, Update update) {
            switch (update.getMessage().getText()) {
                case Command.GET_GROUP_SCHEDULE:
                    bot.sendSchedule(user);
                    next = BotState.Student;
                    next.responseNeeded = false;
                    break;
                case Command.GET_CALL_SCHEDULE:
                    sendMessage(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/dhW3riD.png"));
                    next = BotState.Student;
                    next.responseNeeded = false;
                    break;
                case Command.GET_LECTURER_LIST:
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
                case Command.GET_HELP:
                    sendMessage(new SendMessage().setChatId(update.getMessage().getChatId()).setText("▪️Если у тебя " +
                            "возникли проблемы или ты неправильно ввёл свою группу, можешь меня перезапустить " +
                            "командой /restart \n" +
                            "▪️Время рассылки расписания вводится в формате HH:mm (24h). Ты жмёшь на кнопку, бот даёт " +
                            "запрос на ввод времени, после чего вводи удобное тебе время! \n" +
                            "▪️Группа вводится в Политех-формате (АИ-181)\n" +
                            "▪️Если всё полетело пиши @lmaa19 / @arShoKaBo / @Dima_Sh_2001 или на почту student_ass@gmail.com\n"));
                    next = BotState.Student;
                    next.responseNeeded = false;
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
        public void sendResponse(Message message) {
            User student = getUserService().findUserByChatId(message.getChatId());
            sendMessage(new SendMessage().
                    setText("Вы вошли как студент группы " + ((Student) student).getGroup().getGroupName()).
                    setChatId(message.getChatId()).
                    enableMarkdown(true).
                    setReplyMarkup(new ButtonsHolder().setStudentKeyboard()));
        }

        @Override
        public BotState nextState() {
            return next == null ? this : next;
        }
    },

    Lecturer {
        private BotState next;

        @Override
        public void handleInput(User user, Update update) {
            switch (update.getMessage().getText()) {
                case Command.GET_LECTURER_SCHEDULE:
                    bot.sendSchedule(user);
                    next = BotState.Lecturer;
                    next.responseNeeded = false;
                    break;
                case Command.GET_CALL_SCHEDULE:
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
        public void sendResponse(Message message) {
            sendMessage(new SendMessage().
                    setText("Вы препод. Вы можете делать это: ").
                    setChatId(message.getChatId()).
                    enableMarkdown(true).
                    setReplyMarkup(new ButtonsHolder().setLecturerKeyboard()));
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    EnterTime {
        private BotState next;

        @Override
        public void handleInput(User user, Update update) {
            if (user instanceof Member) {
                Member member = (Member) user;
                Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
                if (pattern.matcher(update.getMessage().getText()).matches()) {
                    member.setScheduleTime(update.getMessage().getText());
                    getUserService().updateUser(member);
                    next = BotState.Student;
                    next.responseNeeded = false;
                    sendMessage(new SendMessage().setChatId(update.getMessage().getChatId())
                            .setText("Отлично! Расаписание придёт в " + update.getMessage().getText()));
                } else {
                    SendMessage message = new SendMessage();
                    message.setChatId(update.getMessage().getChatId());
                    message.setText("Время введено некорректно. Попробуйте снова");
                    sendMessage(message);
                    next = BotState.EnterTime;
                    next.responseNeeded = true;
                }

            }
        }

        @Override
        public void sendResponse(Message message) {
            sendMessage(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите время (например 08:30)"));
        }

        @Override
        public BotState nextState() {
            return next;
        }
    };

    protected final AssistantBot bot = AssistantBot.getInstance();
    private final UserService userService = new UserService();
    private final GroupService groupService = new GroupService();
    protected boolean responseNeeded;

    BotState() {
        this.responseNeeded = true;
    }

    public boolean isResponseNeeded() {
        return responseNeeded;
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

    protected void sendMessage(EditMessageText editMessageText) {
        try {
            bot.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public abstract void handleInput(User user, Update update);

    public abstract void sendResponse(Message message);

    public abstract BotState nextState();
}
