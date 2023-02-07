package word;

import java.util.*;

public class WordStat {

    public static void wordStat(String str) {
        String[] phrase = str.split(" ");
        int amountPhrases = phrase.length;
        System.out.println("iii 1. Количество слов в тексте: " + amountPhrases);

        System.out.println("iii 2. TOP10 самых часто упоминаемых слов, упорядоченных по количеству упоминаний в\n" +
                "обратном порядке и отсортированный по алфавиту : ");
        // сортируем по алфавиту и определяем цитируемость
        Map<String, Integer> words = new TreeMap<>(String::compareToIgnoreCase);
        for (String line : phrase) {                    // считаем вхождение строк
            if (words.containsKey(line)) {
                words.put(line, 1 + words.get(line));
            } else {
                words.put(line, 1);
            }
        }
        words.entrySet().stream()                       // сортируем по частоте цитируемости
                .limit(10)                      // ТОП10 (не более 10 наиболее цитируемых)
                .sorted((i1, i2) -> i2.getValue().compareTo(i1.getValue()))
                .forEach(e -> System.out.print("  " + e.getValue() + "  " + e.getKey() + " "));
    }
}