package jeu2048.model;

/**
 * MODÈLE (MVC) — Contient les règles du jeu 2048.
 * Vérifie les conditions de victoire (tuile 2048)
 * et de défaite (grille pleine sans fusion possible).
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class GameLogic {

    /** Référence à la grille de jeu */
    private Board board;

    /**
     * Constructeur.
     * @param board la grille de jeu à analyser
     */
    public GameLogic(Board board) {
        this.board = board;
    }

    /**
     * Vérifie si le joueur a atteint la tuile 2048.
     * Parcourt toutes les tuiles et cherche la valeur 2048.
     * @return true si une tuile vaut 2048
     */
    public boolean isWon() {
        for (Tile[] row : board.getTiles())
            for (Tile t : row)
                if (t.getValue() == 2048) return true;
        return false;
    }

    /**
     * Vérifie si la partie est terminée.
     * La partie est perdue si :
     * - La grille est complètement remplie ET
     * - Aucune fusion n'est possible (horizontale
     *   ou verticale)
     * @return true si aucun mouvement n'est possible
     */
    public boolean isGameOver() {
        Tile[][] tiles = board.getTiles();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Case vide → le jeu peut continuer
                if (tiles[i][j].isEmpty()) return false;
                // Fusion possible vers le bas
                if (i < 3 && tiles[i][j].getValue()
                        == tiles[i+1][j].getValue())
                    return false;
                // Fusion possible vers la droite
                if (j < 3 && tiles[i][j].getValue()
                        == tiles[i][j+1].getValue())
                    return false;
            }
        }
        // Grille pleine, aucune fusion possible
        return true;
    }
}