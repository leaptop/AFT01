package task17API;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static properties.Properties.credentialsProperties;

/**
 * Запускать на проде.
 * Реализуй автотесты для метода GetHolidays . Проверки реализуй и через валидацию JSON и через перевод JSON к объекту
 * и валидацию уже объекта(часть тестов одним способом, часть - другим).
 * e Подумай какие можно реализовать негативные автотесты и реализуй несколько на свое усмотрение. Инъекции не
 * используем!
 *
 * @author Алексеев Степан
 * @date 16.01.2023
 */
public class RestAssured17 {
    static ResponseSpecification responseStatus200AndNoErrorSpec;
    static ResponseSpecification responseCurrentYearSpec;

    /**
     * Определяет общие параметры для всех запросов
     */
    private static void setMainParams() {
        RequestSpecBuilder keyParameter = new RequestSpecBuilder();
        keyParameter.addParam("key", "wvS9fmlcgT6jOIO6tyhESV55F6dbNpk3PeWkobf8");
        RestAssured.requestSpecification = keyParameter.build();
    }

    /**
     * Создаёт спецификации для проверок, начальные настройки
     */
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = credentialsProperties.url();
        RestAssured.port = 443;
        RestAssured.basePath = "/api/v2/public";
        setMainParams();
        //Общие проверки, которые можно использовать для всех ответов:
        responseStatus200AndNoErrorSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("response.messages.type", not(hasItem("error")))
                .build();
        responseCurrentYearSpec = new ResponseSpecBuilder()
                .expectBody("response.items.date",
                        everyItem(startsWith(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"))))).build();
    }

    /**
     * a Без переданных параметров:
     * 1 Возвращается HTTP-код 200
     * 2 В ответе присутствуют записи только за текущий год
     * 3 В ответе присутствуют записи обоих типов: короткий день и выходной
     */
    @Test(description = "Проверка метода GetHolidays без параметров")
    void testNoParams() {
        JsonPath jsonPath = when()
                .get("/Calendar/GetHolidays")
                .then()
                .spec(responseStatus200AndNoErrorSpec)
                .spec(responseCurrentYearSpec)
                .log()
                .all()
                .extract()
                .body().jsonPath();
        List<HolidayItem> items = jsonPath.getList("response.items", HolidayItem.class);
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(items.stream().allMatch(a -> a.getTypeEnum().equals(TypeOfDay.HOLIDAY) ||
                a.getTypeEnum().equals(TypeOfDay.SHORTDAY)
        ), String.format("Найдены другие типы дня помимо %s & %s", TypeOfDay.HOLIDAY, TypeOfDay.SHORTDAY));
        soft.assertTrue(items.stream().allMatch(a ->
                        ((Integer) a.getDateParsed().getYear()).equals(LocalDate.now().getYear())),
                "Текущий год не совпадает с годом в теле ответа");
        soft.assertAll();
    }

    /**
     * b year = 2019, day_type не указан:
     * 1 Возвращается HTTP-код 200
     * 2 В ответе присутствуют записи только за 2019 год
     * 3 В ответе присутствую записи обоих типов: короткий день и выходной
     */
    @Test(description = "Проверка с годом 2019 и без указания day_type")
    void testYearAndNoDayType() {
        String year = "2019";
        when()
                .get(String.format("/Calendar/GetHolidays?year=%s", year))
                .then()
                .spec(responseStatus200AndNoErrorSpec)
                .body("response.items.date", everyItem(startsWith(year)))
                .body("response.items.type", everyItem(either(is("holy_day")).or(is("short_day"))))
                .log().all()
        ;
    }

    @DataProvider(name = "dayTypes")
    public Object[] getDifferentDayTypes() {
        return new Object[]{"SHORT_DAY", "HOLY_DAY"};
    }

    /**
     * c day_type =SHORT_DAY, year не указан
     * d day_type =HOLY_DAY, year не указан
     * <p>
     * 1 Возвращается HTTP-код 200
     * 2 В ответе присутствуют записи только за текущий год
     * 3 В ответе присутствуют записи только указанного типа
     */
    @Test(description = "Проверка при неуказанном годе и разныx типах дня", dataProvider = "dayTypes")
    void testDayTypeAndNoYear(String dayType) {
        when()
                .get(String.format("/Calendar/GetHolidays?day_type=%s", dayType))
                .then()
                .spec(responseStatus200AndNoErrorSpec)
                .spec(responseCurrentYearSpec)
                .body("response.items.type", everyItem(equalTo(dayType.toLowerCase())));
    }

    /**
     * Проверка ввода некорректного года
     */
    @Test(description = "Проверка ввода некорректного года")
    void testIncorrectYear() {
        when()
                .get("/Calendar/GetHolidays?year=3023")
                .then()
                .statusCode(200)
                .body("response.count.", equalTo(0))
                .body("items", is(nullValue()))
        ;
    }

    /**
     * Проверка неправильно введённого типа дня
     */
    @Test
    void testIncorrectDayType() {
        when()
                .get("/Calendar/GetHolidays?day_type=holiday")
                .then()
                .statusCode(200)
                .body("response.messages.type", hasItem("error"))
                .body("response.messages.text", hasItem(
                        "Параметр day-type может отсутствовать или принимать одно из значений: SHORT_DAY, HOLY_DAY"))
                .log().all()
        ;
    }
}

/**
 * DTO для хранения дня
 */
//@Data
class HolidayItem {
    private String date;
    private String type;
    private int type_id;
    private TypeOfDay typeEnum;

    public HolidayItem(String date, String type, int type_id) {
        this.date = date;
        this.type = type;
        this.type_id = type_id;
    }

    public LocalDate getDateParsed() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public TypeOfDay getTypeEnum() {
        switch (type) {
            case "holy_day":
                typeEnum = TypeOfDay.HOLIDAY;
                break;
            case "short_day":
                typeEnum = TypeOfDay.SHORTDAY;
                break;
            case "transfer_оff_day":
                typeEnum = TypeOfDay.TRANSFEROFFDAY;
        }
        return typeEnum;
    }
}

/**
 * Перечисление для более читабельных значений дней
 */
enum TypeOfDay {
    HOLIDAY,
    SHORTDAY,
    TRANSFEROFFDAY
}