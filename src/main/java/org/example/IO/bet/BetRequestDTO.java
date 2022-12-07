package org.example.IO.bet;

public class BetRequestDTO {
    private double bet;
    private boolean isHigher;

    public BetRequestDTO() {
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public boolean getIsHigher() {
        return isHigher;
    }

    public void setHigher(boolean higher) {
        isHigher = higher;
    }
}
