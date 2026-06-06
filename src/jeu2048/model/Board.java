package jeu2048.model;

import java.util.Random;

/**
 * MODÈLE (MVC) — Représente la grille de jeu 4x4.
 * Gère les déplacements (haut, bas, gauche, droite)
 * et les fusions des tuiles identiques.
 * Notifie le ScoreManager lors de chaque fusion.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class Board {

    /** Grille de tuiles 4x4 */
    private Tile[][] tiles;

    /** Gestionnaire de score — notifié à chaque fusion */
    private ScoreManager scoreManager;

    /** Taille fixe de la grille */
    private static final int SIZE = 4;

    /** Indique si une fusion a eu lieu lors du dernier coup */
    private boolean mergeHappened = false;

    /**
     * Constructeur — initialise la grille vide et place
     * deux tuiles aléatoires pour démarrer la partie.
     * @param scoreManager gestionnaire de score
     */
    public Board(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        tiles = new Tile[SIZE][SIZE];
        // Initialiser toutes les cases à 0 (vide)
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                tiles[i][j] = new Tile(0);
        // Placer deux tuiles de départ
        addRandomTile();
        addRandomTile();
    }

    /**
     * Retourne la grille de tuiles.
     * @return tableau 2D de tuiles
     */
    public Tile[][] getTiles() { return tiles; }

    /**
     * Ajoute une tuile aléatoire (90% chance = 2,
     * 10% chance = 4) dans une case vide aléatoire.
     */
    public void addRandomTile() {
        Random rand = new Random();
        int r, c;
        // Chercher une case vide aléatoirement
        do {
            r = rand.nextInt(SIZE);
            c = rand.nextInt(SIZE);
        } while (!tiles[r][c].isEmpty());
        // 90% de chance d'avoir un 2, 10% un 4
        tiles[r][c].setValue(rand.nextInt(10) < 9 ? 2 : 4);
    }

    /**
     * Remet la grille à zéro et place deux nouvelles
     * tuiles aléatoires (utilisé pour Nouveau Jeu).
     */
    public void reset() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                tiles[i][j].setValue(0);
        addRandomTile();
        addRandomTile();
    }

    /**
     * Déplace toutes les tuiles vers la gauche
     * et fusionne les tuiles identiques adjacentes.
     * @return true si au moins une tuile a bougé
     */
    public boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int[] row = getRow(i);
            int[] merged = merge(row);
            if (!rowEquals(row, merged)) moved = true;
            setRow(i, merged);
        }
        return moved;
    }

    /**
     * Déplace toutes les tuiles vers la droite
     * et fusionne les tuiles identiques adjacentes.
     * @return true si au moins une tuile a bougé
     */
    public boolean moveRight() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int[] row = getRow(i);
            // Inverser pour réutiliser merge() vers gauche
            int[] reversed = reverse(row);
            int[] merged = merge(reversed);
            if (!rowEquals(reversed, merged)) moved = true;
            setRow(i, reverse(merged));
        }
        return moved;
    }

    /**
     * Déplace toutes les tuiles vers le haut
     * et fusionne les tuiles identiques adjacentes.
     * @return true si au moins une tuile a bougé
     */
    public boolean moveUp() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            int[] col = getCol(j);
            int[] merged = merge(col);
            if (!rowEquals(col, merged)) moved = true;
            setCol(j, merged);
        }
        return moved;
    }

    /**
     * Déplace toutes les tuiles vers le bas
     * et fusionne les tuiles identiques adjacentes.
     * @return true si au moins une tuile a bougé
     */
    public boolean moveDown() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            int[] col = getCol(j);
            // Inverser pour réutiliser merge() vers haut
            int[] reversed = reverse(col);
            int[] merged = merge(reversed);
            if (!rowEquals(reversed, merged)) moved = true;
            setCol(j, reverse(merged));
        }
        return moved;
    }

    /**
     * Algorithme de fusion d'une ligne vers la gauche.
     * Les zéros sont ignorés, les tuiles identiques
     * adjacentes sont fusionnées et le score est mis à jour.
     * @param line tableau de valeurs à fusionner
     * @return nouvelle ligne après fusion
     */
    private int[] merge(int[] line) {
        int[] result = new int[SIZE];
        int pos = 0;
        for (int i = 0; i < SIZE; i++) {
            // Ignorer les cases vides
            if (line[i] == 0) continue;
            // Fusionner si la tuile précédente est identique
            if (pos > 0 && result[pos-1] == line[i]) {
                result[pos-1] *= 2;
                // Notifier le score de la fusion
                scoreManager.addPoints(result[pos-1]);
                mergeHappened = true;
            } else {
                result[pos++] = line[i];
            }
        }
        return result;
    }

    // ── Méthodes utilitaires privées ──────────────────────

    /**
     * Extrait une ligne de la grille sous forme de tableau.
     * @param i index de la ligne
     * @return tableau des valeurs de la ligne
     */
    private int[] getRow(int i) {
        int[] row = new int[SIZE];
        for (int j = 0; j < SIZE; j++)
            row[j] = tiles[i][j].getValue();
        return row;
    }

    /**
     * Réinsère une ligne modifiée dans la grille.
     * @param i   index de la ligne
     * @param row nouvelles valeurs
     */
    private void setRow(int i, int[] row) {
        for (int j = 0; j < SIZE; j++)
            tiles[i][j].setValue(row[j]);
    }

    /**
     * Extrait une colonne de la grille.
     * @param j index de la colonne
     * @return tableau des valeurs de la colonne
     */
    private int[] getCol(int j) {
        int[] col = new int[SIZE];
        for (int i = 0; i < SIZE; i++)
            col[i] = tiles[i][j].getValue();
        return col;
    }

    /**
     * Réinsère une colonne modifiée dans la grille.
     * @param j   index de la colonne
     * @param col nouvelles valeurs
     */
    private void setCol(int j, int[] col) {
        for (int i = 0; i < SIZE; i++)
            tiles[i][j].setValue(col[i]);
    }

    /**
     * Inverse un tableau (utilisé pour moveRight/moveDown).
     * @param arr tableau à inverser
     * @return nouveau tableau inversé
     */
    private int[] reverse(int[] arr) {
        int[] r = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            r[i] = arr[arr.length-1-i];
        return r;
    }

    /**
     * Compare deux tableaux élément par élément.
     * @param a premier tableau
     * @param b deuxième tableau
     * @return true si les tableaux sont identiques
     */
    private boolean rowEquals(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != b[i]) return false;
        return true;
    }
}