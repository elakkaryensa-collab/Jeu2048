package jeu2048.controller;

import java.sql.*;

/**
 * CONTRÔLEUR (MVC) — Gère les comptes utilisateurs.
 * Fournit les opérations d'inscription, connexion,
 * et gestion des meilleurs scores en base de données.
 * Communique avec MySQL via JDBC.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class AccountManager {

    /** Connexion active à la base de données */
    private Connection conn;

    /**
     * Constructeur — initialise la connexion et
     * crée la table users si elle n'existe pas.
     * @param conn connexion MySQL active
     */
    public AccountManager(Connection conn) {
        this.conn = conn;
        createTable();
    }

    /**
     * Crée la table users en base de données
     * si elle n'existe pas encore.
     * Colonnes : id, username, password, best_score
     */
    private void createTable() {
        try {
            String sql =
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password VARCHAR(50) NOT NULL," +
                "best_score INT DEFAULT 0)";
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            System.out.println("Erreur table users: "
                + e.getMessage());
        }
    }

    /**
     * Inscrit un nouvel utilisateur en base de données.
     * Échoue si le pseudo est déjà pris (contrainte UNIQUE).
     * @param username pseudo choisi
     * @param password mot de passe
     * @return true si l'inscription a réussi
     */
    public boolean register(String username,
                            String password) {
        try {
            String sql =
                "INSERT INTO users(username, password)" +
                " VALUES(?, ?)";
            PreparedStatement ps =
                conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Pseudo déjà existant
            return false;
        }
    }

    /**
     * Vérifie les identifiants d'un utilisateur.
     * @param username pseudo saisi
     * @param password mot de passe saisi
     * @return true si les identifiants sont corrects
     */
    public boolean login(String username,
                         String password) {
        try {
            String sql =
                "SELECT * FROM users WHERE " +
                "username=? AND password=?";
            PreparedStatement ps =
                conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Récupère le meilleur score d'un utilisateur.
     * @param username pseudo du joueur
     * @return meilleur score enregistré, 0 si introuvable
     */
    public int getBestScore(String username) {
        try {
            String sql =
                "SELECT best_score FROM users " +
                "WHERE username=?";
            PreparedStatement ps =
                conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("best_score");
        } catch (SQLException e) { }
        return 0;
    }

    /**
     * Met à jour le meilleur score si le nouveau
     * score est supérieur à l'ancien.
     * @param username pseudo du joueur
     * @param score    nouveau score à comparer
     */
    public void updateBestScore(String username,
                                int score) {
        try {
            // Mise à jour uniquement si amélioration
            String sql =
                "UPDATE users SET best_score=? " +
                "WHERE username=? AND best_score<?";
            PreparedStatement ps =
                conn.prepareStatement(sql);
            ps.setInt(1, score);
            ps.setString(2, username);
            ps.setInt(3, score);
            ps.executeUpdate();
        } catch (SQLException e) { }
    }

    /**
     * Retourne le meilleur score global
     * parmi tous les joueurs enregistrés.
     * @return score maximum global
     */
    public int getGlobalBestScore() {
        try {
            String sql =
                "SELECT MAX(best_score) AS global_best" +
                " FROM users";
            ResultSet rs = conn.createStatement()
                .executeQuery(sql);
            if (rs.next())
                return rs.getInt("global_best");
        } catch (SQLException e) {
            System.out.println(
                "Erreur global best score: "
                + e.getMessage());
        }
        return 0;
    }

    /**
     * Retourne le nom du joueur avec le meilleur
     * score global.
     * @return nom du meilleur joueur, "-" si vide
     */
    public String getGlobalBestPlayer() {
        try {
            String sql =
                "SELECT username FROM users " +
                "ORDER BY best_score DESC LIMIT 1";
            ResultSet rs = conn.createStatement()
                .executeQuery(sql);
            if (rs.next())
                return rs.getString("username");
        } catch (SQLException e) {
            System.out.println(
                "Erreur global best player: "
                + e.getMessage());
        }
        return "-";
    }

    /**
     * Retourne la connexion active à la BDD.
     * Utilisé par d'autres classes (ex: GameFrame).
     * @return connexion JDBC active
     */
    public Connection getConnection() {
        return conn;
    }
}