package word;

import java.util.*;
import java.util.stream.Collectors;

public class WordStat {

    abstract class IntegerComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer i1, Integer i2) {
            return i2.compareTo(i1);
        }
    }

    public static void wordStat(String str) {
        String[] phrase = str.split(" ");
        int amountPhrases = phrase.length - 1;
        System.out.println("iii 1. Количество слов в тексте: " + amountPhrases);

        System.out.println("iii 2. TOP10 самых часто упоминаемых слов, упорядоченных по количеству упоминаний в\n" +
                "обратном порядке и отсортированный по алфавиту : ");
        TreeMap<String, Integer> words = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);      // сортируем по алфавиту и определяем цитируемость
            }
        });
        for (String line : phrase) {                    // считаем вхождение строк
            if (words.containsKey(line)) {
                words.put(line, 1 + words.get(line));
            } else {
                words.put(line, 1);
            }
        }
        words.entrySet().stream()                       // сортируем по частоте цитируемости
                .limit(10)                      // ТОП10 (не более 10 наиболее цитируемых)
                .sorted((i1, i2) -> {
                    return i1.getValue().compareTo(i2.getValue()) * -1;
                })
                .forEach(e -> System.out.print("  " + e.getValue() + "  " + e.getKey() + " "));
    }
}