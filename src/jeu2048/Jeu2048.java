package jeu2048;

import jeu2048.controller.SoundManager;
import jeu2048.controller.DatabaseManager;
import jeu2048.controller.KeyHandler;
import jeu2048.model.GameLogic;
import jeu2048.model.ScoreManager;
import jeu2048.view.GameFrame;

public class Jeu2048 {
    public static void main(String[] args) {
        ScoreManager scoreManager = new ScoreManager();
        DatabaseManager db = new DatabaseManager();
        SoundManager sound = new SoundManager();
        GameFrame frame = new GameFrame(
            scoreManager, db, sound);
        GameLogic logic = new GameLogic(
            frame.getBoardRef()[0]);
        KeyHandler kh = new KeyHandler(
            frame.getBoardRef()[0], logic,
            frame.getGamePanel(), db, sound);
        frame.setKeyHandler(kh);
    }
}