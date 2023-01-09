package http;

import com.codeborne.selenide.WebDriverRunner;
import helpers.TestBase;
import okhttp3.*;
//import okhttp3.internal.JavaNetCookieJar;
import okhttp3.JavaNetCookieJar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.http.FormEncodedData;
import pages.selenide.CalendarPO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.fail;
import static properties.AeonBitsProperties.credentialsProperties;
import static sun.net.www.protocol.http.HttpURLConnection.userAgent;

/**
 * @author Алексеев Степан
 * @date 03.01.2023
 */
public class Task14HTTPAuthorization {
    private final OkHttpClient client = new OkHttpClient();

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");


    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType JSON = MediaType.get(
            "application/json; charset=utf-8");
    //     public static final MediaType JSON = MediaType.get("application/json; " +
//                "charset=1251");
    public CalendarPO calendarPO;

    @Test
    public void post2() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "{'_csrf_token': ''," +
                "'_username': '" + credentialsProperties.username() + "'," +
                "'_password': '" + credentialsProperties.password() + "'," +
                "'_submit': 'Войти'}";
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                        "q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8," +
                        "application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Accept-Language", "ru,en;q=0.9,ru-RU;q=0.8,en-US;" +
                        "q=0.7")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Length", "187")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//Cookie: _gcl_au=1.1.101985907.1669973365; _ga=GA1.2.1638702588.1669973366;
// side-menu=minimized; m-datatable__local_data-1-m-meta={%22pagination%22:{%22page%22:1%2C%22perpage%22:10%2C%22total%22:2%2C%22pages%22:1}%2C%22sort%22:{%22sort%22:%22%22%2C%22field%22:%22%22}%2C%22query%22:{}}; PHPSESSID=a15dfa9b51c96d24016c975dc23d032a
                .addHeader("Host", "tt.quality-lab.ru")
                .addHeader("Origin", "https://tt.quality-lab.ru")
                .addHeader("Referer", "https://tt.quality-lab.ru/login")
                .addHeader("sec-ch-ua",
                        "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\"" +
                                ", \"Google Chrome\";v=\"108\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Sec-Fetch-Dest", "document")
                .addHeader("Sec-Fetch-Mode", "navigate")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("Sec-Fetch-User", "?1")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", ("Mozilla/5.0 (Windows NT 10.0; Win64;" +
                        " x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/108.0.0.0 Safari/537.36)"))
                .url("https://tt.quality-lab.ru/login_check")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
    }

    @Test
    public void fromLK() {

        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        ChromeOptions options = new ChromeOptions();
        WebDriver chromedriver = new ChromeDriver(options);
        chromedriver.manage().window().maximize();

        CookieManager cookieManager = new CookieManager();//1 start
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieManager);

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .followRedirects(false)
                .build();//1 end
        RequestBody formBody = null;
        try {
            formBody
                    = new FormBody.Builder(StandardCharsets.UTF_8)//2
                    .add("_csrf_token", "")
                    .add("_username", credentialsProperties.username())
                    .add("_password", credentialsProperties.password())
                    .add("_submit", "Войти")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Request request = new Request.Builder()
                .url("https://tt.quality-lab.ru/login_check")
                .addHeader("Connection", "keep-alive")
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }//2 end
        chromedriver.get("https://tt.quality-lab.ru");//3a
        chromedriver.manage().deleteAllCookies();//3b

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = simpleDateFormat.parse("2023-09-09");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        cookieManager.getCookieStore().getCookies().forEach(httpCookie -> {//3c
            org.openqa.selenium.Cookie cookie = new org.openqa.selenium
                    .Cookie(
                    httpCookie.getName(),
                    httpCookie.getValue(),
                    httpCookie.getDomain(),
                    httpCookie.getPath(),
                    date
            );
            chromedriver.manage().addCookie(cookie);
        });
        chromedriver.get("https://tt.quality-lab.ru/calendar/");//4 Здесь
        // пользователь д.б. уже авторизован. Но пока высвечивается сообщение:
        // "No session available, it either timed out or cookies are not enabled."
