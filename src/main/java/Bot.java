import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private String userNameToby = "Toby_Home_Bot";
    private String userNameKayakClub = "Vilkovo_KilimbeyBot";

    private String BotTokenToby = "754118114:AAFBlY7kiDDhBRzaQj32656UJSLtNYIFiLA";
    private String BotTokenKayakClub = "1143227705:AAGsrgQ4xz2Mg9qfWAVj-LzvKlurmYgQquw";


    public String getBotUsername() {
        return userNameKayakClub;
    }

    public String getBotToken() {
        return BotTokenKayakClub;
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setBtn(sendMessage);
            sendMessage(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message systemMessage = update.getMessage();
        if (systemMessage != null && systemMessage.hasText()) {
            switch (systemMessage.getText()) {
                case "/start":
                    sendMsg(systemMessage, Commands.greeting);
                    break;
                case "/Help":
                    sendMsg(systemMessage, Commands.helpText);
                    break;
                case "/Contacts":
                    sendMsg(systemMessage, Commands.contactsText);
                    break;
                case "/Services":
                    sendMsg(systemMessage, Commands.servicesOfferText);
                    sendMsg(systemMessage, Commands.servicesText);
                    break;
                case "1":
                    sendMsg(systemMessage, Commands.photoHunt);
                    break;
                case "2":
                    sendMsg(systemMessage, Commands.fishing);
                    break;
                case "3":
                    sendMsg(systemMessage, Commands.walkingAroundTheCity);
                    break;
                case "4":
                    sendMsg(systemMessage, Commands.kayaking);
                    break;
                case "5":
                    sendMsg(systemMessage, Commands.excursionToZeroKm);
                    break;
                case "6":
                    sendMsg(systemMessage, Commands.excursionToSaltyKut);
                    break;
                case "7":
                    sendMsg(systemMessage, Commands.camping);
                    break;
                case "8":
                    sendMsg(systemMessage, Commands.holidaysOnIslands);
                    break;
                case "/Weather":
                    sendMsg(systemMessage, Commands.weatherText);
                    break;
                default:
                    try {
                        sendMsg(systemMessage, Weather.getWeather(systemMessage.getText(), model));
                    } catch (IOException e) {
                        sendMsg(systemMessage, Commands.weatherErrorText);
                    }
            }
        }
    }

    public void setBtn(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/Help"));
        keyboardFirstRow.add(new KeyboardButton("/Contacts"));
        keyboardFirstRow.add(new KeyboardButton("/Services"));
        keyboardFirstRow.add(new KeyboardButton("/Weather"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
