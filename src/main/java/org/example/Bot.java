package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Time;
import java.util.*;

public class Bot extends TelegramLongPollingBot {
    private Map<Long, Id> Users = new HashMap<>();
    private String[] option = {"random joke", "cat fact", "numbers fact", "random quote", "country"};
    private String massage = "Hi, What information do you want? (Enter one of the option)";
    private boolean[] isAvailable;
    ArrayList<Id> idArrayList = new ArrayList<>();
    private int[] requestsCounter;
    private int sumOfRequests;
    private int sumOfToday = 0;
    private int sumOfYesterday = 0;
    private int sumOf2daysEgo = 0;

    public Bot(boolean[] isAvailable) {

        if (new Time(System.currentTimeMillis()).toString().equals("00:00:00")) {
            sumOf2daysEgo = sumOfYesterday;
            sumOfYesterday = sumOfToday;
            sumOfToday = 0;
        }

        requestsCounter = new int[5];
        Arrays.fill(requestsCounter, 0);

        for (int i = 0; i < option.length; i++) {
            if (isAvailable[i]) {

                massage += "\n";
                massage += option[i];
            }
        }

        this.isAvailable = isAvailable;
    }

    @Override
    public String getBotUsername() {
        return "YYinformationbot";
    }

    @Override
    public String getBotToken() {
        return "5992274951:AAHVRg4t7JsZvGY3QLMpyLqghauMqv8O6C4";
    }

    @Override
    public void onUpdateReceived(Update update) {

        new Thread(() -> {

            long chatId = update.getMessage().getChatId();
            Id id = Users.get(chatId);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            if (id == null) {
                id = new Id(update.getMessage().getChat().getUserName(), new Time(System.currentTimeMillis()), "Start");
                Users.put(chatId, id);
                sendMessage.setText(this.massage);
                Id tempId = id;
                idArrayList.add(tempId);
            } else {
                try {

                    id.setLestRequestsTime(new Time(System.currentTimeMillis()));
                    id.setSumOfRequests();

                    if (update.getMessage().getText().equals("random joke") && isAvailable[0]) { /////////////////////////////////////////////////////////////////////////////

                        sumOfRequests++;
                        requestsCounter[0]++;
                        id.setLestRequests("random joke");

                        HttpResponse<String> response = Unirest.get("https://official-joke-api.appspot.com/random_joke").asString();

                        ObjectMapper objectMapper = new ObjectMapper();
                        RandomJokeModel randomJokeModel = objectMapper.readValue(response.getBody(), RandomJokeModel.class);


                        sendMessage.setText(randomJokeModel.getSetup() + " " + randomJokeModel.getPunchline());

                        sumOfToday++;

                    } else if (update.getMessage().getText().equals("cat fact") && isAvailable[1]) {///////////////////////////////////////////////////////////////////////////

                        sumOfRequests++;
                        requestsCounter[1]++;
                        id.setLestRequests("cat fact");

                        HttpResponse<String> response = Unirest.get("https://catfact.ninja/fact?max_length=140").asString();

                        ObjectMapper objectMapper = new ObjectMapper();
                        CatFactsModel catFactsModel = objectMapper.readValue(response.getBody(), CatFactsModel.class);


                        sendMessage.setText(catFactsModel.getFact());

                        sumOfToday++;

                    } else if (update.getMessage().getText().equals("numbers fact") && isAvailable[2]) {///////////////////////////////////////////////////////////////////////

                        sumOfRequests++;
                        requestsCounter[2]++;
                        id.setLestRequests("numbers fact");

                        Random random = new Random();

                        int num = random.nextInt();

                        HttpResponse<String> response = Unirest.get("http://numbersapi.com/" + num + "/math?callback=showNumber").asString();

                        sendMessage.setText(response.getBody());

                        sumOfToday++;

                    } else if (update.getMessage().getText().equals("random quote") && isAvailable[3]) {///////////////////////////////////////////////////////////////////////

                        sumOfRequests++;
                        requestsCounter[3]++;
                        id.setLestRequests("random quote");

                        HttpResponse<String> response = Unirest.get("https://api.quotable.io/random").asString();

                        ObjectMapper objectMapper = new ObjectMapper();
                        RandomQuoteFactModel randomQuoteFactModel = objectMapper.readValue(response.getBody(), RandomQuoteFactModel.class);

                        sendMessage.setText(randomQuoteFactModel.getContent() + "\n" + randomQuoteFactModel.getAuthor());

                        sumOfToday++;

                    } else if (update.getMessage().getText().equals("country") && isAvailable[4]) {///////////////////////////////////////////////////////////////////////////

                        sumOfRequests++;
                        requestsCounter[4]++;

                        id.setLestRequests("country");

                        sendMessage.setText("Enter the alpha code");

                    } else if (id.getLestRequests().equals("country") && isAvailable[4]) {

                        String country = update.getMessage().getText();

                        HttpResponse<String> response = Unirest.get("https://restcountries.com/v2/alpha/" + country).asString();

                        ObjectMapper objectMapper = new ObjectMapper();
                        CountryModel countryModel = objectMapper.readValue(response.getBody(), CountryModel.class);

                        String border = "";
                        for (int i = 0; i < countryModel.getBorders().size(); i++) {
                            border += countryModel.getBorders().get(i) + ", ";
                        }
                        sendMessage.setText("name: " + countryModel.getName() + "\ncapital: " + countryModel.getCapital() + "\npopulation: " + countryModel.getPopulation() + "\nborder: " + border);

                        sumOfToday++;

                    } else {/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        sendMessage.setText("Something is wrong");
                    }
                } catch (Exception e) {
                }
            }
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public Id[] getId() {

        Id[] ids = new Id[10];

        for (int i = 0; i < ids.length; i++) {
            if (idArrayList.size() - 1 >= i) {
                ids[i] = idArrayList.get(idArrayList.size() - i - 1);
            } else {
                ids[i] = null;
            }
        }

        return ids;
    }

    public int getSumOfRequests() {
        return sumOfRequests;
    }

    public String getMostPopularRequest() {

        int max = 0;
        String mostPopularRequest = "";

        for (int i = 0; i < requestsCounter.length; i++) {
            if (requestsCounter[i] > max) {
                max = requestsCounter[i];
                mostPopularRequest = option[i];
            }
        }
        return mostPopularRequest;
    }
    public int getSumOfUsers() {
        return Users.size();
    }
    public String getMostActiveUser() {

        int max = 0;
        String mostActiveUser = "";

        for (int i = 0; i < idArrayList.size(); i++) {
            if (idArrayList.get(i).getSumOfRequests() > max) {
                max = idArrayList.get(i).getSumOfRequests();
                mostActiveUser = idArrayList.get(i).getName();
            }
        }
        return mostActiveUser;
    }

    public int getSumOfToday() {
        return sumOfToday;
    }
    public void setSumOfToday(int sumOfToday) {
        this.sumOfToday = sumOfToday;
    }
    public int getSumOfYesterday() {
        return sumOfYesterday;
    }
    public void setSumOfYesterday(int sumOfYesterday) {
        this.sumOfYesterday = sumOfYesterday;
    }
    public int getSumOf2daysEgo() {
        return sumOf2daysEgo;
    }
    public void setSumOf2daysEgo(int sumOf2daysEgo) {
        this.sumOf2daysEgo = sumOf2daysEgo;
    }
}