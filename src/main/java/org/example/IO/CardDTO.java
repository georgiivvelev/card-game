package org.example.IO;

import org.example.domain.CardFace;

public class CardDTO {
    private String cardFace;
    private String cardSuit;

    public CardDTO() {
    }


    public String getCardFace() {
        return cardFace;
    }

    public void setCardFace(String cardFace) {
        if (cardFace.equals(CardFace.J.name()) || cardFace.equals(CardFace.Q.name())
                || cardFace.equals(CardFace.K.name()) || cardFace.equals(CardFace.A.name())) {
            this.cardFace = cardFace;
        } else {
            this.cardFace = String.valueOf(CardFace.valueOf(cardFace).getValue());
        }

    }

    public String getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(String cardSuit) {
        this.cardSuit = cardSuit;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
