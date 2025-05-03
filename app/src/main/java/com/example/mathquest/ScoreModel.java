package com.example.mathquest;

public class ScoreModel {
    private String player1Name;
    private int player1Score;
    private String player2Name;
    private int player2Score;
    private String date;

    public ScoreModel(String player1Name, int player1Score, String player2Name, int player2Score, String date) {
        this.player1Name = player1Name;
        this.player1Score = player1Score;
        this.player2Name = player2Name;
        this.player2Score = player2Score;
        this.date = date;
    }

    public String getPlayer1Name() { return player1Name; }
    public int getPlayer1Score() { return player1Score; }
    public String getPlayer2Name() { return player2Name; }
    public int getPlayer2Score() { return player2Score; }
    public String getDate() { return date; }
}

