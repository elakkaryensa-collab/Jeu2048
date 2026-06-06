package jeu2048.controller;

import javax.sound.midi.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CONTRÔLEUR (MVC) — Gère les effets sonores du jeu.
 * Utilise l'API MIDI de Java pour générer des sons
 * sans fichier audio externe.
 * Les sons sont joués dans un thread dédié pour
 * ne pas bloquer l'interface graphique.
 *
 * @author Groupe Jeu2048
 * @version 1.0
 */
public class SoundManager {

    /** Synthétiseur MIDI Java */
    private Synthesizer synthesizer;

    /** Canal MIDI pour jouer les notes */
    private MidiChannel channel;

    /** Activation/désactivation du son */
    private boolean enabled = true;

    /**
     * Thread unique dédié au son — daemon pour
     * s'arrêter automatiquement à la fermeture.
     */
    private final ExecutorService executor =
        Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "sound-thread");
            t.setDaemon(true);
            return t;
        });

    /**
     * Constructeur — initialise le synthétiseur MIDI
     * et sélectionne l'instrument Vibraphone (n°11).
     * Joue une note silencieuse pour préchauffer
     * le moteur MIDI.
     */
    public SoundManager() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            channel = synthesizer.getChannels()[0];
            // Instrument : Vibraphone — doux et agréable
            channel.programChange(11);
            // Préchauffage du moteur MIDI
            executor.submit(() -> {
                try {
                    channel.noteOn(60, 0);
                    Thread.sleep(10);
                    channel.noteOff(60);
                } catch (Exception e) {}
            });
        } catch (Exception e) {
            System.out.println("Son non disponible: "
                + e.getMessage());
        }
    }

    /**
     * Joue un son court lors d'un déplacement de tuile.
     * Note Do central (60), vélocité modérée.
     */
    public void playMove() {
        if (!enabled || channel == null) return;
        executor.submit(() -> {
            try {
                channel.noteOn(60, 60);
                Thread.sleep(80);
                channel.noteOff(60);
            } catch (Exception e) {}
        });
    }

    /**
     * Joue un son lors de la fusion de deux tuiles.
     * La hauteur de la note dépend de la valeur
     * de la tuile fusionnée (plus grand = plus aigu).
     * @param value valeur de la tuile après fusion
     */
    public void playMerge(int value) {
        if (!enabled || channel == null) return;
        executor.submit(() -> {
            try {
                // Calculer la note selon la valeur
                int note = 60 + (int)(
                    Math.log(value) / Math.log(2)) * 2;
                note = Math.min(note, 100);
                channel.noteOn(note, 90);
                Thread.sleep(150);
                channel.noteOff(note);
            } catch (Exception e) {}
        });
    }

    /**
     * Joue une mélodie ascendante pour célébrer
     * l'atteinte de la tuile 2048 (victoire).
     */
    public void playWin() {
        if (!enabled || channel == null) return;
        executor.submit(() -> {
            try {
                // Accord majeur ascendant
                int[] notes = {60, 64, 67, 72};
                for (int note : notes) {
                    channel.noteOn(note, 100);
                    Thread.sleep(150);
                    channel.noteOff(note);
                }
            } catch (Exception e) {}
        });
    }

    /**
     * Joue une mélodie descendante pour signaler
     * la fin de partie (game over).
     */
    public void playGameOver() {
        if (!enabled || channel == null) return;
        executor.submit(() -> {
            try {
                // Accord majeur descendant
                int[] notes = {72, 67, 64, 60};
                for (int note : notes) {
                    channel.noteOn(note, 100);
                    Thread.sleep(200);
                    channel.noteOff(note);
                }
            } catch (Exception e) {}
        });
    }

    /**
     * Active ou désactive les sons du jeu.
     * Si désactivé, arrête immédiatement toutes
     * les notes en cours.
     * @param enabled true pour activer, false pour couper
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled && channel != null)
            channel.allNotesOff();
    }

    /**
     * @return true si le son est activé
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Ferme proprement le synthétiseur MIDI
     * et arrête le thread de son.
     */
    public void close() {
        try {
            executor.shutdownNow();
            if (synthesizer != null)
                synthesizer.close();
        } catch (Exception e) {}
    }
}