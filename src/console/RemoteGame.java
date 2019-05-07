/*
 * Code used in the "Software Engineering" course.
 *
 * Copyright 2017 by Claudio Cusano (claudio.cusano@unipv.it)
 * Dept of Electrical, Computer and Biomedical Engineering,
 * University of Pavia.
 */
package console;

import hangman.ArtificialPlayer;
import hangman.Hangman;
import hangman.Player;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Class for the playing without a network.
 *
 * @author Claudio Cusano <claudio.cusano@unipv.it>
 */
public class RemoteGame {
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Play the game with the terminal.
     *
     */
    public RemoteGame(PrintWriter writer, BufferedReader reader) {
        this.reader = reader;
        this.writer = writer;
        Hangman game = new Hangman();
        Player player = new RemotePlayer(this.writer, this.reader);
        // Player player = new ArtificialPlayer();
        game.playGame(player);
    }
}