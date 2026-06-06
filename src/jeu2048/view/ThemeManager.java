package jeu2048.view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * VUE (MVC) — Gestionnaire des thèmes visuels du jeu.
 * Définit les couleurs des tuiles, du fond et du texte
 * pour chacun des 5 thèmes disponibles.
 * Pattern MVC : fait partie de la View.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class ThemeManager {

    /**
     * Enumération des thèmes disponibles.
     * Chaque thème correspond à une palette de couleurs.
     */
    public enum Theme {
        CLASSIQUE, SOMBRE, PASTEL, NEON, OCEAN
    }

    /** Thème actuellement sélectionné */
    private Theme currentTheme = Theme.CLASSIQUE;

    /** Palettes de couleurs pour chaque thème */
    private static final Map<Integer, Color>
        CLASSIQUE_COLORS = new HashMap<>();
    private static final Map<Integer, Color>
        SOMBRE_COLORS    = new HashMap<>();
    private static final Map<Integer, Color>
        PASTEL_COLORS    = new HashMap<>();
    private static final Map<Integer, Color>
        NEON_COLORS      = new HashMap<>();
    private static final Map<Integer, Color>
        OCEAN_COLORS     = new HashMap<>();

    /**
     * Initialisation statique des palettes de couleurs.
     * Chaque valeur de tuile (2, 4, 8...) est associée
     * à une couleur spécifique par thème.
     */
    static {
        // ── Thème Classique (original 2048) ──
        CLASSIQUE_COLORS.put(0,    new Color(0xCDC1B4));
        CLASSIQUE_COLORS.put(2,    new Color(0xEEE4DA));
        CLASSIQUE_COLORS.put(4,    new Color(0xEDE0C8));
        CLASSIQUE_COLORS.put(8,    new Color(0xF2B179));
        CLASSIQUE_COLORS.put(16,   new Color(0xF59563));
        CLASSIQUE_COLORS.put(32,   new Color(0xF67C5F));
        CLASSIQUE_COLORS.put(64,   new Color(0xF65E3B));
        CLASSIQUE_COLORS.put(128,  new Color(0xEDCF72));
        CLASSIQUE_COLORS.put(256,  new Color(0xEDCC61));
        CLASSIQUE_COLORS.put(512,  new Color(0xEDC850));
        CLASSIQUE_COLORS.put(1024, new Color(0xEDC53F));
        CLASSIQUE_COLORS.put(2048, new Color(0xEDC22E));

        // ── Thème Sombre (tons foncés et violets) ──
        SOMBRE_COLORS.put(0,    new Color(0x3D3D3D));
        SOMBRE_COLORS.put(2,    new Color(0x4A4A4A));
        SOMBRE_COLORS.put(4,    new Color(0x555555));
        SOMBRE_COLORS.put(8,    new Color(0x1A6B8A));
        SOMBRE_COLORS.put(16,   new Color(0x1A7A5E));
        SOMBRE_COLORS.put(32,   new Color(0x7A1A1A));
        SOMBRE_COLORS.put(64,   new Color(0x9B2C2C));
        SOMBRE_COLORS.put(128,  new Color(0x6A3D8F));
        SOMBRE_COLORS.put(256,  new Color(0x7B4FA6));
        SOMBRE_COLORS.put(512,  new Color(0x8C5FBD));
        SOMBRE_COLORS.put(1024, new Color(0x9D70D4));
        SOMBRE_COLORS.put(2048, new Color(0xAE81EB));

        // ── Thème Pastel (tons doux et clairs) ──
        PASTEL_COLORS.put(0,    new Color(0xE8E8E8));
        PASTEL_COLORS.put(2,    new Color(0xFFD1DC));
        PASTEL_COLORS.put(4,    new Color(0xFFECB3));
        PASTEL_COLORS.put(8,    new Color(0xC8E6C9));
        PASTEL_COLORS.put(16,   new Color(0xB3E5FC));
        PASTEL_COLORS.put(32,   new Color(0xE1BEE7));
        PASTEL_COLORS.put(64,   new Color(0xFFCCBC));
        PASTEL_COLORS.put(128,  new Color(0xB2EBF2));
        PASTEL_COLORS.put(256,  new Color(0xF8BBD0));
        PASTEL_COLORS.put(512,  new Color(0xDCEDC8));
        PASTEL_COLORS.put(1024, new Color(0xFFF9C4));
        PASTEL_COLORS.put(2048, new Color(0xD7CCC8));

        // ── Thème Néon (couleurs vives sur fond noir) ──
        NEON_COLORS.put(0,    new Color(0x1A1A2E));
        NEON_COLORS.put(2,    new Color(0x16213E));
        NEON_COLORS.put(4,    new Color(0x0F3460));
        NEON_COLORS.put(8,    new Color(0xFF006E));
        NEON_COLORS.put(16,   new Color(0xFF4500));
        NEON_COLORS.put(32,   new Color(0xFFBE0B));
        NEON_COLORS.put(64,   new Color(0x00F5FF));
        NEON_COLORS.put(128,  new Color(0x39FF14));
        NEON_COLORS.put(256,  new Color(0xBF00FF));
        NEON_COLORS.put(512,  new Color(0xFF1493));
        NEON_COLORS.put(1024, new Color(0x00FF7F));
        NEON_COLORS.put(2048, new Color(0xFFD700));

        // ── Thème Océan (tons bleus et aquatiques) ──
        OCEAN_COLORS.put(0,    new Color(0xB0D4E8));
        OCEAN_COLORS.put(2,    new Color(0xC8E6F5));
        OCEAN_COLORS.put(4,    new Color(0x90C4D8));
        OCEAN_COLORS.put(8,    new Color(0x5BA3C9));
        OCEAN_COLORS.put(16,   new Color(0x2E86AB));
        OCEAN_COLORS.put(32,   new Color(0x1A6B8A));
        OCEAN_COLORS.put(64,   new Color(0x0D4F6C));
        OCEAN_COLORS.put(128,  new Color(0x00A8CC));
        OCEAN_COLORS.put(256,  new Color(0x0096B4));
        OCEAN_COLORS.put(512,  new Color(0x007A96));
        OCEAN_COLORS.put(1024, new Color(0x005F78));
        OCEAN_COLORS.put(2048, new Color(0x004456));
    }

    /**
     * Change le thème actuel.
     * @param theme nouveau thème à appliquer
     */
    public void setTheme(Theme theme) {
        this.currentTheme = theme;
    }

    /**
     * @return le thème actuellement sélectionné
     */
    public Theme getCurrentTheme() {
        return currentTheme;
    }

    /**
     * Retourne la couleur d'une tuile selon sa valeur
     * et le thème actuel.
     * @param value valeur de la tuile
     * @return couleur correspondante
     */
    public Color getTileColor(int value) {
        return getCurrentMap().getOrDefault(
            value, new Color(0x3C3A32));
    }

    /**
     * Retourne la couleur de fond de la grille
     * selon le thème actuel.
     * @return couleur de fond
     */
    public Color getBackgroundColor() {
        switch (currentTheme) {
            case SOMBRE: return new Color(0x1A1A2E);
            case NEON:   return new Color(0x0D0D0D);
            case PASTEL: return new Color(0xF5F5F5);
            case OCEAN:  return new Color(0x87CEEB);
            default:     return new Color(0xBBADA0);
        }
    }

    /**
     * Retourne la couleur du texte sur une tuile
     * selon le thème et la valeur de la tuile.
     * Les petites valeurs ont un texte sombre,
     * les grandes valeurs ont un texte blanc.
     * @param value valeur de la tuile
     * @return couleur du texte
     */
    public Color getTextColor(int value) {
        if (currentTheme == Theme.SOMBRE ||
            currentTheme == Theme.NEON)
            return Color.WHITE;
        if (currentTheme == Theme.OCEAN)
            return value <= 4 ?
                new Color(0x1A4A6A) : Color.WHITE;
        return value <= 4 ?
            new Color(0x776E65) : Color.WHITE;
    }

    /**
     * Retourne la couleur du texte pour la barre
     * de score selon le thème actuel.
     * @return couleur du texte de score
     */
    public Color getScoreTextColor() {
        switch (currentTheme) {
            case SOMBRE:
            case NEON:   return Color.WHITE;
            case OCEAN:  return new Color(0x1A4A6A);
            default:     return Color.DARK_GRAY;
        }
    }

    /**
     * Retourne la palette de couleurs du thème actuel.
     * @return map valeur → couleur
     */
    private Map<Integer, Color> getCurrentMap() {
        switch (currentTheme) {
            case SOMBRE: return SOMBRE_COLORS;
            case PASTEL: return PASTEL_COLORS;
            case NEON:   return NEON_COLORS;
            case OCEAN:  return OCEAN_COLORS;
            default:     return CLASSIQUE_COLORS;
        }
    }
}