package org.example.IO.bet;

import org.example.IO.CardDTO;

public class BetResponseDTO {
    private String betResult;
    private CardDTO previousCard;
    private CardDTO currentCard;

    private boolean youBetIsHigher;
    private double residualBalance;

    public BetResponseDTO() {
    }

    public String getBetResult() {
        return betResult;
    }

    public void setBetResult(String betResult) {
        this.betResult = betResult;
    }

    public CardDTO getPreviousCard() {
        return previousCard;
    }

    public void setPreviousCard(CardDTO previousCard) {
        this.previousCard = previousCard;
    }

    public CardDTO getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(CardDTO currentCard) {
        this.currentCard = currentCard;
    }

    public double getResidualBalance() {
        return residualBalance;
    }

    public void setResidualBalance(double residualBalance) {
        this.residualBalance = residualBalance;
    }

    public boolean getYouBetIsHigher() {
        return youBetIsHigher;
    }

    public void setYouBetIsHigher(boolean higher) {
        youBetIsHigher = higher;
    }
}
