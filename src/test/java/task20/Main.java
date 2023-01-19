package task20;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Алексеев Степан
 * @date 19.01.2023
 */
public class Main {
    /**
     * 1 Создай метод который считает остаток от деления одного числа на другое (передаем два параметра:
     * делимое и делитель)
     * <p>
     * 6  В методе из задания 1 выбрасывай непроверяемое исключение, если остаток от деления = 1 и
     * проверяемое исключение,
     * * если остаток от деления равен 4
     *
     * @param a числитель
     * @param b знаменатель
     * @return остаток от деления
     */
    public int divisionMod(int a, int b) throws CheckedException4, CheckedException55 {
        int mod = a % b;
        if (mod == 1) {
            throw new UncheckedException1("Остаток от деления = 1");
        } else if (mod == 4) {
            throw new CheckedException4("Oстаток от деления равен 4");
        } else if (a == 5 && b == 5) {
            throw new CheckedException55();
        }
        return mod;
    }

    /**
     * 2 В тест-методе создай два массива, заполни их числами от -5 до 5
     * 3 Для каждого числа из первого массива выведи остаток от деления на каждое число из другого массива. В случае
     * невозможности деления выведи “Бесконечность”
     * 4 Для каждого остатка из подзадания 3 посчитать количество пар делимое+делитель, приводящих к такому результату (да я
     * * в курсе что тут не будет исключения, отрабатываем работу с коллекциями)
     */
    @Test
    void test1() {
        int arr1[] = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
        int arr2[] = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
        Map<Integer, Integer> mapa = new HashMap<>();
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                try {
                    int res = divisionMod(arr1[i], arr2[j]);
                    System.out.println(res);
                } catch (ArithmeticException e) {
                    mapa.put(arr1[i], arr2[j]);
                    System.out.println("Бесконечность");
                } catch (CheckedException4 | CheckedException55 | UncheckedException1 e) {

                }
            }
        }
        System.out.println(String.format("Число пар делимое/делитель, которые выбрасывают арифметическое исключение: " +
                "%d", mapa.size()));
    }
}

/**
 * 5 Создай два своих типа исключения: одно непроверяемое, другое проверяемое
 * <p>
 * 7 В случае возникновения добавленных тобой исключений выводи текст “Намеренно”
 */
class CheckedException4 extends Exception {//проверяемое

    public CheckedException4(String s) {
        System.out.println("Намеренно");
    }
}

/**
 * хоть и можно проверить, но всё равно называется непроверяемым
 */
class UncheckedException1 extends RuntimeException {

    public UncheckedException1(String s) {
        System.out.println("Намеренно");
    }
}

/**
 * 8 Создай еще один тип проверяемого исключения, выбрасывай его если делитель и делимое равно 5.
 * В случае возникновения этого типа исключения: выводи сообщение “5:5!” в консоль и выбрасывай сообщение дальше.
 * Твой юнит-тест должен его вывести
 */
class CheckedException55 extends RuntimeException {
    public CheckedException55() {
        System.out.println("5:5!");
    }
}