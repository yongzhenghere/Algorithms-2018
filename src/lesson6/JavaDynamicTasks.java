package lesson6;

import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
     // Трудоемкость: T = O(n * n)
     // Ресурсоемкость: R = O(n)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() == 0 || list.size() == 1) return list;
        int[] lens = new int[list.size()];
        Arrays.fill(lens, 1);
        int[] preIndex = new int[list.size()];
        Arrays.fill(preIndex, -1);
        int maxLengthIndex = 0;
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && lens[j] + 1 > lens[i]) {
                    lens[i] = lens[j] + 1;
                    preIndex[i] = j;
                    if (lens[maxLengthIndex] < lens[i]) {
                        maxLengthIndex = i;
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        int index = maxLengthIndex;
        while (index != -1) {
            result.add(list.get(index));
            index = preIndex[index];
        }
        Collections.sort(result);
        return result;
    }
    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
     // Трудоемкость: T = O(n * m)
     // Ресурсоемкость: R = O(n * m)
    public static int shortestPathOnField(String inputName) throws IOException{
        List<String> numList = new ArrayList<>();
        int height = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                numList.add(line.replaceAll(" ", ""));
                height++;
            }
        }
        int width = numList.get(0).length();
        int[][] minSum = new int[height][width];
        minSum[0][0] = numList.get(0).charAt(0) - 48;
        for (int i = 1; i < height; i++) {
            minSum[i][0] = numList.get(i).charAt(0) - 48 + minSum[i - 1][0];
        }
        for (int i = 1; i < width; i++) {
            minSum[0][i] = numList.get(0).charAt(i) - 48 + minSum[0][i - 1];
        }
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                int minPreviousSum = Math.min( minSum[j -1][i -1], Math.min(minSum[j - 1][i], minSum[j][i - 1]));
                minSum[j][i] = numList.get(j).charAt(i) - 48 + minPreviousSum;
            }
        }
        return minSum[height - 1][width - 1];
    }
    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
