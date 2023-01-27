package task17API;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import lombok.Data;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static properties.Properties.credentialsProperties;

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
    static ResponseSpecBuilder responseNoError = new ResponseSpecBuilder();
    static ResponseSpecBuilder responseError = new ResponseSpecBuilder();
    static ResponseSpecBuilder responseCurrentYear = new ResponseSpecBuilder();
    static RequestSpecBuilder keyParam = new RequestSpecBuilder();

    /**
     * Общие параметры для всех запросов
     */
    private static void setMainParams() {
        RequestSpecBuilder keyParameter = new RequestSpecBuilder();
        keyParameter.addParam("key", "wvS9fmlcgT6jOIO6tyhESV55F6dbNpk3PeWkobf8");
        RestAssured.requestSpecification = keyParameter.build();
    }

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = credentialsProperties.url();
        RestAssured.port = 443;
        RestAssured.basePath = "/api/v2/public";

        setMainParams();
        //Общие проверки для всех ответов
        responseStatus.expectStatusCode(200);
        responseNoError.expectBody("response.messages.type", not(hasItem("error")));
        responseError.expectBody("response.messages.type", hasItem("error"));
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
                .spec(responseNoError.build())
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
     * b year = 2019, day_type не указан:
     * 1 Возвращается HTTP-код 200
     * 2 В ответе присутствуют записи только за 2019 год
     * 3 В ответе присутствую записи обоих типов: короткий день и выходной
     */
    @Test
    void TestB() {
        JsonPath jsonPath = when()
                .get("/Calendar/GetHolidays?year=2019")
                .then()
                .spec(responseNoError.build())
                .spec(responseStatus.build())
                .extract()
                .body().jsonPath();
        List<HolidayItem> items = jsonPath.getList("response.items", HolidayItem.class);
        SoftAssert soft = new SoftAssert();
        boolean holidayFound = false;
        boolean shortdayFound = false;
        for (HolidayItem item : items) {
            soft.assertEquals(item.getDateParsed().getYear(), 2019,
                    "В теле ответа есть год, не равный 2019");
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
                .spec(responseNoError.build())
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
                .spec(responseNoError.build())
                .spec(responseCurrentYear.build())
                .body("response.items.type", everyItem(equalTo("holy_day")));
    }

    /**
     * Вызываю метод неправильно, ожидая перенаправления на страницу с текстом о необходимости залогироваться.
     * Переделать наверное надо. Как-то неточно проверяется
     */
    @Test
    void testE1() {
        when()
                .get("/Calendar/GetHoliday")
                .then()
                .body(containsString("login"))
        ;
    }

    /**
     * Проверка ввода некорректного года
     */
    @Test
    void testE2() {
        when()
                .get("/Calendar/GetHolidays?year=20149")
                .then()
                .statusCode(500)
        ;
    }

    /**
     * Проверка ввода некорректного айди пользователя
     */
    @Test
    void testE3() {
        when()
                .get("/Calendar/GetToday?user_id=123")
                .then()
                .spec(responseError.build())
        ;
    }

    /**
     * Проверка ввода без обязательного параметра user_id
     */
    @Test
    void testE4() {
        when()
                .get("/Calendar/GetToday")
                .then()
                .spec(responseError.build())
        ;
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