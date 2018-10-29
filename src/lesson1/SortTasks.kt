@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*


/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
 * каждый на отдельной строке. Пример:
 *
 * 13:15:19
 * 07:26:57
 * 10:00:03
 * 19:56:14
 * 13:15:19
 * 00:40:31
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 00:40:31
 * 07:26:57
 * 10:00:03
 * 13:15:19
 * 13:15:19
 * 19:56:14
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
 // Трудоемкость T = O(n * log(n))
 // Ресурсоемкость R = O(n)
fun sortTimes(inputName: String, outputName: String) {
    val dateList = ArrayList<Date>()
    val formatter = SimpleDateFormat("HH:mm:ss")
    if (inputName != null && outputName != null) {
        for (line in File(inputName).readLines()) {
            val date = formatter.parse(line)
            dateList.add(date)
        }
        dateList.sort()
        val writer = File(outputName).bufferedWriter()
        for (element in dateList) {
            writer.write(formatter.format(element))
            writer.newLine()
        }
        writer.close()
    }
}
    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    fun sortAddresses(inputName: String, outputName: String) {
        TODO()
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
     // Трудоемкость T = O(n * log(n))
     // Ресурсоемкость R = O(n)
    fun sortTemperatures(inputName: String, outputName: String) {
        val input = File(inputName).readLines()
        val buffer = input.asSequence().map { it.toDouble() }.sorted().toList()
        val output = buffer.asSequence().map { it.toString() }.toList()
        Files.write(Paths.get(outputName), output)
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
     // Трудоемкость T = O(n)
     // Ресурсоемкость R = O(n)
    fun sortSequence(inputName: String, outputName: String) {
        val list = ArrayList<Int>()
        var count = 1
        var maxCount = 1
        for (line in File(inputName).readLines()) {
            val num = Integer.parseInt(line)
            list.add(num)
        }
        val unsortedList = ArrayList(list)
        list.sort()
        var pointer = list[0]
        var maxCommon = list[0]
        for (i in 1 until list.size) {
            if (list[i] == pointer) {
                count++
            } else {
                if (count > maxCount) {
                    maxCommon = list[i - 1]
                    maxCount = count
                }
                pointer = list[i]
                count = 1
            }
        }
        if (count > maxCount) {
            maxCommon = list[list.size - 1]
        }
        val writer = File(outputName).bufferedWriter()
        for (num in unsortedList) {
            if (num != maxCommon) {
                writer.write(num!!.toString())
                writer.newLine()
            }
        }
        for (i in 0 until Integer.max(count, maxCount)) {
            writer.write(Integer.toString(maxCommon))
            writer.newLine()
        }
        writer.close()
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
     // Трудоемкость T = O(n)
     // Ресурсоемкость R = O(n)
    fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
        val list = ArrayList<T>()
        System.arraycopy(first, 0, second, 0, first.size)
        for (i in second.indices) {
            list.add(second[i]!!)
        }
        for (i in 0 until list.size - 1) {
            second[i] = list[i]
        }
        Arrays.sort(second)
    }



