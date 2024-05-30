import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSearch {
    public static void main(String[] args) {
        String pattern = readPatternFromKeyboard();
        // wczytywanie z klawiatury
        String currentDirectory = System.getProperty("user.dir");
        searchInDirectory(currentDirectory, pattern);
    }
    // weź bierzący katoalog i szukaj plików tekstówych txt

    private static String readPatternFromKeyboard() {
        System.out.print("Podaj wzorzec do wyszukania (* - dowolny ciąg znaków, ? - dowolny pojedynczy znak): ");
        return new Scanner(System.in).nextLine();
    }

    private static void searchInDirectory(String directory, String pattern) {
        File dir = new File(directory);
        File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                searchInFile(file, pattern);
            }
        } else {
            System.out.println("Brak plików z rozszerzeniem .txt w bieżącym katalogu.");
        }
    }

    private static void searchInFile(File file, String pattern) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                Pattern p = Pattern.compile(pattern.replace("*", ".*").replace("?", "."));
                Matcher m = p.matcher(line);
                while (m.find()) {
                    System.out.printf("Plik: %s, Linia: %d, Znaleziony fragment: %s%n", file.getName(), lineNumber,
                            m.group());
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas czytania pliku: " + e.getMessage());
        }
    }

    private static List<Integer> findAllMatches(String text, String pattern) {
        List<Integer> indexes = new ArrayList<>();
        int patternLength = pattern.length();
        int index = 0;

        while ((index = text.indexOf(pattern, index)) != -1) {
            indexes.add(index);
            index += patternLength;
        }

        return indexes;
    }
}
