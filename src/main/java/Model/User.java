package Model;

import Controller.Controller;
import Controller.ManageData;

import java.util.ArrayList;

public class User {
    String username;
    String password;
    String nickname;
    String email;
    String slogan;
    String answer;
    int questionNumber;
    int highScore;

    public ArrayList<Trade> tradeList;

    public int lastTradeIndex;

    public User(String username, String password, String nickname, String email, String slogan, String answer, int questionNumber) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.answer = answer;
        this.questionNumber = questionNumber;
        this.highScore = 0;
        this.tradeList = new ArrayList<>();
        this.lastTradeIndex = -1;
    }

    public static void createUser(String username, String password, String nickname, String email, String slogan, String answer, int questionNumber) {
        User user = new User(username, ManageData.encrypt(password), nickname, email, slogan, answer, questionNumber);
        Controller.addUser(user);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getAnswer() {
        return answer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void addTrade(Trade trade) {
        this.tradeList.add(trade);
    }
}