package jeu2048.model;

import jeu2048.controller.AccountManager;

/**
 * MODÈLE (MVC) — Gère le score de la partie en cours
 * et le meilleur score du joueur connecté.
 * Interagit avec AccountManager pour persister
 * le meilleur score en base de données MySQL.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class ScoreManager {

    /** Score de la partie en cours */
    private int score;

    /** Meilleur score du joueur connecté */
    private int bestScore;

    /** Gestionnaire de comptes pour la persistance */
    private AccountManager accountManager;

    /** Nom d'utilisateur du joueur connecté */
    private String currentUser;

    /**
     * Constructeur — initialise les scores à zéro.
     */
    public ScoreManager() {
        this.score = 0;
        this.bestScore = 0;
    }

    /**
     * Configure le joueur connecté et charge son
     * meilleur score depuis la base de données.
     * @param username       nom d'utilisateur
     * @param accountManager gestionnaire de comptes
     */
    public void setUser(String username,
                        AccountManager accountManager) {
        this.currentUser = username;
        this.accountManager = accountManager;
        // Charger le meilleur score depuis la BDD
        this.bestScore = accountManager
            .getBestScore(username);
    }

    /**
     * Ajoute des points au score courant.
     * Met à jour le meilleur score en BDD si nécessaire.
     * @param points points à ajouter (valeur de la fusion)
     */
    public void addPoints(int points) {
        score += points;
        // Vérifier si c'est un nouveau record
        if (score > bestScore) {
            bestScore = score;
            // Sauvegarder en base de données
            if (accountManager != null
                    && currentUser != null) {
                accountManager.updateBestScore(
                    currentUser, bestScore);
            }
        }
    }

    /**
     * Remet le score courant à zéro
     * (utilisé pour Nouveau Jeu).
     */
    public void reset() { score = 0; }

    /**
     * @return score de la partie en cours
     */
    public int getScore() { return score; }

    /**
     * @return meilleur score enregistré du joueur
     */
    public int getBestScore() { return bestScore; }
}