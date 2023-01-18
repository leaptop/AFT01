package task19;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author Алексеев Степан
 * @date 17.01.2023
 */
public class Main {
    /**
     * создать список, состоящий из элементов исходного списка умноженных на 10
     * создать список, состоящий из элементов исходного списка, содержащих цифру 3
     * найти минимальное значение
     * найти максимальное значение
     * перевести каждое число в строку, добавить с обоих сторон по восклицательному знаку и склеить все в одну строку. (должно получиться: “!1!!2!!3!!4!!5!!6!!7!!8!!9!!10!!11!!12!!13!!14!!15!!16!...”)
     * определить есть ли в списке делители числа 347 (т.е. число 347 делится на число без остатка). Возвращает boolean
     * проверить что все числа в списке больше 0 и меньше 101. Возвращает boolean
     * разбить на группы по остатку от деления на 5. Результат Map<Integer, List<Integer>>, где ключ это остаток от деления, а значение - список цифр, дающих такой остаток
     * для каждого числа в списке проверить assert-ом что оно больше 50 и обернуть это все в assertAll JUnit-а
     */
    public ArrayList<Integer> getList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        return list;
    }

    ArrayList<Integer> ls = getList();

    public Integer getSumOfElements(ArrayList<Integer> list) {
        return list.stream().reduce(0, (a, b) -> a + b);
    }

    public OptionalDouble getAverage(ArrayList<Integer> list) {
        return list.stream().mapToInt(e -> e).average();
    }

    public List<Integer> getMultipliedBy10(ArrayList<Integer> list) {
        return list.stream().map(a -> a * 10).collect(Collectors.toList());
    }

    public List<Integer> getListContainingDigit3(ArrayList<Integer> list) {
        return list.stream().map(a -> String.valueOf(a)).filter(a -> a.contains("3"))
                .map(a -> Integer.parseInt(a)).collect(Collectors.toList());
    }

    public Integer getMin(ArrayList<Integer> list) {
        return list.stream().min(Integer::compare).get();
    }

    /**
     * @param list список для поиска
     * @return возвращает максимальное значение
     */
    public Integer getMax(ArrayList<Integer> list) {
        return list.stream().max(Integer::compare).get();
    }

    /**
     * Переводит каждое число в строку. Добавляет с обеих сторон по восклицательному знаку и склеивает все в одну
     * строку. (должно получиться: “!1!!2!!3!!4!!5!!6!!7!!8!!9!!10!!11!!12!!13!!14!!15!!16!...”)
     *
     * @param list
     * @return
     */
    public String getStringRemake(ArrayList<Integer> list) {
        return list.stream().map(a -> ("!" + a + "!")).reduce("", (a, b) -> a + b);
    }

    /**
     * Определяет есть ли в списке делители числа 347 (т.е. число 347 делится на число без остатка).
     *
     * @param list
     * @return
     */
    public Boolean hasDividerOf347(ArrayList<Integer> list) {
        return list.stream().filter(a -> 347 % a == 0).count() > 0;
    }

    /**
     * Проверяет, что все числа в списке больше 0 и меньше 101
     *
     * @param list
     * @return
     */
    public Boolean checkIfNumberMore0AndLess101(ArrayList<Integer> list) {
        return list.stream().filter(a -> a > 0).filter(a -> a < 101).count() == 100;
    }

    /**
     * Разбивает на группы по остатку от деления на 5. Результат Map<Integer, List<Integer>>, где ключ это остаток от
     * деления, а значение - список цифр, дающих такой остаток:
     *
     * @param list
     * @return
     */
    public Map<Integer, List<Integer>> getMapOfMod5(ArrayList<Integer> list) {
        return list.stream().collect(Collectors.groupingBy(a -> a % 5));
    }

    @Test
    public void test0() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        //для каждого числа в списке проверить assert-ом что оно больше 50 и обернуть это все в assertAll JUnit-а:
        assertAll("smt", (Executable) list.stream()
                .map( a -> (() -> (Assertions.assertTrue(a > 50)))));


        list.stream().forEach(a -> Assertions.assertTrue(a > 0));
        //  assertAll( list.stream().forEach(a -> Assertions.assertTrue(a > 0)))        ;


        String pause = "";
    }

    @Test
    public void test1() {
        Assertions.assertEquals(5050, getSumOfElements(ls));
    }

    @Test
    public void test2() {
        Assertions.assertEquals(50.5, getAverage(ls).getAsDouble());
    }

    @Test
    public void test3() {
        List<Integer> processedList = getMultipliedBy10(ls);
        for (int i = 0; i < ls.size(); i++) {
            Assertions.assertEquals(ls.get(i) * 10, processedList.get(i));
        }
    }

    @Test
    public void test4() {
        int[] control = {3, 13, 23, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 43, 53, 63, 73, 83, 93};
        List<Integer> result = getListContainingDigit3(ls);
        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(control[i], result.get(i));
        }
    }

    @Test
    public void test5() {
        Assertions.assertEquals(1, getMin(ls));
    }

    @Test
    public void test6() {
        Assertions.assertEquals(100, getMax(ls));
    }

    @Test
    public void test7() {
        String control = "!1!!2!!3!!4!!5!!6!!7!!8!!9!!10!!11!!12!!13!!14!!15!!16!!17!!18!!19!!20!!21!!22!" +
                "!23!!24!!25!!26!!27!!28!!29!!30!!31!!32!!33!!34!!35!!36!!37!!38!!39!!40!!41!!42!!43!!44!!45!" +
                "!46!!47!!48!!49!!50!!51!!52!!53!!54!!55!!56!!57!!58!!59!!60!!61!!62!!63!!64!!65!!66!!67!!68!" +
                "!69!!70!!71!!72!!73!!74!!75!!76!!77!!78!!79!!80!!81!!82!!83!!84!!85!!86!!87!!88!!89!!90!!91!" +
                "!92!!93!!94!!95!!96!!97!!98!!99!!100!";
        Assertions.assertEquals(control, getStringRemake(ls));
    }

    @Test
    public void test8() {
        Assertions.assertTrue(hasDividerOf347(ls));
    }

    @Test
    public void test9() {
        Assertions.assertTrue(checkIfNumberMore0AndLess101(ls));
    }

    @Test
    public void test10() {
        Map<Integer, List<Integer>> mapa = getMapOfMod5(ls);
        assertAll(
                () -> Assertions.assertEquals(5, mapa.get(0).get(0)),
                () -> Assertions.assertEquals(100, mapa.get(0).get(19)),
                () -> Assertions.assertEquals(4, mapa.get(4).get(0)),
                () -> Assertions.assertEquals(99, mapa.get(4).get(19))
        );
    }
}