package jeu2048.model;

/**
 * MODÈLE (MVC) — Représente une tuile de la grille 2048.
 * Une tuile contient une valeur numérique (2, 4, 8...).
 * Une valeur de 0 signifie que la case est vide.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class Tile {

    /** Valeur numérique de la tuile (0 = case vide) */
    private int value;

    /**
     * Constructeur — crée une tuile avec une valeur donnée.
     * @param value valeur initiale de la tuile
     */
    public Tile(int value) {
        this.value = value;
    }

    /**
     * Retourne la valeur de la tuile.
     * @return valeur numérique de la tuile
     */
    public int getValue() { return value; }

    /**
     * Modifie la valeur de la tuile.
     * @param value nouvelle valeur
     */
    public void setValue(int value) { this.value = value; }

    /**
     * Vérifie si la tuile est vide.
     * @return true si la valeur est 0
     */
    public boolean isEmpty() { return value == 0; }
}