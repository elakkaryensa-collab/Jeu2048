package jeu2048.view;

import jeu2048.controller.AccountManager;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * VUE (MVC) — Panneau d'accueil et de navigation.
 * Gère trois écrans via un CardLayout :
 * 1. Page de connexion/inscription
 * 2. Page d'accueil (stats, mode, jouer)
 * 3. Page de paramètres (thème, langue, musique)
 * Supporte 3 langues et 5 thèmes visuels.
 * Pattern MVC : fait partie de la View.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class WelcomePanel extends JPanel {

    /** Champ de saisie du pseudo */
    private JTextField usernameField;

    /** Champ de saisie du mot de passe */
    private JPasswordField passwordField;

    /** Gestionnaire de comptes utilisateurs */
    private AccountManager accountManager;

    /** Référence à la fenêtre principale */
    private GameFrame gameFrame;

    /** Thème visuel sélectionné */
    private String selectedTheme = "Classique";

    /** Mode de jeu sélectionné */
    private String selectedMode = "Classique";

    /** Nom de l'utilisateur connecté */
    private String currentUsername = "";

    /** Langue sélectionnée */
    private String selectedLanguage = "Français";

    /** Table de traductions multi-langue */
    private Map<String, Map<String, String>>
        translations = new HashMap<>();

    /**
     * Constructeur — initialise le panneau d'accueil.
     * @param accountManager gestionnaire de comptes
     * @param gameFrame      fenêtre principale
     */
    public WelcomePanel(AccountManager accountManager,
                        GameFrame gameFrame) {
        this.accountManager = accountManager;
        this.gameFrame      = gameFrame;
        initTranslations();
        setLayout(new CardLayout());
        setBackground(Color.WHITE);
        buildLoginPanel();
    }

    /**
     * Initialise les traductions pour les 3 langues :
     * Français, English, العربية.
     */
    private void initTranslations() {
        // ── Français ──
        Map<String, String> fr = new HashMap<>();
        fr.put("pseudo",        "Pseudo");
        fr.put("password",      "Mot de passe");
        fr.put("login",         "Se connecter");
        fr.put("register",      "Créer un compte");
        fr.put("welcome",       "Bienvenue, ");
        fr.put("best_score",    "MEILLEUR SCORE");
        fr.put("global_best",   "MEILLEUR SCORE GLOBAL");
        fr.put("games_played",  "PARTIES JOUÉES");
        fr.put("game_mode",     "Mode de jeu");
        fr.put("play",          "JOUER !");
        fr.put("continue",      "Continuer");
        fr.put("settings_title","Paramètres");
        fr.put("account",       "COMPTE");
        fr.put("connected_as",  "Connecté en tant que");
        fr.put("logout",        "Déconnexion");
        fr.put("music",         "MUSIQUE");
        fr.put("music_on",      "Activée");
        fr.put("music_off",     "Désactivée");
        fr.put("theme",         "THÈME");
        fr.put("language",      "LANGUE");
        fr.put("fill_fields",
            "Remplis tous les champs !");
        fr.put("error",         "Erreur");
        fr.put("wrong_creds",
            "Pseudo ou mot de passe incorrect !");
        fr.put("already_exists",
            "Ce pseudo existe déjà !");
        fr.put("mode_classic",
            "Classique — Atteindre 2048");
        fr.put("mode_time",
            "Time Attack — 3 minutes pour scorer");
        fr.put("mode_challenge",
            "Challenge — 200 mouvements max");
        fr.put("mode_2players",
            "2 Joueurs — Alternatif");
        translations.put("Français", fr);

        // ── English ──
        Map<String, String> en = new HashMap<>();
        en.put("pseudo",        "Username");
        en.put("password",      "Password");
        en.put("login",         "Sign In");
        en.put("register",      "Create Account");
        en.put("welcome",       "Welcome, ");
        en.put("best_score",    "BEST SCORE");
        en.put("global_best",   "GLOBAL BEST SCORE");
        en.put("games_played",  "GAMES PLAYED");
        en.put("game_mode",     "Game Mode");
        en.put("play",          "PLAY !");
        en.put("continue",      "Continue");
        en.put("settings_title","Settings");
        en.put("account",       "ACCOUNT");
        en.put("connected_as",  "Connected as");
        en.put("logout",        "Log Out");
        en.put("music",         "MUSIC");
        en.put("music_on",      "Enabled");
        en.put("music_off",     "Disabled");
        en.put("theme",         "THEME");
        en.put("language",      "LANGUAGE");
        en.put("fill_fields",
            "Please fill all fields!");
        en.put("error",         "Error");
        en.put("wrong_creds",
            "Wrong username or password!");
        en.put("already_exists",
            "This username already exists!");
        en.put("mode_classic",
            "Classic — Reach 2048");
        en.put("mode_time",
            "Time Attack — Score in 3 minutes");
        en.put("mode_challenge",
            "Challenge — 200 moves max");
        en.put("mode_2players",
            "2 Players — Alternating");
        translations.put("English", en);

        // ── العربية ──
        Map<String, String> ar = new HashMap<>();
        ar.put("pseudo",        "اسم المستخدم");
        ar.put("password",      "كلمة المرور");
        ar.put("login",         "تسجيل الدخول");
        ar.put("register",      "إنشاء حساب");
        ar.put("welcome",       "مرحباً، ");
        ar.put("best_score",    "أفضل نتيجة");
        ar.put("global_best",   "أفضل نتيجة عالمية");
        ar.put("games_played",  "الأشواط المُلعَبة");
        ar.put("game_mode",     "وضع اللعبة");
        ar.put("play",          "!العب");
        ar.put("continue",      "متابعة");
        ar.put("settings_title","الإعدادات");
        ar.put("account",       "الحساب");
        ar.put("connected_as",  "متصل بوصفك");
        ar.put("logout",        "تسجيل الخروج");
        ar.put("music",         "الموسيقى");
        ar.put("music_on",      "مفعّلة");
        ar.put("music_off",     "معطّلة");
        ar.put("theme",         "السمة");
        ar.put("language",      "اللغة");
        ar.put("fill_fields",
            "!يرجى ملء جميع الحقول");
        ar.put("error",         "خطأ");
        ar.put("wrong_creds",
            "!اسم مستخدم أو كلمة مرور غير صحيحة");
        ar.put("already_exists",
            "!اسم المستخدم موجود بالفعل");
        ar.put("mode_classic",
            "كلاسيكي — الوصول إلى 2048");
        ar.put("mode_time",
            "ضد الوقت — 3 دقائق للتسجيل");
        ar.put("mode_challenge",
            "تحدي — 200 حركة كحد أقصى");
        ar.put("mode_2players",
            "لاعبان — بالتناوب");
        translations.put("العربية", ar);
    }

    /**
     * Traduit une clé dans la langue sélectionnée.
     * @param key clé de traduction
     * @return texte traduit
     */
    private String t(String key) {
        Map<String, String> lang =
            translations.get(selectedLanguage);
        if (lang == null)
            lang = translations.get("Français");
        return lang.getOrDefault(key, key);
    }

    // ── Couleurs selon le thème ───────────────────────────

    /** @return couleur de fond selon le thème */
    private Color getThemeBg() {
        switch (selectedTheme) {
            case "Sombre": return new Color(0x1E1E2E);
            case "Pastel": return new Color(0xFFF0F5);
            case "Neon":   return new Color(0x0D0D1A);
            case "Ocean":  return new Color(0xE8F4F8);
            default:       return Color.WHITE;
        }
    }

    /** @return couleur primaire selon le thème */
    private Color getThemePrimary() {
        switch (selectedTheme) {
            case "Sombre": return new Color(0xCBA6F7);
            case "Pastel": return new Color(0xE78FA3);
            case "Neon":   return new Color(0x00FFAA);
            case "Ocean":  return new Color(0x2A7FAF);
            default:       return new Color(0xF5853F);
        }
    }

    /** @return couleur secondaire selon le thème */
    private Color getThemeSecondary() {
        switch (selectedTheme) {
            case "Sombre": return new Color(0x313244);
            case "Pastel": return new Color(0xF7C5D5);
            case "Neon":   return new Color(0x1A1A3E);
            case "Ocean":  return new Color(0xB8DFF0);
            default:       return new Color(0xC8A882);
        }
    }

    /** @return couleur du texte selon le thème */
    private Color getThemeText() {
        switch (selectedTheme) {
            case "Sombre": return new Color(0xCDD6F4);
            case "Neon":   return new Color(0xE0E0FF);
            default:       return new Color(0x7C6A5A);
        }
    }

    // ── PAGE LOGIN ────────────────────────────────────────

    /**
     * Construit la page de connexion/inscription.
     * Affiche le titre, les tuiles décoratives
     * et le formulaire centré.
     */
    private void buildLoginPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(getThemeBg());

        // Carte centrale blanche
        JPanel card = new JPanel();
        card.setLayout(
            new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(0xE8DDD5), 1, true),
            new EmptyBorder(30, 30, 30, 30)));

        // Titre 2048
        JLabel title = new JLabel(
            "2048", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 80));
        title.setForeground(getThemePrimary());
        title.setAlignmentX(CENTER_ALIGNMENT);
        card.add(title);

        // Sous-titre
        JLabel subtitle = new JLabel(
            "Connectez-vous pour jouer",
            SwingConstants.CENTER);
        subtitle.setFont(
            new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(0xB0A090));
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        card.add(subtitle);
        card.add(Box.createVerticalStrut(14));

        // Tuiles décoratives (2, 4, 8, 16, 32)
        JPanel tilesPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 6, 0));
        tilesPanel.setBackground(Color.WHITE);
        tilesPanel.setAlignmentX(CENTER_ALIGNMENT);
        String[] tileLabels = {"2","4","8","16","32"};
        Color[]  tileColors = {
            new Color(0xEEB975), new Color(0xF49241),
            new Color(0xE8622A), new Color(0xD44A1A),
            new Color(0xB8360E)
        };
        for (int i = 0; i < tileLabels.length; i++) {
            JLabel tile = new JLabel(
                tileLabels[i], SwingConstants.CENTER);
            tile.setPreferredSize(new Dimension(36, 36));
            tile.setFont(new Font("Arial", Font.BOLD,
                i < 3 ? 13 : 11));
            tile.setForeground(Color.WHITE);
            tile.setBackground(tileColors[i]);
            tile.setOpaque(true);
            tilesPanel.add(tile);
        }
        card.add(tilesPanel);
        card.add(Box.createVerticalStrut(20));

        // Séparateur
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(0xF0E8E0));
        card.add(sep);
        card.add(Box.createVerticalStrut(18));

        // Formulaire (largeur fixe 260px)
        JPanel form = new JPanel();
        form.setLayout(
            new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setAlignmentX(CENTER_ALIGNMENT);

        // Champ Pseudo
        JLabel lblUser = new JLabel(t("pseudo"));
        lblUser.setFont(
            new Font("Arial", Font.BOLD, 11));
        lblUser.setForeground(new Color(0x8C7B6E));
        lblUser.setAlignmentX(LEFT_ALIGNMENT);
        form.add(lblUser);
        form.add(Box.createVerticalStrut(5));
        usernameField = new JTextField();
        styleField(usernameField);
        usernameField.setAlignmentX(LEFT_ALIGNMENT);
        usernameField.setMaximumSize(
            new Dimension(260, 42));
        form.add(usernameField);
        form.add(Box.createVerticalStrut(12));

        // Champ Mot de passe
        JLabel lblPass = new JLabel(t("password"));
        lblPass.setFont(
            new Font("Arial", Font.BOLD, 11));
        lblPass.setForeground(new Color(0x8C7B6E));
        lblPass.setAlignmentX(LEFT_ALIGNMENT);
        form.add(lblPass);
        form.add(Box.createVerticalStrut(5));
        passwordField = new JPasswordField();
        styleField(passwordField);
        passwordField.setAlignmentX(LEFT_ALIGNMENT);
        passwordField.setMaximumSize(
            new Dimension(260, 42));
        form.add(passwordField);
        form.add(Box.createVerticalStrut(18));

        // Bouton Se connecter
        JButton loginBtn = createStyledButton(
            t("login"), getThemePrimary());
        loginBtn.setAlignmentX(LEFT_ALIGNMENT);
        loginBtn.setMaximumSize(
            new Dimension(260, 44));
        form.add(loginBtn);
        form.add(Box.createVerticalStrut(8));

        // Bouton Créer un compte
        JButton registerBtn = createOutlineButton(
            t("register"));
        registerBtn.setAlignmentX(LEFT_ALIGNMENT);
        registerBtn.setMaximumSize(
            new Dimension(260, 44));
        form.add(registerBtn);
        card.add(form);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());
        passwordField.addActionListener(e -> login());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(20, 30, 20, 30);
        outer.add(card, g);

        removeAll();
        add(outer, "login");
        ((CardLayout) getLayout()).show(this, "login");
        revalidate();
        repaint();
    }

    // ── PAGE ACCUEIL ──────────────────────────────────────

    /**
     * Construit et affiche la page d'accueil.
     * Affiche les stats du joueur, les modes de jeu
     * et les boutons JOUER / Continuer.
     * @param username nom de l'utilisateur connecté
     */
    public void showHomePanel(String username) {
        currentUsername = username;

        // Supprimer l'ancien panneau home
        Component toRemove = null;
        for (int i = 0; i < getComponentCount(); i++) {
            if ("homeScroll".equals(
                    getComponent(i).getName())) {
                toRemove = getComponent(i);
                break;
            }
        }
        if (toRemove != null) remove(toRemove);

        JPanel home = new JPanel();
        home.setLayout(
            new BoxLayout(home, BoxLayout.Y_AXIS));
        home.setBackground(getThemeBg());
        home.setBorder(new EmptyBorder(20, 20, 30, 20));

        JScrollPane scroll = new JScrollPane(home);
        scroll.setName("homeScroll");
        scroll.setBorder(null);
        scroll.getViewport().setBackground(getThemeBg());

        // ── Header : titre + bouton paramètres ──
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(getThemeBg());
        header.setMaximumSize(new Dimension(2000, 70));
        header.setAlignmentX(LEFT_ALIGNMENT);

        JLabel titleHome = new JLabel("2048");
        titleHome.setFont(
            new Font("Arial", Font.BOLD, 56));
        titleHome.setForeground(getThemePrimary());
        header.add(titleHome, BorderLayout.WEST);

        // Bouton Paramètres en gras
        JButton settingsBtn = new JButton(
            t("settings_title"));
        settingsBtn.setFont(
            new Font("Arial", Font.BOLD, 13));
        settingsBtn.setBackground(getThemePrimary());
        settingsBtn.setForeground(Color.WHITE);
        settingsBtn.setBorder(
            new EmptyBorder(8, 14, 8, 14));
        settingsBtn.setFocusPainted(false);
        settingsBtn.setBorderPainted(false);
        settingsBtn.setCursor(
            new Cursor(Cursor.HAND_CURSOR));
        settingsBtn.setOpaque(true);
        settingsBtn.addActionListener(
            e -> showSettings(username));
        header.add(settingsBtn, BorderLayout.EAST);
        home.add(header);
        home.add(Box.createVerticalStrut(12));

        // ── Message de bienvenue ──
        JLabel welcome = new JLabel(
            t("welcome") + username + " !");
        welcome.setFont(
            new Font("Arial", Font.BOLD, 22));
        welcome.setForeground(getThemeText());
        welcome.setAlignmentX(LEFT_ALIGNMENT);
        home.add(welcome);
        home.add(Box.createVerticalStrut(16));

        // Séparateur
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(2000, 1));
        sep.setForeground(new Color(0xE0D8D0));
        sep.setAlignmentX(LEFT_ALIGNMENT);
        home.add(sep);
        home.add(Box.createVerticalStrut(16));

        // ── Stats : mon score + meilleur global ──
        JPanel statsPanel = new JPanel(
            new GridLayout(1, 2, 12, 0));
        statsPanel.setBackground(getThemeBg());
        statsPanel.setMaximumSize(new Dimension(2000, 90));
        statsPanel.setAlignmentX(LEFT_ALIGNMENT);

        int myBest      =
            accountManager.getBestScore(username);
        int globalBest  =
            accountManager.getGlobalBestScore();
        String bestPlayer =
            accountManager.getGlobalBestPlayer();

        statsPanel.add(createStatCard(
            t("best_score"), String.valueOf(myBest)));
        statsPanel.add(createStatCardWithSub(
            t("global_best"),
            String.valueOf(globalBest), bestPlayer));
        home.add(statsPanel);
        home.add(Box.createVerticalStrut(20));

        // ── Couleurs des cartes de mode ──
        Color modeCardBg    = getThemeSecondary().brighter();
        Color modeRowBg     = getThemeBg();
        Color modeRowBorder = getThemeSecondary();
        Color modeSubClr;
        switch (selectedTheme) {
            case "Sombre":
                modeSubClr = new Color(0xA0A0C0); break;
            case "Neon":
                modeSubClr = new Color(0x80FFD0); break;
            case "Ocean":
                modeSubClr = new Color(0x5A9FC0); break;
            case "Pastel":
                modeSubClr = new Color(0xC07080); break;
            default:
                modeSubClr = new Color(0x9E8E7E); break;
        }

        // ── Carte Mode de jeu ──
        JPanel modeCard = new JPanel();
        modeCard.setLayout(
            new BoxLayout(modeCard, BoxLayout.Y_AXIS));
        modeCard.setBackground(modeCardBg);
        modeCard.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    getThemeSecondary(), 1, true),
                new EmptyBorder(14, 16, 14, 16)));
        modeCard.setMaximumSize(new Dimension(2000, 260));
        modeCard.setAlignmentX(LEFT_ALIGNMENT);

        JLabel modeTitle = new JLabel(
            t("game_mode").toUpperCase(),
            SwingConstants.CENTER);
        modeTitle.setFont(
            new Font("Arial", Font.BOLD, 11));
        modeTitle.setForeground(getThemeText());
        modeTitle.setAlignmentX(CENTER_ALIGNMENT);
        modeCard.add(modeTitle);
        modeCard.add(Box.createVerticalStrut(10));

        // 4 modes de jeu
        String[][] modeData = {
            {t("mode_classic").split(" — ")[0],
             t("mode_classic").contains(" — ") ?
             t("mode_classic").split(" — ")[1] : ""},
            {t("mode_time").split(" — ")[0],
             t("mode_time").contains(" — ") ?
             t("mode_time").split(" — ")[1] : ""},
            {t("mode_challenge").split(" — ")[0],
             t("mode_challenge").contains(" — ") ?
             t("mode_challenge").split(" — ")[1] : ""},
            {t("mode_2players").split(" — ")[0],
             t("mode_2players").contains(" — ") ?
             t("mode_2players").split(" — ")[1] : ""}
        };
        String[] modeKeys = {
            "Classique", "Time Attack",
            "Challenge", "2Joueurs"};
        ButtonGroup modeGroup = new ButtonGroup();

        for (int i = 0; i < modeData.length; i++) {
            final String modeKey = modeKeys[i];
            boolean isSelected =
                modeKey.equals(selectedMode);

            Color rowBg = isSelected ?
                getThemePrimary().darker() : modeRowBg;
            Color rowBorder = isSelected ?
                getThemePrimary() : modeRowBorder;
            Color nameClr = isSelected ?
                Color.WHITE : getThemeText();
            Color subClr = isSelected ?
                new Color(220,220,220) : modeSubClr;

            JPanel row = new JPanel(
                new BorderLayout(10, 0));
            row.setBackground(rowBg);
            row.setMaximumSize(new Dimension(2000, 56));
            row.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        rowBorder, 1, true),
                    new EmptyBorder(8, 12, 8, 12)));

            JPanel textBlock = new JPanel();
            textBlock.setLayout(new BoxLayout(
                textBlock, BoxLayout.Y_AXIS));
            textBlock.setBackground(rowBg);

            JLabel name = new JLabel(modeData[i][0]);
            name.setFont(
                new Font("Arial", Font.BOLD, 14));
            name.setForeground(nameClr);
            textBlock.add(name);

            if (!modeData[i][1].isEmpty()) {
                JLabel sub = new JLabel(modeData[i][1]);
                sub.setFont(
                    new Font("Arial", Font.PLAIN, 11));
                sub.setForeground(subClr);
                textBlock.add(sub);
            }

            JRadioButton radio = new JRadioButton();
            radio.setBackground(rowBg);
            radio.setSelected(isSelected);
            radio.setFocusPainted(false);
            modeGroup.add(radio);
            radio.addActionListener(e -> {
                selectedMode = modeKey;
                showHomePanel(username);
            });

            row.add(textBlock, BorderLayout.CENTER);
            row.add(radio, BorderLayout.EAST);
            modeCard.add(row);
            if (i < modeData.length - 1)
                modeCard.add(Box.createVerticalStrut(6));
        }

        home.add(modeCard);
        home.add(Box.createVerticalStrut(20));

        // ── Bouton JOUER ──
        JButton playBtn = new JButton(t("play"));
        playBtn.setFont(
            new Font("Arial", Font.BOLD, 16));
        playBtn.setBackground(getThemePrimary());
        playBtn.setForeground(Color.WHITE);
        playBtn.setFocusPainted(false);
        playBtn.setBorderPainted(false);
        playBtn.setOpaque(true);
        playBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playBtn.setMaximumSize(new Dimension(2000, 56));
        playBtn.setAlignmentX(LEFT_ALIGNMENT);
        playBtn.addActionListener(e -> {
            applyTheme(selectedTheme);
            gameFrame.setGameMode(selectedMode);
            gameFrame.getScoreManager().reset();
            gameFrame.getBoardRef()[0].reset();
            gameFrame.showGame(username);
        });
        home.add(playBtn);
        home.add(Box.createVerticalStrut(8));

        // ── Bouton Continuer (si partie en cours) ──
        if (gameFrame.isGameInProgress()) {
            JButton continueBtn =
                new JButton(t("continue"));
            continueBtn.setFont(
                new Font("Arial", Font.BOLD, 16));
            continueBtn.setBackground(
                getThemeSecondary());
            continueBtn.setForeground(Color.WHITE);
            continueBtn.setFocusPainted(false);
            continueBtn.setBorderPainted(false);
            continueBtn.setOpaque(true);
            continueBtn.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
            continueBtn.setMaximumSize(
                new Dimension(2000, 46));
            continueBtn.setAlignmentX(LEFT_ALIGNMENT);
            continueBtn.addActionListener(e -> {
                applyTheme(selectedTheme);
                gameFrame.showGame(username);
            });
            home.add(continueBtn);
        }
        home.add(Box.createVerticalStrut(20));

        add(scroll, "home");
        ((CardLayout) getLayout()).show(this, "home");
        revalidate();
        repaint();
    }

    // ── PAGE SETTINGS ─────────────────────────────────────

    /**
     * Affiche la fenêtre de paramètres.
     * Permet de modifier : compte, musique, langue, thème.
     * @param username nom de l'utilisateur connecté
     */
    private void showSettings(String username) {
        JDialog dialog = new JDialog();
        dialog.setTitle(t("settings_title"));
        dialog.setSize(420, 620);
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(
            new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(getThemeBg());
        panel.setBorder(
            new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel(t("settings_title"));
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(getThemePrimary());
        title.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        // ── Section COMPTE ──
        addSectionLabel(panel, t("account"));
        JPanel accountCard =
            new JPanel(new BorderLayout());
        accountCard.setBackground(
            getThemeSecondary().brighter());
        accountCard.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    getThemeSecondary(), 1, true),
                new EmptyBorder(12, 15, 12, 15)));
        accountCard.setMaximumSize(
            new Dimension(400, 65));

        JPanel userInfo = new JPanel(new GridLayout(2,1));
        userInfo.setBackground(
            accountCard.getBackground());
        JLabel connectedAs = new JLabel(
            t("connected_as"));
        connectedAs.setFont(
            new Font("Arial", Font.PLAIN, 11));
        connectedAs.setForeground(getThemeText());
        JLabel userLabel = new JLabel(username);
        userLabel.setFont(
            new Font("Arial", Font.BOLD, 15));
        userLabel.setForeground(getThemePrimary());
        userInfo.add(connectedAs);
        userInfo.add(userLabel);

        // Bouton déconnexion
        JButton logoutBtn = new JButton(t("logout"));
        logoutBtn.setFont(
            new Font("Arial", Font.BOLD, 12));
        logoutBtn.setForeground(new Color(0xF65E3B));
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setCursor(
            new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            dialog.dispose();
            ((CardLayout) getLayout())
                .show(this, "login");
        });

        accountCard.add(userInfo, BorderLayout.CENTER);
        accountCard.add(logoutBtn, BorderLayout.EAST);
        panel.add(accountCard);
        panel.add(Box.createVerticalStrut(15));

        // ── Section MUSIQUE ──
        addSectionLabel(panel, t("music"));
        JPanel musicCard = new JPanel(new BorderLayout());
        musicCard.setBackground(
            getThemeSecondary().brighter());
        musicCard.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    getThemeSecondary(), 1, true),
                new EmptyBorder(12, 15, 12, 15)));
        musicCard.setMaximumSize(new Dimension(400, 55));

        JLabel musicLabel = new JLabel(t("music_on"));
        musicLabel.setFont(
            new Font("Arial", Font.PLAIN, 15));
        musicLabel.setForeground(getThemeText());

        JToggleButton musicToggle =
            new JToggleButton("ON");
        musicToggle.setSelected(true);
        musicToggle.setFont(
            new Font("Arial", Font.BOLD, 12));
        musicToggle.setBackground(getThemePrimary());
        musicToggle.setForeground(Color.WHITE);
        musicToggle.setFocusPainted(false);
        musicToggle.setBorder(
            new EmptyBorder(5, 10, 5, 10));
        musicToggle.addActionListener(e -> {
            gameFrame.getSoundManager().setEnabled(
                musicToggle.isSelected());
            musicLabel.setText(
                musicToggle.isSelected() ?
                t("music_on") : t("music_off"));
            musicToggle.setText(
                musicToggle.isSelected() ? "ON" : "OFF");
            musicToggle.setBackground(
                musicToggle.isSelected() ?
                getThemePrimary() : getThemeSecondary());
        });
        musicCard.add(musicLabel, BorderLayout.WEST);
        musicCard.add(musicToggle, BorderLayout.EAST);
        panel.add(musicCard);
        panel.add(Box.createVerticalStrut(15));

        // ── Section LANGUE ──
        addSectionLabel(panel, t("language"));
        String[] langs = {
            "Français", "English", "العربية"};
        for (String lang : langs) {
            JPanel langCard =
                new JPanel(new BorderLayout());
            boolean isSel = lang.equals(selectedLanguage);
            langCard.setBackground(isSel ?
                getThemeSecondary() :
                getThemeSecondary().brighter());
            langCard.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        getThemeSecondary(), 1, true),
                    new EmptyBorder(10, 15, 10, 15)));
            langCard.setMaximumSize(
                new Dimension(400, 48));

            JLabel langName = new JLabel(lang);
            langName.setFont(
                new Font("Arial", Font.PLAIN, 14));
            langName.setForeground(getThemeText());

            JLabel check = new JLabel(isSel ? "✓" : "");
            check.setFont(
                new Font("Arial", Font.BOLD, 16));
            check.setForeground(getThemePrimary());

            langCard.add(langName, BorderLayout.WEST);
            langCard.add(check, BorderLayout.EAST);
            langCard.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
            langCard.addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(
                            MouseEvent e) {
                        selectedLanguage = lang;
                        dialog.dispose();
                        rebuildAll(username);
                        showSettings(username);
                    }
                });
            panel.add(langCard);
            panel.add(Box.createVerticalStrut(5));
        }
        panel.add(Box.createVerticalStrut(10));

        // ── Section THÈME ──
        addSectionLabel(panel, t("theme"));
        String[] themes = {
            "Classique","Sombre","Pastel","Neon","Ocean"};
        for (String theme : themes) {
            JPanel themeCard =
                new JPanel(new BorderLayout());
            boolean isSel = theme.equals(selectedTheme);
            themeCard.setBackground(isSel ?
                getThemeSecondary() :
                getThemeSecondary().brighter());
            themeCard.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        getThemeSecondary(), 1, true),
                    new EmptyBorder(10, 15, 10, 15)));
            themeCard.setMaximumSize(
                new Dimension(400, 48));

            JLabel themeName = new JLabel(theme);
            themeName.setFont(
                new Font("Arial", Font.PLAIN, 14));
            themeName.setForeground(getThemeText());

            JLabel check = new JLabel(isSel ? "✓" : "");
            check.setFont(
                new Font("Arial", Font.BOLD, 16));
            check.setForeground(getThemePrimary());

            themeCard.add(themeName, BorderLayout.WEST);
            themeCard.add(check, BorderLayout.EAST);
            themeCard.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
            themeCard.addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(
                            MouseEvent e) {
                        selectedTheme = theme;
                        applyTheme(theme);
                        dialog.dispose();
                        rebuildAll(username);
                        showSettings(username);
                    }
                });
            panel.add(themeCard);
            panel.add(Box.createVerticalStrut(5));
        }

        JScrollPane sp = new JScrollPane(panel);
        sp.getViewport().setBackground(getThemeBg());
        sp.setBorder(null);
        dialog.add(sp);
        dialog.setVisible(true);
    }

    /**
     * Reconstruit tous les panneaux après un changement
     * de thème ou de langue.
     * @param username nom de l'utilisateur connecté
     */
    private void rebuildAll(String username) {
        removeAll();
        buildLoginPanel();
        showHomePanel(username);
    }

    // ── HELPERS ───────────────────────────────────────────

    /**
     * Crée une carte de statistique simple.
     * @param label libellé de la stat
     * @param value valeur à afficher
     * @return panneau carte
     */
    private JPanel createStatCard(String label,
                                   String value) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(getThemeSecondary());
        card.setBorder(BorderFactory.createEmptyBorder(
            15, 10, 15, 10));
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0;
        JLabel lbl = new JLabel(
            label, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setForeground(Color.WHITE);
        card.add(lbl, g);
        g.gridy = 1;
        JLabel val = new JLabel(
            value, SwingConstants.CENTER);
        val.setFont(new Font("Arial", Font.BOLD, 22));
        val.setForeground(Color.WHITE);
        card.add(val, g);
        return card;
    }

    /**
     * Crée une carte de statistique avec sous-titre.
     * Utilisée pour le meilleur score global avec
     * le nom du meilleur joueur.
     * @param label libellé
     * @param value valeur principale
     * @param sub   sous-titre (nom du joueur)
     * @return panneau carte
     */
    private JPanel createStatCardWithSub(String label,
                                          String value,
                                          String sub) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(getThemePrimary().darker());
        card.setBorder(BorderFactory.createEmptyBorder(
            15, 10, 15, 10));
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0;
        JLabel lbl = new JLabel(
            label, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setForeground(Color.WHITE);
        card.add(lbl, g);
        g.gridy = 1;
        JLabel val = new JLabel(
            value, SwingConstants.CENTER);
        val.setFont(new Font("Arial", Font.BOLD, 22));
        val.setForeground(Color.WHITE);
        card.add(val, g);
        g.gridy = 2;
        JLabel subLbl = new JLabel(
            sub, SwingConstants.CENTER);
        subLbl.setFont(
            new Font("Arial", Font.PLAIN, 11));
        subLbl.setForeground(new Color(255, 220, 180));
        card.add(subLbl, g);
        return card;
    }

    /**
     * Ajoute un label de section dans un panneau.
     * @param panel panneau cible
     * @param text  texte du label
     */
    private void addSectionLabel(JPanel panel,
                                  String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont()
            .deriveFont(Font.BOLD));
        label.setForeground(getThemeText());
        label.setBorder(new EmptyBorder(0, 0, 5, 0));
        label.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(label);
    }

    /**
     * Applique le style visuel à un champ de saisie.
     * @param field champ à styliser
     */
    private void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(0xE8DDD5), 2, true),
                BorderFactory.createEmptyBorder(
                    10, 12, 10, 12)));
        field.setBackground(new Color(0xFDFAF7));
        field.setForeground(new Color(0x3A2E28));
        field.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 42));
    }

    /**
     * Crée un bouton stylisé avec fond coloré.
     * @param text texte du bouton
     * @param bg   couleur de fond
     * @return bouton stylisé
     */
    private JButton createStyledButton(String text,
                                        Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }

    /**
     * Crée un bouton avec contour (secondaire).
     * Utilisé pour le bouton "Créer un compte".
     * @param text texte du bouton
     * @return bouton avec contour
     */
    private JButton createOutlineButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setBackground(new Color(0xFDFAF7));
        btn.setForeground(new Color(0x8C7B6E));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(0xE8DDD5), 1, true),
            new EmptyBorder(10, 20, 10, 20)));
        btn.setOpaque(true);
        return btn;
    }

    /**
     * Applique le thème visuel au panneau de jeu.
     * @param themeName nom du thème à appliquer
     */
    private void applyTheme(String themeName) {
        switch (themeName) {
            case "Classique":
                gameFrame.getGamePanel().setTheme(
                    ThemeManager.Theme.CLASSIQUE); break;
            case "Sombre":
                gameFrame.getGamePanel().setTheme(
                    ThemeManager.Theme.SOMBRE); break;
            case "Pastel":
                gameFrame.getGamePanel().setTheme(
                    ThemeManager.Theme.PASTEL); break;
            case "Neon":
                gameFrame.getGamePanel().setTheme(
                    ThemeManager.Theme.NEON); break;
            case "Ocean":
                gameFrame.getGamePanel().setTheme(
                    ThemeManager.Theme.OCEAN); break;
        }
    }

    /**
     * Gère la connexion d'un utilisateur existant.
     * Vérifie les champs et les identifiants en BDD.
     */
    private void login() {
        String username =
            usernameField.getText().trim();
        String password =
            new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                t("fill_fields"), t("error"),
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (accountManager.login(username, password)) {
            showHomePanel(username);
        } else {
            JOptionPane.showMessageDialog(this,
                t("wrong_creds"), t("error"),
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gère l'inscription d'un nouvel utilisateur.
     * Vérifie les champs et crée le compte en BDD.
     * Connecte automatiquement après inscription.
     */
    private void register() {
        String username =
            usernameField.getText().trim();
        String password =
            new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                t("fill_fields"), t("error"),
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (accountManager.register(username, password)) {
            // Connexion automatique après inscription
            showHomePanel(username);
        } else {
            JOptionPane.showMessageDialog(this,
                t("already_exists"), t("error"),
                JOptionPane.ERROR_MESSAGE);
        }
    }
}