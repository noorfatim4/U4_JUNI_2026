package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HighscoreManager klassen hanterar en topp-10 highscore-lista
 * som sparas till en textfil
 *
 * @author Noor-Fatima Nabi & Fauzia Bhuiyan
 */
public class HighscoreManager {
    private static final int MAX_ENTRIES = 10;
    private static final String FILE_NAME   = "highscores.txt";
    private final List<HighscoreEntry> highscores = new ArrayList<>();

    /**
     * Konstruktor för att läsa in highscorelista
     * @author Fauzia Bhuiyan
     */
    public HighscoreManager() {
        loadFromFile();
    }

    /**
     * Denna metod läser in highscore-listan från textfilen.
     * @author Noor-Fatima Nabi
     */
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";", 2);
                if (parts.length < 2) continue;
                try {
                    int score = Integer.parseInt(parts[1].trim());
                    highscores.add(new HighscoreEntry(parts[0].trim(), score));
                } catch (NumberFormatException ignored) { }
            }
            sortHighscores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Denna metod sparar den nuvarande highscore-listan till textfilen
     * @author Noor-Fatima Nabi
     */
    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (HighscoreEntry entry : highscores) {
                pw.println(entry.getName() + ";" + entry.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Denna metod sorterar highscore-listan i fallande ordning, högst poäng överst
     * @author Noor-Fatima Nabi
     */
    private void sortHighscores() {
        highscores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
    }

    /**
     * Denna metod kontrollerar om ett poängresultat kvalificerar
     * sig för highscore-listan
     * @param score
     * @return boolean
     * @author Noor-Fatima Nabi & Fauzia Bhuiyan
     */
    public boolean qualifies(int score) {
        if (highscores.size() < MAX_ENTRIES) return true;
        return score > highscores.get(highscores.size() - 1).getScore();
    }

    /**
     * Denna metod lägger till ett nytt resultat i highscore-listan,
     * sorterar om och sparar till filen
     * @param name
     * @param score
     * @author Fauzia Bhuiyan
     */
    public void addScore(String name, int score) {
        highscores.add(new HighscoreEntry(name, score));
        sortHighscores();
        while (highscores.size() > MAX_ENTRIES) {
            highscores.remove(highscores.size() - 1);
        }
        saveToFile();
    }

    /**
     * Denna metod returnerar highscore-listan som en array
     * @author Fauzia Bhuiyan & Noor-Fatima Nabi
     */
    public String[] getHighscoreStrings() {
        String[] result = new String[highscores.size()];
        for (int i = 0; i < highscores.size(); i++) {
            HighscoreEntry e = highscores.get(i);
            result[i] = (i + 1) + ". " + e.getName() + " – " + e.getScore();
        }
        return result;
    }
}
