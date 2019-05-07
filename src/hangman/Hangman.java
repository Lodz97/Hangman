/*
 * Code used in the "Software Engineering" course.
 *
 * Copyright 2017 by Claudio Cusano (claudio.cusano@unipv.it)
 * Dept of Electrical, Computer and Biomedical Engineering,
 * University of Pavia.
 */
package hangman;

/**
 * The controller.
 * 
 * @author Claudio Cusano <claudio.cusano@unipv.it>
 */
public class Hangman {
    Dictionary dict;
    
    /**
     * Initialize the object.
     */
    public Hangman() {
        dict = new Dictionary();
    }
    
    /**
     * Play a round of the game.
     * 
     * @param player the participant to the game
     */
    public void playGame(Player player) {
        System.out.println("play");
        Game game = new Game(dict.pickWord());
        game.addObserver(player);
        game.notifyObservers();
        while (game.getResult() == GameResult.OPEN) {
            System.out.println("choose");
            char c = player.chooseLetter(game);
            System.out.println("attempt");
            game.makeAttempt(c);
            System.out.println("postattempt");
        }
    }
}
