package jeu2048.controller;

import jeu2048.model.GameLogic;
import jeu2048.model.Board;
import jeu2048.model.Player;
import jeu2048.view.GamePanel;
import jeu2048.view.GameFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * CONTRÔLEUR (MVC) — Gère les entrées clavier du joueur.
 * Intercepte les touches fléchées pour déplacer les tuiles,
 * et la touche ENTRÉE pour alterner entre joueurs
 * en mode 2 joueurs.
 * Vérifie après chaque coup les conditions de
 * victoire et de défaite.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class KeyHandler extends KeyAdapter {

    /** La grille de jeu — modèle */
    private Board board;

    /** Logique du jeu (victoire/défaite) */
    private GameLogic logic;

    /** Le panneau graphique — vue */
    private GamePanel panel;

    /** Gestionnaire de base de données */
    private DatabaseManager db;

    /** Gestionnaire de sons */
    private SoundManager sound;

    /**
     * Constructeur — initialise tous les composants
     * nécessaires au contrôleur.
     * @param board  la grille de jeu
     * @param logic  la logique du jeu
     * @param panel  le panneau graphique
     * @param db     le gestionnaire de BDD
     * @param sound  le gestionnaire de sons
     */
    public KeyHandler(Board board, GameLogic logic,
                      GamePanel panel, DatabaseManager db,
                      SoundManager sound) {
        this.board = board;
        this.logic = logic;
        this.panel = panel;
        this.db    = db;
        this.sound = sound;
    }

    /**
     * Méthode appelée à chaque appui sur une touche.
     * Gère :
     * - Les touches fléchées (déplacements)
     * - La touche ENTRÉE (changement de joueur)
     * - La vérification victoire/défaite après chaque coup
     * @param e événement clavier
     */
    @Override
    public void keyPressed(KeyEvent e) {

        // ── Mode 2 joueurs : ENTRÉE pour changer ──────────
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (panel.getGameMode().equals("2Joueurs") &&
                panel.isWaitingForSwitch()) {
                // Réinitialiser la grille pour le joueur suivant
                board.reset();
                panel.switchPlayer();
                panel.repaint();
                return;
            }
        }

        // Bloquer les mouvements si en attente de switch
        if (panel.isWaitingForSwitch()) return;

        // ── Traitement des touches fléchées ───────────────
        boolean moved = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                moved = board.moveLeft();  break;
            case KeyEvent.VK_RIGHT:
                moved = board.moveRight(); break;
            case KeyEvent.VK_UP:
                moved = board.moveUp();    break;
            case KeyEvent.VK_DOWN:
                moved = board.moveDown();  break;
        }

        // ── Après un mouvement valide ──────────────────────
        if (moved) {
            // Ajouter une nouvelle tuile aléatoire
            board.addRandomTile();
            // Jouer le son de déplacement
            sound.playMove();
            // Décrémenter les mouvements (mode Challenge)
            panel.decrementMoves();

            // Mettre à jour le meilleur score en BDD
            JFrame frame = (JFrame) SwingUtilities
                .getWindowAncestor(panel);
            if (frame instanceof GameFrame) {
                GameFrame gf = (GameFrame) frame;
                gf.getAccountManager().updateBestScore(
                    gf.getCurrentUser(),
                    panel.getScore());
                gf.updateScoreBar();
            }
            panel.repaint();
        }

        // ── Vérification victoire ──────────────────────────
        if (logic.isWon()) {
            sound.playWin();
            db.saveScore(panel.getScore());

            if (panel.getGameMode().equals("2Joueurs")) {
                // Mode 2 joueurs : passer au joueur suivant
                panel.setWaitingForSwitch(true);
                panel.repaint();
            } else {
                panel.showMessage(
                    "Félicitations ! Tu as atteint 2048 !");
            }
        }

        // ── Vérification défaite ───────────────────────────
        if (logic.isGameOver()) {
            sound.playGameOver();
            db.saveScore(panel.getScore());

            if (panel.getGameMode().equals("2Joueurs")) {
                // Afficher les scores des deux joueurs
                Player p1 = panel.getPlayer1();
                Player p2 = panel.getPlayer2();
                String winner;
                if (panel.getPlayer1Score() >
                    panel.getPlayer2Score()) {
                    winner = p1.getName() + " gagne ! 🏆";
                } else if (panel.getPlayer2Score() >
                           panel.getPlayer1Score()) {
                    winner = p2.getName() + " gagne ! 🏆";
                } else {
                    winner = "Égalité ! 🤝";
                }
                panel.showMessage(
                    "Game Over !\n" +
                    p1.getName() + " : " +
                    panel.getPlayer1Score() + "\n" +
                    p2.getName() + " : " +
                    panel.getPlayer2Score() + "\n\n" +
                    winner);
            } else {
                db.printTopScores();
                panel.showMessage("Game Over ! Score : "
                    + panel.getScore());
            }
        }
    }
}