//        calendarPO = open("https://tt.quality-lab.ru/calendar/", CalendarPO.class)
//                .waitForCalendarToLoad();
        // openCalendar();
        // checkCurrentMonthAndYear();
    }

    @Test
    public void whenSendPostRequestWithAuthorization_thenCorrect()
            throws IOException {
        String postBody = "test post";

        Request request = new Request.Builder()
                .url("https://tt.quality-lab.ru/login_check")
                .addHeader("Authorization",
                        Credentials.basic(credentialsProperties.username(),
                                credentialsProperties.password()))
                .post(RequestBody.create(
                        MediaType.parse("text/x-markdown"), postBody))
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
        Assertions.assertEquals(200, response.code());
    }

    /**
     * Returns all of the cookies from a set of HTTP response headers.
     */
    public static List<Cookie> parseAll(HttpUrl url, Headers headers) {
        List<String> cookieStrings = headers.values("Set-Cookie");
        List<Cookie> cookies = null;
        for (int i = 0, size = cookieStrings.size(); i < size; i++) {
            Cookie cookie = Cookie.parse(url, cookieStrings.get(i));
            if (cookie == null) continue;
            if (cookies == null) cookies = new ArrayList<>();
            cookies.add(cookie);
        }
        return cookies != null
                ? Collections.unmodifiableList(cookies)
                : Collections.emptyList();
    }


    @Test
    public void runJurassic() throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    @Test
    public void smth2() {
        //PostExample example = new PostExample();
        String json = bowlingJson(credentialsProperties.username(),
                credentialsProperties.password());
        String response = "null response";
        try {
            response = post("https://tt-testing.quality-lab.ru/login", json);
        } catch (Exception e) {

        }

        System.out.println("RESPONSE: " + response);
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String bowlingJson(String player1, String player2) {
        return
                "{'_csrf_token':'',"
                        + "'_username':'" + player1 + "',"
                        + "'_password':'" + player2 + "',"
                        + "'_submit':'Войти'}";

//                + "'dateStarted':1367702378785,}";
//                + "'players':["
//                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
//                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
//                + "]}";
    }

    /**
     * Открываем календарь и ждём появления сообщения о загрузке. Потом ждём его исчезновения.
     */

    // @BeforeEach
    public void openCalendar() {
        calendarPO = open("https://tt.quality-lab.ru/calendar/", CalendarPO.class)
                .waitForCalendarToLoad();
    }

    @Test
    public void smth() {
        //       open("https://tt-testing.quality-lab.ru/login");

        RequestBody formBody = new FormBody.Builder()
                .add("_csrf_token", "")
                .add("_username", "")
                .add("_password", "")
                .add("_submit", "Войти")
                .build();
        Request request = new Request.Builder()
                .url("https://tt-testing.quality-lab.ru/login")
                .post(formBody)
                .build();
        openCalendar();
        checkCurrentMonthAndYear();

        try {
            Response response = client.newCall(request).execute();

            // Do something with the response.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkCurrentMonthAndYear() {
        Date date = Date.from(Instant.now());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("LLLL yyyy", Locale.getDefault());
        String result = newDateFormat.format(date);
        Assertions.assertAll(
                () -> Assertions.assertEquals(result, calendarPO.getCurrentMonthAndYear(),
                        "Текущие месяц и год не совпадают с выведенными на сайте"),
                () -> Assertions.assertTrue(calendarPO.getWorkDayLInks().size() > 0,
                        "Не найдены рабочие дни в месяце"),
                () -> Assertions.assertTrue(calendarPO.getHolidayLinks().size() > 0,
                        "Не найдены выходные дни в месяце")
        );
    }
//    @Test
//    public void run3() throws Exception {
//        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "Square Logo")
//                .addFormDataPart("image", "logo-square.png",
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                .build();
//
//        Request request = new Request.Builder()
//                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID, )
//                .url("https://api.imgur.com/3/image")
//                .post(requestBody)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful())
//                throw new IOException("Unexpected code " + response);
//
//            System.out.println(response.body().string());
//        }
//    }

    @Test
    public void run2() throws Exception {
        String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    @Test
    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            System.out.println("Server: " + response.header("Server"));
            System.out.println("Date: " + response.header("Date"));
            System.out.println("Vary: " + response.headers("Vary"));
        }
    }


//    public void cookieSpmething(){
//       // Сначала создаем куки-менеджер и прикрепляем его к HTTP-клиенту:
//        CookieManager cookieManager = new CookieManager();
//        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
//        JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieManager);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .cookieJar(cookieJar)
//                .followRedirects(false)
//                .build();
//
//    }
//    @Test
//    public void whenGetRequest_thenCorrect() throws IOException {
//        Request request = new Request.Builder()
//                .url("https://tt-testing.quality-lab.ru/login")
//                .build();
//
//
//        OkHttpClient chromedriver = new OkHttpClient();
//        Call call = chromedriver.newCall(request);
//
//
//        call.enqueue(new Callback() {
//            public void onResponse(Call call, Response response)
//                    throws IOException {
//                // ...
//            }
//
//            public void onFailure(Call call, IOException e) {
//                fail();
//            }
//        });
//
//        Response response = call.execute();
//
//        Assertions.assertEquals(response.code(), 200);
//    }

}
