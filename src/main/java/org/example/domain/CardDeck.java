package org.example.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private List<Card> cards;

    public CardDeck() {
        this.cards = new ArrayList<>();
        initializeCardDeck();
        shuffleDeck();
    }

    private final void initializeCardDeck() {
        String[] cardFaces = Constants.CARD_FACES.split(",");
        String[] cardSuits = Constants.CARD_SUITS.split(",");

        for (int i = 0; i < cardFaces.length; i++) {
            for (int j = 0; j < cardSuits.length; j++) {
                Card card = new Card(CardFace.valueOf(cardFaces[i]), CardSuit.valueOf(cardSuits[j]));
                cards.add(card);
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(this.cards);
    }

    public Card pickCard() {
        if (cards.size() <= 0) {
            throw new RuntimeException(Constants.ERROR_EMPTY_DECK);
        }
        return cards.remove(0);
    }
}
