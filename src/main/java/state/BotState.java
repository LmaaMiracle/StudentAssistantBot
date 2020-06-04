package state;

import bot.AssistantBot;
import bot.ButtonsHolder;
import bot.Command;
import bot.InlineHolder;
import database.entity.*;
import database.service.GroupService;
import database.service.UserService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Optional;
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
            execute(new SendMessage().
                    setChatId(update.getMessage().getChatId()).
                    setText("Сперва прочтите Info!"));
            next = BotState.Start;
            next.responseNeeded = false;
        }

        @Override
        public void sendResponse(Message message) {
            execute(new SendMessage().setChatId(message.getChatId()).setText(
                    "\nДобро пожаловать \uD83D\uDE42\n" +
                            "Я был создан с целью помочь человечеству улучшить учебный процесс.\n" +
                            "Я буду полезен как преподавателям, так и студентам. Моя главная задача — помочь и не сломаться \uD83D\uDC7E.\n" +
                            "Я ещё развиваюсь и поэтому жду твои пожелания и замечания!\n\n" +
                            "Нажмите ℹ️ Info для дальнейшей работы")
                    .setReplyMarkup(new InlineHolder().getStartInlineKeyboard()));
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
                    next = BotState.LecturerEnterLogin;
                    next.responseNeeded = true;
                    break;
                default:
                    execute(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Info;
                    next.responseNeeded = false;
            }
        }

        @Override
        public void sendResponse(Message message) {
            String info = "" +
                    "Я был создан тремя обычными студентами, они-то обо мне и заботятся. " +
                    "Всё что есть во мне — их рук дело.\n" +
                    "Если хотите дать свой фидбек пишите кому-то из них:\n" +
                    "Александр Ярчук: @lmaa19\nДмитрий Шматков: @Dima_Sh_2001\n" +
                    "Кирилл Беляев: @arShoKaBo\n" +
                    "Почта бота: student_ass@gmail.com\n\n" +
                    "Немного о функционале: \n" +
                    "▪️Есть кнопки с разного рода инфой. Это расписание пар/расписание звонков/список преподавателей " +
                    "твоей группы и т. д., c этим не должно быть проблем.\n" +
                    "▪️Есть главная фича — это рассылка твоего расписания пар в указанное тобой время. \n" +
                    "Для этого выбери кнопку \"Время уведомления\", после чего получишь просьбу о вводе " +
                    "времени в формате \"HH:mm\", отправь мне удобное тебе время, и я не забуду тебе напомнить " +
                    "о твоих парах!";

            execute(new EditMessageText()
                    .setChatId(message.getChatId())
                    .setMessageId(message.getMessageId())
                    .setText(info));

            try {
                Thread.sleep(625);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            execute(new SendMessage().
                    setText("" +
                            "Чтобы начать работу с ботом выбери на клавиатуре с кнопками ниже кто ты, и далее тебе придётся пройти небольшую аутентификацию.\n" +
                            "Раздел преподавателя пока что находится в разработке, поэтому временно недоступен ⏳").
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

            if (update.hasCallbackQuery()) {
                Message message = update.getCallbackQuery().getMessage();

                switch (update.getCallbackQuery().getData()) {
                    case "groupList":

                        StringBuilder groupList = new StringBuilder();
                        groupList.append("*Доступные группы:*\n");
                        for (String groupName : groupService.getGroupNameSet()) {
                            groupList.append("\n• ").append(groupName);
                        }

                        execute(new SendMessage()
                                .enableMarkdown(true)
                                .setChatId(message.getChatId())
                                .setText(groupList.toString()));
                        break;

                    case "groupInputHelp":

                        String groupInputHelp = "" +
                                "▪️Чтобы авторизоваться, необходимо ввести название своей группы.\n" +
                                "▪️Полный список доступных групп можно увидеть, нажав на кнопку \"Список групп\".\n" +
                                "▪️Название группы можно вводить как с дефисом, так и без, а также без учёта регистра.\n\n" +
                                "Например:\n" +
                                "_АИ-182, АИ182, аи182_";

                        execute(new SendMessage()
                                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                                .setText(groupInputHelp)
                                .enableMarkdown(true));
                        break;
                }

            } else {
                String messageText = update.getMessage().getText().toLowerCase();
                for (Group group : groupService.findAllGroups()) {
                    String groupName = group.getGroupName().toLowerCase();
                    if (messageText.equals(groupName) || messageText.equals(groupName.replace("-", ""))) {
                        userService.deleteUser(user);
                        Student student = new Student();

                        student.setBotState(user.getBotState());
                        student.setChatId(user.getChatId());
                        student.setGroup(group);

                        userService.saveUser(student);

                        next = BotState.Student;
                        next.responseNeeded = true;
                        return;
                    }
                }

                execute(new SendMessage().
                        setChatId(update.getMessage().getChatId()).
                        setText("Группы с таким названием не существует. Попробуйте снова"));
            }

            next = BotState.StudentRegistration;
            next.responseNeeded = false;
        }

        @Override
        public void sendResponse(Message message) {
            execute(new SendMessage()
                    .setChatId(message.getChatId())
                    .setText("Введите название вашей группы")
                    .setReplyMarkup(new InlineHolder().getStudentRegistrationInlineKeyboard()));
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    LecturerEnterLogin {

        private BotState next;

        @Override
        public void handleInput(User user, Update update) {

            UserService userService = getUserService();
            Optional<LecturerData> optionalLecturerData = userService.findAllLecturerData().stream()
                    .filter(lecturerData -> update.getMessage().getText().equals(lecturerData.getSecondName()))
                    .findAny();
            if (optionalLecturerData.isPresent()) {
                userService.deleteUser(user);

                Lecturer lecturer = new Lecturer();
                lecturer.setChatId(user.getChatId());
                lecturer.setBotState(user.getBotState());
                lecturer.setLecturerData(userService.findLecturerDataBySecondName(update.getMessage().getText()));

                userService.saveUser(lecturer);next = BotState.Lecturer;
                next.responseNeeded = true;
            } else {
                execute(new SendMessage()
                        .setChatId(user.getChatId())
                        .setText("Неправильная фамилия"));
                next = BotState.LecturerEnterLogin;
                next.responseNeeded = false;
            }
        }

        @Override
        public void sendResponse(Message message) {
            execute(new SendMessage().
                    setChatId(message.getChatId()).
                    setText("Введите Вашу Фамилию:"));
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
                    execute(new SendPhoto()
                            .setChatId(user.getChatId())
                            .setCaption("@ONPUStudentAssistantBot")
                            .setPhoto("https://i.imgur.com/dhW3riD.png"));
                    next = BotState.Student;
                    next.responseNeeded = false;
                    break;
                case Command.GET_LECTURER_LIST:
                    execute(new SendMessage()
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
                    execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText("▪️Если у тебя " +
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
                    execute(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Student;
                    next.responseNeeded = false;
            }
        }

        @Override
        public void sendResponse(Message message) {
            User student = getUserService().findUserByChatId(message.getChatId());
            execute(new SendMessage().
                    setText("✔️ Вы вошли как студент группы " + ((Student) student).getGroup().getGroupName()).
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
                    execute(new SendPhoto()
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
                    execute(new SendMessage()
                            .setChatId(user.getChatId())
                            .setText("Такого выбора нет, попробуйте ещё раз"));
                    next = BotState.Lecturer;
                    next.responseNeeded = false;
            }
        }

        @Override
        public void sendResponse(Message message) {
            execute(new SendMessage().
                    setText("Вы вошли как преподователь.").
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
                Pattern pattern = Pattern.compile("([01][0-9]|2[0-3]):[0-5][0-9]");

                if (update.hasCallbackQuery()) {
                    if (update.getCallbackQuery().getData().equals("timeInput")) {
                        String answer = "" +
                                "▪️Ввод времени происходит в формате HH:mm.\n" +
                                "▪️Несколько примеров: 08:00 / 12:25 / 00:49\n" +
                                "▪️После ввода времени бот автоматически начнёт отправлять тебе ежедневно расписание твоей группы.\n" +
                                "▪️Возможна погрешность во времени отправки, она обычно до 40-50 секунд, что не смертельно.\n\n" +
                                "Вводи время, я ведь жду! \uD83D\uDE34";
                        execute(new EditMessageText()
                                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                                .setText(answer));
                    }
                    next = BotState.EnterTime;
                    next.responseNeeded = false;
                }

                if (pattern.matcher(update.getMessage().getText()).matches()) {
                    member.setScheduleTime(update.getMessage().getText());
                    getUserService().updateUser(member);
                    execute(new SendMessage().setChatId(update.getMessage().getChatId())
                            .setText("Отлично! Расаписание будет приходить в " + update.getMessage().getText()
                                    + " в течение минуты"));
                    if (member instanceof Student) {
                        next = BotState.Student;
                    } else if (member instanceof Lecturer) {
                        next = BotState.Lecturer;
                    }
                } else {
                    execute(new SendMessage().
                            setChatId(update.getMessage().getChatId()).
                            setText("Время введено некорректно. Попробуйте снова"));
                    next = BotState.EnterTime;
                }
                next.responseNeeded = false;
            }
        }

        @Override
        public void sendResponse(Message message) {
            execute(new SendMessage()
                    .setChatId(message.getChatId())
                    .setText("Введите время (например 08:30)")
                    .setReplyMarkup(new InlineHolder().getTimeInputInstructionInlineKeyboard()));
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

    protected <T extends Serializable, Method extends BotApiMethod<T>> void execute(Method method) {
        try {
            bot.execute(method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void execute(SendPhoto sendPhoto) {
        try {
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public abstract void handleInput(User user, Update update);

    public abstract void sendResponse(Message message);

    public abstract BotState nextState();
}
