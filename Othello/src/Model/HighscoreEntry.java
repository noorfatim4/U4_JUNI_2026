package Model;

/**
 * HighscoreEntry klassen representerar en post i highscore-listan
 * med ett namn och ett poängresultat.
 *
 * @author Noor-Fatima Nabi & Fauzia Bhuiyan
 */
public class HighscoreEntry {
    private String name;
    private int score;

    /**
     * Denna kontruktor skapar en ny highscore-post
     * @param name
     * @param score
     * @author Fauzia Bhuiyan
     */
    public HighscoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score; }

    @Override
    public String toString() {
        return name + " - " + score;
    }
}

