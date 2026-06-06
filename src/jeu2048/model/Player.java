package jeu2048.model;

/**
 * MODÈLE — Représente un joueur dans le mode 2 joueurs.
 * Chaque joueur possède un nom, un numéro et un score.
 * Pattern MVC : fait partie du Model.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class Player {

    /** Nom du joueur */
    private String name;

    /** Score accumulé du joueur */
    private int score;

    /** Numéro du joueur (1 ou 2) */
    private int number;

    /**
     * Constructeur — initialise un joueur.
     * @param name   nom affiché du joueur
     * @param number numéro du joueur (1 ou 2)
     */
    public Player(String name, int number) {
        this.name   = name;
        this.number = number;
        this.score  = 0;
    }

    /**
     * @return le nom du joueur
     */
    public String getName() { return name; }

    /**
     * @return le score actuel du joueur
     */
    public int getScore() { return score; }

    /**
     * @return le numéro du joueur
     */
    public int getNumber() { return number; }

    /**
     * Ajoute des points au score du joueur.
     * @param points points à ajouter
     */
    public void addScore(int points) {
        score += points;
    }

    /** Remet le score du joueur à zéro */
    public void reset() { score = 0; }

    /**
     * @return représentation textuelle du joueur
     */
    @Override
    public String toString() {
        return "Joueur " + number + 
               " (" + name + ") : " + score;
    }
}