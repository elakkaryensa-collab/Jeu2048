package jeu2048.controller;

import java.sql.*;

/**
 * CONTRÔLEUR (MVC) — Gère la connexion à la base
 * de données MySQL et les opérations sur les scores.
 * Établit la connexion JDBC au démarrage et fournit
 * les méthodes de sauvegarde et lecture des scores.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class DatabaseManager {

    /** URL de connexion MySQL */
    private static final String URL =
        "jdbc:mysql://localhost:3306/jeu2048";

    /** Nom d'utilisateur MySQL */
    private static final String USER = "root";

    /** Mot de passe MySQL */
    private static final String PASSWORD =
        "anawissal2006";

    /** Connexion active à la base de données */
    private Connection conn;

    /**
     * Constructeur — établit la connexion MySQL
     * et crée la table scores si nécessaire.
     */
    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection(
                URL, USER, PASSWORD);
            createTable();
            System.out.println(
                "Base de donnees connectee !");
        } catch (SQLException e) {
            System.out.println(
                "Erreur DB: " + e.getMessage());
        }
    }

    /**
     * Crée la table scores en base de données
     * si elle n'existe pas encore.
     * Colonnes : id, score, date
     * @throws SQLException si erreur SQL
     */
    private void createTable() throws SQLException {
        String sql =
            "CREATE TABLE IF NOT EXISTS scores (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "score INT NOT NULL," +
            "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        conn.createStatement().execute(sql);
    }

    /**
     * Sauvegarde un score en base de données
     * avec la date et l'heure actuelles.
     * @param score score à enregistrer
     */
    public void saveScore(int score) {
        try {
            String sql =
                "INSERT INTO scores(score) VALUES(?)";
            PreparedStatement ps =
                conn.prepareStatement(sql);
            ps.setInt(1, score);
            ps.executeUpdate();
            System.out.println(
                "Score sauvegarde: " + score);
        } catch (SQLException e) {
            System.out.println(
                "Erreur save: " + e.getMessage());
        }
    }

    /**
     * Affiche dans la console les 5 meilleurs scores
     * enregistrés, triés par ordre décroissant.
     */
    public void printTopScores() {
        try {
            String sql =
                "SELECT score, date FROM scores " +
                "ORDER BY score DESC LIMIT 5";
            ResultSet rs = conn.createStatement()
                .executeQuery(sql);
            System.out.println("=== Top 5 Scores ===");
            int rank = 1;
            while (rs.next()) {
                System.out.println(rank +
                    ". Score: " + rs.getInt("score") +
                    " | Date: " + rs.getString("date"));
                rank++;
            }
        } catch (SQLException e) {
            System.out.println(
                "Erreur lecture: " + e.getMessage());
        }
    }

    /**
     * Ferme proprement la connexion à la base
     * de données.
     */
    public void close() {
        try {
            if (conn != null) conn.close();
            System.out.println("Connexion fermee");
        } catch (SQLException e) { }
    }

    /**
     * Retourne la connexion active.
     * Utilisée par AccountManager.
     * @return connexion JDBC active
     */
    public Connection getConnection() {
        return conn;
    }
}