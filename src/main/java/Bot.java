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
                    sendMsg(systemMessage, "Привет!\uD83D\uDD90\n" +
                            "Мы туристическое агенство Vilkovo Kilimbey.\n" +
                            "С нами всегда весело, интересно, комфортно и безопасно.");
                    break;
                case "/Help":
                    sendMsg(systemMessage, "С помощью нашего бота вы можете выбрать\n" +
                                                "интересующий вас отдых и оставить свои данные\n" +
                                                "для того что бы менеджер связался с вами для дальнейшего бронирования! ");
                    break;
                case "/Contacts":
                    sendMsg(systemMessage, "Украина, г. Вилково, ул. Белгородский канал 2\n " + "+38 095 538 69 59\n " + "+38 097 815 20 85");
                    break;
                case "/Services":
                    sendMsg(systemMessage, "1. Фото охота\uD83E\uDD86\n" +
                            "2. Рыбалка\uD83D\uDC1F\n" +
                            "3. Прогулки по городу\uD83C\uDFE1\n" +
                            "4. Прогулки на каяках\uD83D\uDEF6\n" +
                            "5. Экскурсия на 0-км\uD83D\uDEA4\n" +
                            "6. Экскурсия в Солёный Кут\uD83D\uDEA4\n" +
                            "7. Кемпинг\uD83C\uDF32\n" +
                            "8. Отдых на островах\uD83C\uDF8D");
                    sendMsg(systemMessage,"Сделайте выбор и введите номер который вас интересующей");
                    break;
                case "/Weather":
                    sendMsg(systemMessage, "Для того что бы узнать погоду, введите интиресующий вас город!");
                    break;
                default:
                    try {
                        sendMsg(systemMessage, Weather.getWeather(systemMessage.getText(), model));
                    } catch (IOException e) {
                        sendMsg(systemMessage, "Город не найден!");
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
