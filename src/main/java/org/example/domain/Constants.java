package org.example.domain;

public class Constants {
    public static final String CARD_FACES = "TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,J,Q,K,A";
    public static final String CARD_SUITS = "CLUBS,DIAMONDS,HEARTS,SPADES";


    public static final int  MAX_THREAD_ALLOWED = 10;

    //Errors
    public static final String ERROR_EMPTY_DECK = "Deck is empty";
    public static final String ERROR_EMPTY_TAKEN_CARDS_DEQUE = "No Cards Previously Taken";
    public static final String ERROR_BET_IS_NOT_COVERED = "Balance is not enough to cover the bet";

    //Endpoints
    public static final String ENDPOINT_START = "/start";
    public static final String ENDPOINT_SHUFFLE = "/shuffle";
    public static final String ENDPOINT_BET = "/bet";

    // Bet Operation Result
    public static enum BetResult {
        SUCCESS, FAIL, NEUTRAL
    }
}
