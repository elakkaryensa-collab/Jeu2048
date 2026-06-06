package jeu2048.view;

import jeu2048.model.Board;
import jeu2048.model.Player;
import jeu2048.model.ScoreManager;
import jeu2048.model.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * VUE (MVC) — Panneau graphique principal du jeu.
 * Affiche la grille 4x4, les tuiles et leurs valeurs.
 * Gère les animations, les modes de jeu et le mode
 * 2 joueurs.
 * Pattern MVC : fait partie de la View.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class GamePanel extends JPanel {

    /** Grille de jeu — modèle */
    private Board board;

    /** Gestionnaire de score */
    private ScoreManager scoreManager;

    /** Gestionnaire de thème visuel */
    private ThemeManager themeManager;

    /** Taille d'une tuile en pixels */
    private static final int TILE_SIZE = 100;

    /** Espace entre les tuiles */
    private static final int GAP = 10;

    /** Échelles d'animation pour chaque tuile */
    private float[] tileScales;

    /** Mode de jeu actuel */
    private String gameMode = "Classique";

    /** Mouvements restants (mode Challenge) */
    private int movesLeft = 200;

    /** Total de mouvements effectués */
    private int totalMoves = 0;

    /** Temps restant en secondes (mode Time Attack) */
    private long timeLeft = 180;

    /** Temps écoulé depuis le début de la partie */
    private long elapsedTime = 0;

    /** Timer pour le compte à rebours (Time Attack) */
    private Timer gameTimer;

    /** Timer pour le temps écoulé */
    private Timer elapsedTimer;

    // ── Mode 2 joueurs ────────────────────────────────────
    /** Joueur 1 */
    private Player player1;

    /** Joueur 2 */
    private Player player2;

    /** Joueur dont c'est le tour */
    private Player currentPlayer;

    /** Score sauvegardé du joueur 1 */
    private int player1Score = 0;

    /** Score sauvegardé du joueur 2 */
    private int player2Score = 0;

    /** true si en attente de changement de joueur */
    private boolean waitingForSwitch = false;

    /**
     * Constructeur — initialise le panneau de jeu.
     * @param board        grille de jeu
     * @param scoreManager gestionnaire de score
     */
    public GamePanel(Board board,
                     ScoreManager scoreManager) {
        this.board = board;
        this.scoreManager = scoreManager;
        this.themeManager = new ThemeManager();
        tileScales = new float[16];
        for (int i = 0; i < 16; i++) tileScales[i] = 1.0f;
        setPreferredSize(new Dimension(460, 460));
        setFocusable(true);
        requestFocus();
    }

    /**
     * Configure le mode de jeu et initialise les timers.
     * Modes disponibles : Classique, Time Attack,
     * Challenge, 2Joueurs.
     * @param mode nom du mode de jeu
     */
    public void setMode(String mode) {
        this.gameMode  = mode;
        this.movesLeft = 200;
        this.totalMoves = 0;
        this.timeLeft  = 180;
        this.elapsedTime = 0;
        this.waitingForSwitch = false;

        // Arrêter les timers existants
        if (gameTimer != null) gameTimer.stop();
        if (elapsedTimer != null) elapsedTimer.stop();

        // Timer temps écoulé — actif dans tous les modes
        elapsedTimer = new Timer(1000, e -> {
            elapsedTime++;
            updateHeader();
            repaint();
        });
        elapsedTimer.start();

        // Timer compte à rebours — mode Time Attack
        if (mode.equals("Time Attack")) {
            gameTimer = new Timer(1000, e -> {
                timeLeft--;
                updateHeader();
                repaint();
                if (timeLeft <= 0) {
                    gameTimer.stop();
                    elapsedTimer.stop();
                    showMessage("Temps ecoule ! Score : "
                        + scoreManager.getScore());
                }
            });
            gameTimer.start();
        }
        updateHeader();
        repaint();
    }

    /**
     * Initialise le mode 2 joueurs avec les noms.
     * @param name1 nom du joueur 1
     * @param name2 nom du joueur 2
     */
    public void initTwoPlayers(String name1, String name2) {
        player1 = new Player(name1, 1);
        player2 = new Player(name2, 2);
        currentPlayer = player1;
        player1Score  = 0;
        player2Score  = 0;
        waitingForSwitch = false;
        repaint();
    }

    /**
     * Passe au joueur suivant en mode 2 joueurs.
     * Sauvegarde le score du joueur courant.
     */
    public void switchPlayer() {
        if (currentPlayer == player1) {
            player1Score  = scoreManager.getScore();
            currentPlayer = player2;
        } else {
            player2Score  = scoreManager.getScore();
            currentPlayer = player1;
        }
        scoreManager.reset();
        waitingForSwitch = false;
        repaint();
    }

    /**
     * Met à jour la barre d'informations dans GameFrame
     * selon le mode de jeu actuel.
     */
    private void updateHeader() {
        JFrame frame = (JFrame) SwingUtilities
            .getWindowAncestor(this);
        if (!(frame instanceof GameFrame)) return;
        GameFrame gf = (GameFrame) frame;

        String centerText;
        Color  centerColor = new Color(0x7C6A5A);

        if (gameMode.equals("Time Attack")) {
            // Afficher le compte à rebours + mouvements
            long min = timeLeft / 60;
            long sec = timeLeft % 60;
            centerText = String.format(
                "%02d:%02d  |  Mvts: %d",
                min, sec, totalMoves);
            centerColor = timeLeft <= 30 ?
                Color.RED : new Color(0xF65E3B);

        } else if (gameMode.equals("Challenge")) {
            // Afficher mouvements restants + temps
            centerText = String.format(
                "Mvts restants: %d  |  %ds",
                movesLeft, elapsedTime);
            centerColor = movesLeft <= 20 ?
                Color.RED : new Color(0xF65E3B);

        } else if (gameMode.equals("2Joueurs") &&
                   currentPlayer != null) {
            // Afficher le joueur courant
            centerText = "Tour de : " +
                currentPlayer.getName();
            centerColor = new Color(0xF65E3B);

        } else {
            // Mode classique : temps + mouvements
            centerText = String.format(
                "%ds  |  Mvts: %d",
                elapsedTime, totalMoves);
            centerColor = new Color(0x9E8E7E);
        }

        gf.updateModeLabel(centerText, centerColor);
        gf.updateScoreBar();
    }

    /**
     * Décrémente le compteur de mouvements.
     * En mode Challenge, vérifie si la limite est
     * atteinte. Incrémente toujours le total de coups.
     */
    public void decrementMoves() {
        totalMoves++;
        if (gameMode.equals("Challenge")) {
            movesLeft--;
            updateHeader();
            if (movesLeft <= 0) {
                if (elapsedTimer != null)
                    elapsedTimer.stop();
                showMessage(
                    "Plus de mouvements ! Score : "
                    + scoreManager.getScore());
            }
        }
        updateHeader();
    }

    /**
     * Change le thème visuel du panneau.
     * @param theme nouveau thème
     */
    public void setTheme(ThemeManager.Theme theme) {
        themeManager.setTheme(theme);
        setBackground(themeManager.getBackgroundColor());
        repaint();
    }

    /**
     * Anime l'apparition d'une tuile (effet de zoom).
     * @param row ligne de la tuile
     * @param col colonne de la tuile
     */
    public void animateTile(int row, int col) {
        int idx = row * 4 + col;
        tileScales[idx] = 0.5f;
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tileScales[idx] += 0.1f;
                if (tileScales[idx] >= 1.0f) {
                    tileScales[idx] = 1.0f;
                    timer.stop();
                }
                repaint();
            }
        });
        timer.start();
    }

    /**
     * @return score courant du joueur
     */
    public int getScore() {
        return scoreManager.getScore();
    }

    /**
     * Dessine la grille, les tuiles et les overlays.
     * Appelé automatiquement par Swing à chaque repaint().
     * @param g contexte graphique
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond de la grille
        g2.setColor(themeManager.getBackgroundColor());
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Notifier GameFrame pour màj score et couleurs
        SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities
                .getWindowAncestor(this);
            if (frame instanceof GameFrame) {
                ((GameFrame) frame).updateScoreBar();
                ((GameFrame) frame).updateThemeColors(
                    themeManager.getBackgroundColor(),
                    themeManager.getScoreTextColor());
            }
        });

        // ── Dessin des tuiles ──────────────────────────
        Tile[][] tiles = board.getTiles();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int x = GAP + j * (TILE_SIZE + GAP);
                int y = GAP + i * (TILE_SIZE + GAP);
                int val = tiles[i][j].getValue();
                float scale = tileScales[i * 4 + j];

                // Animation zoom
                int scaledSize = (int)(TILE_SIZE * scale);
                int offset = (TILE_SIZE - scaledSize) / 2;

                // Fond de la tuile
                g2.setColor(
                    themeManager.getTileColor(val));
                g2.fillRoundRect(
                    x + offset, y + offset,
                    scaledSize, scaledSize, 12, 12);

                // Valeur numérique de la tuile
                if (val != 0) {
                    g2.setColor(
                        themeManager.getTextColor(val));
                    int fontSize = val >= 1000 ? 22 :
                                   val >= 100  ? 26 : 32;
                    g2.setFont(new Font("Arial",
                        Font.BOLD, fontSize));
                    FontMetrics fm = g2.getFontMetrics();
                    String text = String.valueOf(val);
                    int tx = x + (TILE_SIZE -
                        fm.stringWidth(text)) / 2;
                    int ty = y + (TILE_SIZE +
                        fm.getAscent()) / 2 - 4;
                    g2.drawString(text, tx, ty);
                }
            }
        }

        // ── Overlay mode 2 joueurs ─────────────────────
        if (gameMode.equals("2Joueurs") &&
            player1 != null && waitingForSwitch) {
            // Fond semi-transparent
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Message changement de joueur
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            String msg = "Appuie sur ENTRÉE pour " +
                "passer au joueur suivant !";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(msg,
                (getWidth() - fm.stringWidth(msg)) / 2,
                getHeight() / 2);
        }
    }

    /**
     * Affiche une boîte de dialogue avec un message.
     * @param msg message à afficher
     */
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // ── Getters mode 2 joueurs ────────────────────────────

    /** @return mode de jeu actuel */
    public String getGameMode() { return gameMode; }

    /** @return joueur 1 */
    public Player getPlayer1() { return player1; }

    /** @return joueur 2 */
    public Player getPlayer2() { return player2; }

    /** @return joueur courant */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /** @return score sauvegardé du joueur 1 */
    public int getPlayer1Score() { return player1Score; }

    /** @return score sauvegardé du joueur 2 */
    public int getPlayer2Score() { return player2Score; }

    /** @return true si en attente de changement */
    public boolean isWaitingForSwitch() {
        return waitingForSwitch;
    }

    /** @param w état d'attente de changement */
    public void setWaitingForSwitch(boolean w) {
        this.waitingForSwitch = w;
    }

    /** @return gestionnaire de thème */
    public ThemeManager getThemeManager() {
        return themeManager;
    }
}