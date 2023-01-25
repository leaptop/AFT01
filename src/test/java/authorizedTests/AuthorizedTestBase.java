package authorizedTests;

import com.codeborne.selenide.WebDriverRunner;
import helpers.TestBase;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.open;
import static properties.Properties.credentialsProperties;

/**
 * @author Алексеев Степан
 * @date 25.12.2022
 */
public class AuthorizedTestBase extends TestBase {
    /**
     * Авторизация по АПИ.
     */
    @BeforeEach
    public void authorizeViaHTTP() {
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
        Request getRequest = new Request.Builder()//это нужно чтобы получить заголовки, без которых post не сработает
                .url(credentialsProperties.url())
                .addHeader("Connection", "keep-alive")
                .build();
        Request postRequest = new Request.Builder()
                .url(credentialsProperties.urlLoginCheck())
                .addHeader("Connection", "keep-alive")
                .post(formBody)
                .build();
        try {
            Response response1 = client.newCall(getRequest).execute();
            Response response2 = client.newCall(postRequest).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }//2 end
        open(credentialsProperties.urlProdNoLogin());
        WebDriverRunner.clearBrowserCache();

        cookieManager.getCookieStore().getCookies().forEach(httpCookie -> {//3c
            org.openqa.selenium.Cookie cookie = new org.openqa.selenium
                    .Cookie(
                    httpCookie.getName(),
                    httpCookie.getValue(),
                    httpCookie.getDomain(),
                    httpCookie.getPath(),
                    null
            );
            WebDriverRunner.driver().getWebDriver().manage().addCookie(cookie);
        });
    }
}
