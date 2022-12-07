package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.IO.BalanceRequestDTO;
import org.example.IO.CardDTO;
import org.example.IO.bet.BetRequestDTO;
import org.example.IO.bet.BetResponseDTO;
import org.example.domain.Card;
import org.example.domain.CardDeck;
import org.example.domain.Constants;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class GameServer {
    private static CardDeck cardDeck;
    private static double playersBalance;
    private static Deque<Card> takenCards = new ArrayDeque<>();
    private static final ModelMapper modelMapper = new ModelMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    private static class StartHttpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (!"POST".equals(httpExchange.getRequestMethod())) {
                httpExchange.sendResponseHeaders(400, 0);
                return;
            }

            final BalanceRequestDTO inputPayload = objectMapper
                    .readValue(httpExchange.getRequestBody(), BalanceRequestDTO.class);

            playersBalance = inputPayload.getBalance();

            Card pickedCard = cardDeck.pickCard();
            takenCards = new ArrayDeque<>();
            takenCards.push(pickedCard);

            CardDTO cardDTO = modelMapper.map(pickedCard, CardDTO.class);

            byte[] output = objectMapper.writeValueAsBytes(cardDTO);
            httpExchange.sendResponseHeaders(200, output.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(output);
            outputStream.close();
        }

    }
    private static class BetHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (!"POST".equals(httpExchange.getRequestMethod())) {
                httpExchange.sendResponseHeaders(400, 0);
                return;
            }
            if (takenCards.isEmpty() || playersBalance == 0.0) {
                throw new RuntimeException(Constants.ERROR_EMPTY_TAKEN_CARDS_DEQUE);
            }

            BetRequestDTO betRequestDTO = objectMapper
                    .readValue(httpExchange.getRequestBody(), BetRequestDTO.class);


            if (!isBetCovered(betRequestDTO.getBet())) {
                throw new RuntimeException(Constants.ERROR_BET_IS_NOT_COVERED);
            }

            Card previouslyTakenCard = takenCards.peek();
            Card currentlyTakenCard = cardDeck.pickCard();

            int compareResult = currentlyTakenCard.compareTo(previouslyTakenCard);

            boolean betIsHigher = betRequestDTO.getIsHigher();

            BetResponseDTO betResponseDTO = new BetResponseDTO();
            betResponseDTO.setPreviousCard(modelMapper.map(previouslyTakenCard, CardDTO.class));
            betResponseDTO.setCurrentCard(modelMapper.map(currentlyTakenCard, CardDTO.class));
            betResponseDTO.setYouBetIsHigher(betRequestDTO.getIsHigher());

            if (compareResult == 0) {
                betResponseDTO.setBetResult(Constants.BetResult.NEUTRAL.name());
            } else {
                if (betIsHigher && compareResult > 0 || !betIsHigher && compareResult < 0) {
                    playersBalance += (betRequestDTO.getBet() * 2);
                    betResponseDTO.setBetResult(Constants.BetResult.SUCCESS.name());
                } else {
                    playersBalance -= betRequestDTO.getBet();
                    betResponseDTO.setBetResult(Constants.BetResult.FAIL.name());
                }
            }
            takenCards.push(currentlyTakenCard);
            betResponseDTO.setResidualBalance(playersBalance);


            byte[] output = objectMapper.writeValueAsBytes(betResponseDTO);
            httpExchange.sendResponseHeaders(200, output.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(output);
            outputStream.close();
        }

    }
    private static class ShuffleHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            takenCards.clear();
            cardDeck = new CardDeck();
            takenCards.push(cardDeck.pickCard());
        }
    }

    private static boolean isBetCovered(double bet) {
        return playersBalance >= bet;
    }

    public static void startServer() throws IOException {
        cardDeck = new CardDeck();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);

        httpServer.createContext(Constants.ENDPOINT_START, new StartHttpHandler());
        httpServer.createContext(Constants.ENDPOINT_SHUFFLE, new ShuffleHttpHandler());
        httpServer.createContext(Constants.ENDPOINT_BET, new BetHttpHandler());


        //this is for multiplayer , for single player may be omitted
        Executor executor = Executors.newFixedThreadPool(Constants.MAX_THREAD_ALLOWED);
        httpServer.setExecutor(executor);

        httpServer.start();
    }

}
