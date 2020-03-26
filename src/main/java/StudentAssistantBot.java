import org.apache.logging.log4j.core.util.JsonUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class StudentAssistantBot extends TelegramLongPollingBot
{
    public static final Logger _Log = Logger.getLogger(StudentAssistantBot.class.getName());


    @Override
    public void onUpdateReceived(Update update)
    {
        //  System.out.println(update.getMessage().getText());
        //  System.out.println(update.getMessage().getFrom().getFirstName() );
        Message message = update.getMessage();
        if (message != null && message.hasText())
        {
            if (message.getText().equals("Расписание"))
                sendSchedule(message, "\uD83D\uDCC4");
            else
                switch (message.getText())
                {
                /*case "Расписание":
                    sendSchedule(message, "ᅠ");
                    break;*/

                    case "Понедельник":
                    {
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setChatId(message.getChatId().toString());
                        sendPhoto.setCaption("@ONPUStudentAssistantBot");
                        sendPhoto.setPhoto("https://psv4.userapi.com/c848224/u72407304/docs/d11/ae1098e07ee7/Pn.png?extra=dU6G8fj0OgLinaxqlhLSRBIUYMLxS_No6roG2URwycC9MBNgb_Icz9DXoTh7JciUuCF8tUsJXJcjookXoysmVFlvqGbnx0rgud8E46d9wX010-_g7cXIzy2T1HkZ6RgC9qZVpm7CeiHvCDuRotaw");
                        try
                        {
                            execute(sendPhoto);
                        } catch (TelegramApiException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;

                    case "Вторник":
                    {
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setChatId(message.getChatId().toString());
                        sendPhoto.setCaption("@ONPUStudentAssistantBot");
                        sendPhoto.setPhoto("https://psv4.userapi.com/c848224/u72407304/docs/d5/797432493a73/vt.png?extra=dXG_oPtAWZ55uVs2PXe-g_fMSCapmOjvTR2DP7MPyv0Uqcf2hJngOQiC_gYI8TJbxA2Tv3nI1PxwkpMUVFxekWcTbZXqiG9eqlZcLsv5zjJ7NJSAmaLENzgiTODQ6R75_vT4LQlrrgvCRqcEmWPq");
                        try
                        {
                            execute(sendPhoto);
                        } catch (TelegramApiException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;

                    case "Среда":
                    {
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setChatId(message.getChatId().toString());
                        sendPhoto.setCaption("@ONPUStudentAssistantBot");
                        sendPhoto.setPhoto("https://psv4.userapi.com/c848224/u72407304/docs/d1/f60a232678c0/sr.png?extra=a7F6XNWoRmaGxyAYQ5QiDydqicWFWK5MlXE-EvaT8OOEro8izj7PoA4MIsinHo9fkEs6yjU6ImNz8Ua8feEVW1G5gxK5J2B6UNuLkpQwYD0FltPQgyzb6_pdgfaLWVQ42ZBY0gPmFoEB82hOy61a");
                        try
                        {
                            execute(sendPhoto);
                        } catch (TelegramApiException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;

                    case "Четверг":
                    {
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setChatId(message.getChatId().toString());
                        sendPhoto.setCaption("@ONPUStudentAssistantBot");
                        sendPhoto.setPhoto("https://psv4.userapi.com/c848224/u72407304/docs/d7/5dc769ed76a7/cht.png?extra=KhPQ0jZtxQ__8JzLB9MqO2Ul3-54CeCa_i0PQx8GIyKPlR6YRxNymN-fz4iGQDeubCSI0uNku4rt32cjgX1-9cEvmwePeG-_hoqEgTvg4KkBSObn5D_TI3r5m9U91L0MN1zEp7_PQ5DUgU_VQlzO");
                        try
                        {
                            execute(sendPhoto);
                        } catch (TelegramApiException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;

                    case "Пятница":
                    {
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setChatId(message.getChatId().toString());
                        sendPhoto.setCaption("@ONPUStudentAssistantBot");
                        sendPhoto.setPhoto("https://psv4.userapi.com/c848224/u72407304/docs/d16/96ac54df2c40/pt.png?extra=zvQCnFfQFIpmqaJRUErclr1j-PfFUPTn1qlqHQdn7hBGYM5Lin7yt2oypcOEe71opq9Vu7q9SxuIhklIeKGMCw2MLIBPkCRgexyja3QdZblnxQb5CmNuYMndLcACIhMJkBKQPgGqbl-Nz7JlF3BX");
                        try
                        {
                            execute(sendPhoto);
                        } catch (TelegramApiException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;

                    case "Полное":
                    {
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setChatId(message.getChatId().toString());
                        sendPhoto.setCaption("@ONPUStudentAssistantBot");
                        sendPhoto.setPhoto("https://sun9-32.userapi.com/c855416/v855416270/d4f17/-svzlXEYbYY.jpg");
                        try
                        {
                            execute(sendPhoto);
                        } catch (TelegramApiException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;

                    case "⬅️ Назад":
                    {
                        sendMsg(message, "Back");
                    }
                    break;

                    default:
                        if (message.getText().equals("Back"))
                            break;
                }

            if (message.getText().equals("Звонки"))
            {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(message.getChatId().toString());
                sendPhoto.setCaption("@ONPUStudentAssistantBot");
                sendPhoto.setPhoto("https://sun9-20.userapi.com/c855416/v855416270/d4ee8/sGnmiLzMUD0.jpg");
                try
                {
                    execute(sendPhoto);
                } catch (TelegramApiException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public String getBotUsername()
    {
        return "Student Assistant";
    }


    @Override
    public String getBotToken()
    {
        return " bot's id here ";
    }


    private void sendMsg(Message message, String s)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true); // было false

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Расписание"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Звонки"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);

        try
        {
            execute(sendMessage);
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }

    }

    private void sendSchedule(Message message1, String s1)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true); // было false

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Понедельник"));
        //keyboardFirstRow.add(new KeyboardButton("Вторник"));
        keyboardFirstRow.add(new KeyboardButton("Среда"));
        keyboardFirstRow.add(new KeyboardButton("Пятница"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Вторник"));
        keyboardSecondRow.add(new KeyboardButton("Четверг"));
        keyboardSecondRow.add(new KeyboardButton("Полное"));

        KeyboardRow keyboardBottomRow = new KeyboardRow();
        keyboardBottomRow.add(new KeyboardButton("⬅️ Назад"));
        //  keyboardSecondRow.add(new KeyboardButton("Пятница"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardBottomRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message1.getChatId().toString());
        sendMessage.setText(s1);

        try
        {
            execute(sendMessage);
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }

    }

    /*private void SendPhoto(Update update)
    {
        Message message = update.getMessage();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setCaption("@ONPUStudentAssistantBot");
        sendPhoto.setPhoto("https://psv4.userapi.com/c848024/u72407304/docs/d13/7b9a92be5f34/0001.png?extra=_TYTL_MlDacZsCnQ9rIuEOfR_dT1StBiQik6bvDQxh2eSuVHXZrs8YKHQgUlH7_weghlDj62odOU7sPQPV-cF6HzsOU01rOA_wgnXVdZ1pGtOEHYiLDbImS21rC3mUZ2TJSyso5PgfTDZ4MOUoRA");
        try
        {
            execute(sendPhoto);
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }*/

}

