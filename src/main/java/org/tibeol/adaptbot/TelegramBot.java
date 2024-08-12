package org.tibeol.adaptbot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.getText().equals("/start"))
            greeting(update);
        else if (update.hasCallbackQuery())
            switch (update.getCallbackQuery().getData()) {
                case "start" -> start(update);
                case "about" -> getInfoAboutCompany(update);
                case "moreinfo" -> getMoreInfoAboutCompany(update);
                case "site" -> getSite(update);
                case "documents" -> getDocuments(update);
                case "address" -> getAddress(update);
                case "contact" -> getContact(update);
                case "allbuttons" -> getAllButtons(update);
            }
        else
            if(message != null) sendMessage(message.getChatId(), null, "Мой \uD83E\uDDE0 пока не готов принимать такие команды☹");
        if (message != null)
            System.out.println("Ник: " + update.getMessage().getChat().getUserName() + "\t Чат ID: " + update.getMessage().getChatId() + "\t Сообщение ID: " + update.getMessage().getMessageId());
    }

    private void greeting(Update update) {
        long chatId = update.getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("\uD83D\uDC49Начать\uD83D\uDC48");
        button.setCallbackData("start");
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        String photoPath = "https://static.tildacdn.com/tild6437-3032-4532-a439-366238353634/___.png";
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(photoPath));
        sendPhoto.setCaption(MessageText.GREETING.getText());
        sendPhoto.setReplyMarkup(markupInline);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка при отправки фото " + e.getMessage());
        }
    }

    private void start(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("\uD83C\uDF3FРасскажи о компании\uD83E\uDDD0");
        button.setCallbackData("about");
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        if (!update.getCallbackQuery().getFrom().getUserName().equals("username"))
            sendMessage(chatId,
                    markupInline,
                    "Так-так \uD83E\uDD14, ваш никнейм - " + update.getCallbackQuery().getFrom().getUserName() + ", записал\uD83D\uDCDD");
        else
            sendMessage(chatId,
                    markupInline,
                    "Так-так \uD83E\uDD14, ваш никнейм скрыт, в графе имя ставлю прочерк)\uD83D\uDCDD");
    }

    private void getInfoAboutCompany(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Здорово\uD83D\uDC4D");
        button.setCallbackData("moreinfo");
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage(chatId,
                markupInline,
                MessageText.ABOUT_COMPANY.getText());
    }

    private void getMoreInfoAboutCompany(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Круто, но давай ближе к делу✅");
        button.setCallbackData("site");
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage(chatId,
                markupInline,
                MessageText.MORE_ABOUT_COMPANY.getText());
    }

    private void getSite(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("\uD83D\uDCD4Какие документы мне нужны❓");
        button.setCallbackData("documents");
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage(chatId,
                markupInline,
                "Понял, ускоряюсь.\uD83D\uDE80 \n" +
                        "Помимо меня, у нашей компании есть прекрасный сайт \uD83D\uDDA5, там еще больше информации:\n" +
                        "\uD83D\uDFE2 [Кликай сюда](https://kompak18.ru/) \uD83D\uDFE2");
    }

    private void getDocuments(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Ага, пойду искать✅");
        button.setCallbackData("address");
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage(chatId,
                markupInline,
                MessageText.DOCUMENTS.getText());
    }

    private void getAddress(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("С кем я могу связаться❓");
        button.setCallbackData("contact");
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage(chatId,
                markupInline,
                MessageText.ADDRESS.getText());
    }

    private void getContact(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String phoneNumber = "+79127699631"; // replace with the phone number you want to send
        String firstName = "Юлия"; // replace with the first name you want to send
        String lastName = "Васильевна"; // replace with the last name you want to send
        SendContact contact = new SendContact();
        contact.setPhoneNumber(phoneNumber);
        contact.setChatId(chatId);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        try {
            execute(contact);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка при отправки контакта");
        }
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Понял, спасибо за информацию✅");
        button.setCallbackData("allbuttons");
        rowInline.add(button);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage(chatId,
                markupInline,
                "До твоего выхода у тебя могут возникнуть вопросы, ты всегда можешь задать их этому прекрасному сотруднику\uD83D\uDE0A");
    }

    private void getAllButtons(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        // Row 1
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("\uD83C\uDF3FРасскажи о компании\uD83E\uDDD0");
        button1.setCallbackData("about");
        rowInline1.add(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Чем занимается компания❓");
        button2.setCallbackData("moreinfo");
        rowInline1.add(button2);

        rowsInline.add(rowInline1);

        // Row 2
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("\uD83D\uDCBBСсылка на сайт\uD83D\uDCBB");
        button3.setCallbackData("site");
        rowInline2.add(button3);

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("\uD83D\uDCDAСписок документов\uD83D\uDCDA");
        button4.setCallbackData("documents");
        rowInline2.add(button4);

        rowsInline.add(rowInline2);

        // Row 3
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        InlineKeyboardButton button5 = new InlineKeyboardButton();
        button5.setText("Напомни адрес\uD83E\uDD14");
        button5.setCallbackData("address");
        rowInline3.add(button5);

        InlineKeyboardButton button6 = new InlineKeyboardButton();
        button6.setText("С кем я могу связаться❓");
        button6.setCallbackData("contact");
        rowInline3.add(button6);

        rowsInline.add(rowInline3);

        markupInline.setKeyboard(rowsInline);
        sendMessage(chatId, markupInline, "Вот мои команды, пользуйся в любое время\uD83D\uDE0E \n");
    }

    public void sendMessage(long chatID, InlineKeyboardMarkup button, String answerText) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatID));
        message.setText(answerText);
        if (button != null)
            message.setReplyMarkup(button);
        message.enableMarkdown(true);
        message.disableWebPagePreview();
        try {
            execute(message);
        } catch (TelegramApiException error) {
            System.out.println("Ошибка отправки сообщения " + error.getMessage());
        }

    }
}
