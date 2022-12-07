package org.example.domain;

public class Card implements Comparable<Card> {
    private final CardFace cardFace;
    private final CardSuit cardSuit;

    public Card(CardFace cardFace, CardSuit cardSuit) {
        this.cardFace = cardFace;
        this.cardSuit = cardSuit;
    }

    public CardFace getCardFace() {
        return cardFace;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (cardFace != card.cardFace) return false;
        return cardSuit == card.cardSuit;
    }

    @Override
    public int hashCode() {
        int result = cardFace.hashCode();
        result = 31 * result + cardSuit.hashCode();
        return result;
    }

    @Override
    public int compareTo(Card o) {
        return this.cardFace.getValue() - o.cardFace.getValue();
    }
}
