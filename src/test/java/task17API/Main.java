package task17API;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import lombok.Data;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

/**
 * Реализуй автотесты для метода GetHolidays . Проверки реализуй и через валидацию JSON и через перевод JSON к объекту
 * и валидацию уже объекта(часть тестов одним способом, часть - другим):
 * a Без переданных параметров:
 * 1 Возвращается HTTP-код 200
 * 2 В ответе присутствуют записи только за текущий год
 * 3 В ответе присутствуют записи обоих типов: короткий день и выходной
 * <p>
 * b year = 2019, day_type не указан:
 * 1 Возвращается HTTP-код 200
 * 2 В ответе присутствуют записи только за 2019 год
 * 3 В ответе присутствую записи обоих типов: короткий день и выходной
 * <p>
 * c day_type =SHORT_DAY, year не указан
 * 1 Возвращается HTTP-код 200
 * 2 В ответе присутствуют записи только за текущий год
 * 3 В ответе присутствуют записи только указанного типа
 * <p>
 * d day_type =HOLY_DAY, year не указан (аналогично предыдщему)
 * <p>
 * e Подумай какие можно реализовать негативные автотесты и реализуй несколько на свое усмотрение. Инъекции не
 * используем!
 *
 * @author Алексеев Степан
 * @date 16.01.2023
 */
public class Main {
    static ResponseSpecBuilder responseStatus = new ResponseSpecBuilder();
    static ResponseSpecBuilder responseError = new ResponseSpecBuilder();
    static ResponseSpecBuilder responseCurrentYear = new ResponseSpecBuilder();

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://tt.quality-lab.ru";
        RestAssured.port = 443;
        RestAssured.basePath = "/api/v2/public";
        //Общие параметры для всех запросов
        RequestSpecBuilder keyParameter = new RequestSpecBuilder();
        keyParameter.addParam("key", "wvS9fmlcgT6jOIO6tyhESV55F6dbNpk3PeWkobf8");
        RestAssured.requestSpecification = keyParameter.build();
        //Общие проверки для всех ответов
        responseStatus.expectStatusCode(200);
        responseError.expectBody("response.messages.type", not(hasItem("error")));
        responseCurrentYear.expectBody("response.items.date",
                everyItem(startsWith(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")))));
    }

    /**
     * a Без переданных параметров:
     * 1 Возвращается HTTP-код 200
     * 2 В ответе присутствуют записи только за текущий год
     * 3 В ответе присутствуют записи обоих типов: короткий день и выходной
     */
    @Test
    void testA() {
        JsonPath jsonPath = when()
                .get("/Calendar/GetHolidays")
                .then()
                .spec(responseError.build())
                .spec(responseStatus.build())
                .spec(responseCurrentYear.build())
                .extract()
                .body().jsonPath();
        List<HolidayItem> items = jsonPath.getList("response.items", HolidayItem.class);
        SoftAssert soft = new SoftAssert();
        boolean holidayFound = false;
        boolean shortdayFound = false;
        for (HolidayItem item : items) {
            soft.assertEquals(item.getDateParsed().getYear(), LocalDate.now().getYear(),
                    "Текущий год не совпадает с годом в теле ответа");
            switch (item.getType_id()) {
                case 2:
                    holidayFound = true;
                    break;
                case 1:
                    shortdayFound = true;
                    break;
                default:
                    soft.fail("Обнаружен день неизвестного типа: " + item.getType_id());
            }
        }
        soft.assertTrue(holidayFound, "Не найдено ни одного дня с типом holy_day");
        soft.assertTrue(shortdayFound, "Не найдено ни одного дня с типом short_day");
        soft.assertAll();
    }
    /**
     * Подумай какие можно реализовать негативные автотесты и реализуй несколько на свое усмотрение. Инъекции не
     * используем!
     * Вызываю метод неправильно, ожидая перенаправления на страницу с текстом о необходимости залогироваться.
     * Переделать наверное надо. Как-то неточно проверяется
     */
    @Test
    void testE1() {
       // JsonPath jsonPath =
                when()
                .get("/Calendar/GetHoliday")
                .then()
                //.extract()
                .body(containsString("login"))
                      //  .jsonPath()
                ;
        String str="";
    }

    /**
     * b year = 2019, day_type не указан:
     * * 1 Возвращается HTTP-код 200
     * * 2 В ответе присутствуют записи только за 2019 год
     * * 3 В ответе присутствую записи обоих типов: короткий день и выходной
     */
    @Test
    void testB() {
        when()
                .get("/Calendar/GetHolidays?year=2019")
                .then()
                .spec(responseError.build())
                .spec(responseStatus.build())
                .body("response.items.date",
                        everyItem(startsWith(LocalDate.of(2019, 01, 01)
                                .format(DateTimeFormatter.ofPattern("yyyy")))))
                .body("response.items.type_id", both(hasItem(2)).and(hasItem(1)));
    }

    /**
     * c day_type =SHORT_DAY, year не указан
     * 1 Возвращается HTTP-код 200
     * 2 В ответе присутствуют записи только за текущий год
     * 3 В ответе присутствуют записи только указанного типа
     */
    @Test
    void testC() {
        when()
                .get("/Calendar/GetHolidays?day_type=short_day")
                .then()
                .spec(responseStatus.build())
                .spec(responseError.build())
                .spec(responseCurrentYear.build())
                .body("response.items.type", everyItem(equalTo("short_day")));
    }

    /**
     * d day_type =HOLY_DAY, year не указан
     * 1 Возвращается HTTP-код 200
     * 2 В ответе присутствуют записи только за текущий год
     * 3 В ответе присутствуют записи только указанного типа
     */
    @Test
    void testD() {
        when()
                .get("/Calendar/GetHolidays?day_type=holy_day")
                .then()
                .spec(responseStatus.build())
                .spec(responseError.build())
                .spec(responseCurrentYear.build())
                .body("response.items.type", everyItem(equalTo("holy_day")));
    }


}


@Data
class HolidayItem {
    private String date;
    private String type;
    private int type_id;

    public LocalDate getDateParsed() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}