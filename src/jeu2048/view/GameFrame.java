package jeu2048.view;

import jeu2048.controller.AccountManager;
import jeu2048.controller.DatabaseManager;
import jeu2048.controller.KeyHandler;
import jeu2048.controller.SoundManager;
import jeu2048.model.Board;
import jeu2048.model.ScoreManager;
import jeu2048.model.Tile;
import javax.swing.*;
import java.awt.*;

/**
 * VUE (MVC) — Fenêtre principale de l'application.
 * Gère la navigation entre la page d'accueil et le jeu
 * via un CardLayout. Contient la barre de score,
 * les boutons de navigation et le panneau de jeu.
 * Pattern MVC : fait partie de la View.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class GameFrame extends JFrame {

    /** Layout pour naviguer entre les écrans */
    private final CardLayout cardLayout;

    /** Panneau principal contenant tous les écrans */
    private final JPanel mainPanel;

    /** Panneau graphique du jeu */
    private final GamePanel gamePanel;

    /** Référence à la grille (tableau pour mutabilité) */
    private final Board[] boardRef;

    /** Gestionnaire de score */
    private final ScoreManager scoreManager;

    /** Gestionnaire de comptes utilisateurs */
    private final AccountManager accountManager;

    /** Nom de l'utilisateur connecté */
    private String currentUser;

    /** Gestionnaire des touches clavier */
    private KeyHandler keyHandler;

    /** Gestionnaire de sons */
    private SoundManager soundManager;

    /** Label du score courant */
    private JLabel scoreLabel;

    /** Label du meilleur score */
    private JLabel bestLabel;

    /** Barre des boutons (haut) */
    private JPanel topBar;

    /** Barre du score (bas du header) */
    private JPanel scoreBar;

    /** Panel header complet */
    private JPanel headerPanel;

    /** Label du mode de jeu (centre) */
    private JLabel modeLabel;

    /**
     * Constructeur — initialise la fenêtre principale,
     * crée les composants et affiche la page d'accueil.
     * @param scoreManager gestionnaire de score
     * @param db           gestionnaire de BDD
     * @param sound        gestionnaire de sons
     */
    public GameFrame(ScoreManager scoreManager,
                     DatabaseManager db,
                     SoundManager sound) {
        this.scoreManager = scoreManager;
        this.soundManager = sound;
        setTitle("Jeu 2048");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);

        // Initialiser la grille et le panneau de jeu
        boardRef  = new Board[]{new Board(scoreManager)};
        gamePanel = new GamePanel(boardRef[0], scoreManager);
        gamePanel.setFocusable(true);

        // Initialiser le gestionnaire de comptes
        accountManager = new AccountManager(
            db.getConnection());

        // Créer et ajouter les deux écrans
        WelcomePanel welcomePanel = new WelcomePanel(
            accountManager, this);
        mainPanel.add(welcomePanel, "welcome");
        mainPanel.add(buildGamePanel(), "game");

        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Enregistre le KeyHandler et l'ajoute au panneau.
     * @param kh gestionnaire de touches
     */
    public void setKeyHandler(KeyHandler kh) {
        this.keyHandler = kh;
        gamePanel.addKeyListener(kh);
    }

    /**
     * Construit le panneau de jeu avec le header
     * (boutons + score) et la grille.
     * @return panneau de jeu complet
     */
    private JPanel buildGamePanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(0xF5F0EB));

        // ── Header ──────────────────────────────────────
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0xF5F0EB));
        headerPanel.setBorder(
            BorderFactory.createEmptyBorder(8, 15, 4, 15));

        // ── Ligne 1 : boutons navigation ──
        topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0xF5F0EB));

        // Bouton retour à l'accueil
        JButton backBtn = new JButton("← Accueil");
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.setBackground(new Color(0x7C6A5A));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusable(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            WelcomePanel wp = (WelcomePanel)
                mainPanel.getComponent(0);
            wp.showHomePanel(currentUser);
            cardLayout.show(mainPanel, "welcome");
        });

        // Bouton nouvelle partie
        JButton newGame = new JButton("Nouveau Jeu");
        newGame.setFont(new Font("Arial", Font.BOLD, 13));
        newGame.setBackground(new Color(0xB0A090));
        newGame.setForeground(Color.WHITE);
        newGame.setFocusable(false);
        newGame.setBorderPainted(false);
        newGame.setOpaque(true);
        newGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newGame.addActionListener(e -> {
            scoreManager.reset();
            boardRef[0].reset();
            updateScoreBar();
            gamePanel.repaint();
            gamePanel.requestFocus();
        });

        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(newGame, BorderLayout.EAST);

        // ── Ligne 2 : score + mode + meilleur ──
        scoreBar = new JPanel(new BorderLayout());
        scoreBar.setBackground(new Color(0xF5F0EB));
        scoreBar.setBorder(
            BorderFactory.createEmptyBorder(4, 0, 4, 0));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(
            new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(new Color(0x7C6A5A));

        // Label du mode au centre (timer, mouvements...)
        modeLabel = new JLabel("", SwingConstants.CENTER);
        modeLabel.setFont(
            new Font("Arial", Font.BOLD, 14));
        modeLabel.setForeground(new Color(0xF65E3B));

        bestLabel = new JLabel("Meilleur: 0");
        bestLabel.setFont(
            new Font("Arial", Font.BOLD, 18));
        bestLabel.setForeground(new Color(0x7C6A5A));
        bestLabel.setHorizontalAlignment(
            SwingConstants.RIGHT);

        scoreBar.add(scoreLabel, BorderLayout.WEST);
        scoreBar.add(modeLabel,  BorderLayout.CENTER);
        scoreBar.add(bestLabel,  BorderLayout.EAST);

        headerPanel.add(topBar,   BorderLayout.NORTH);
        headerPanel.add(scoreBar, BorderLayout.SOUTH);

        container.add(headerPanel, BorderLayout.NORTH);
        container.add(gamePanel,   BorderLayout.CENTER);
        return container;
    }

    /**
     * Met à jour les labels de score dans la barre.
     */
    public void updateScoreBar() {
        if (scoreLabel != null && bestLabel != null) {
            scoreLabel.setText("Score: "
                + scoreManager.getScore());
            bestLabel.setText("Meilleur: "
                + scoreManager.getBestScore());
        }
    }

    /**
     * Met à jour le label central (mode, timer...).
     * @param text  texte à afficher
     * @param color couleur du texte
     */
    public void updateModeLabel(String text, Color color) {
        if (modeLabel != null) {
            modeLabel.setText(text);
            modeLabel.setForeground(color);
        }
    }

    /**
     * Adapte les couleurs du header au thème actuel.
     * @param bg   couleur de fond
     * @param text couleur du texte
     */
    public void updateThemeColors(Color bg, Color text) {
        if (headerPanel != null) {
            headerPanel.setBackground(bg);
            topBar.setBackground(bg);
            scoreBar.setBackground(bg);
        }
        if (scoreLabel != null) {
            scoreLabel.setForeground(text);
            bestLabel.setForeground(text);
        }
        // Mettre à jour les boutons
        for (java.awt.Component c :
                topBar.getComponents()) {
            if (c instanceof JButton)
                c.setBackground(bg.darker());
        }
    }

    /**
     * Affiche l'écran de jeu pour l'utilisateur connecté.
     * Configure le ScoreManager avec l'utilisateur.
     * @param username nom de l'utilisateur
     */
    public void showGame(String username) {
        this.currentUser = username;
        // Lier le ScoreManager à l'utilisateur
        scoreManager.setUser(username, accountManager);
        updateScoreBar();
        cardLayout.show(mainPanel, "game");
        SwingUtilities.invokeLater(() ->
            gamePanel.requestFocusInWindow());
    }

    /**
     * Configure le mode de jeu.
     * En mode 2 joueurs, demande les noms des joueurs.
     * @param mode nom du mode de jeu
     */
    public void setGameMode(String mode) {
        if (mode.equals("2Joueurs")) {
            // Demander les noms des deux joueurs
            String name1 = JOptionPane.showInputDialog(
                this, "Nom du Joueur 1 :",
                "Joueur 1", JOptionPane.PLAIN_MESSAGE);
            if (name1 == null || name1.trim().isEmpty())
                name1 = "Joueur 1";

            String name2 = JOptionPane.showInputDialog(
                this, "Nom du Joueur 2 :",
                "Joueur 2", JOptionPane.PLAIN_MESSAGE);
            if (name2 == null || name2.trim().isEmpty())
                name2 = "Joueur 2";

            gamePanel.setMode(mode);
            gamePanel.initTwoPlayers(
                name1.trim(), name2.trim());
        } else {
            gamePanel.setMode(mode);
        }
    }

    // ── Getters ──────────────────────────────────────────

    /** @return panneau graphique du jeu */
    public GamePanel getGamePanel() { return gamePanel; }

    /** @return référence à la grille */
    public Board[] getBoardRef() { return boardRef; }

    /** @return nom de l'utilisateur connecté */
    public String getCurrentUser() { return currentUser; }

    /** @return gestionnaire de comptes */
    public AccountManager getAccountManager() {
        return accountManager;
    }

    /** @return gestionnaire de score */
    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    /** @return gestionnaire de sons */
    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * Vérifie si une partie est en cours.
     * Utilisé pour afficher le bouton "Continuer".
     * @return true si la grille contient des tuiles
     */
    public boolean isGameInProgress() {
        for (Tile[] row : boardRef[0].getTiles())
            for (Tile t : row)
                if (t.getValue() > 0) return true;
        return false;
    }
}