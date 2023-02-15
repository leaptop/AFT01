package task23control.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import task22.pages.SuccessfulLoginPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

/**
 * @author Алексеев Степан
 * @date 30.01.2023
 */
public class ReportEditPO extends SuccessfulLoginPage {
    /**
     * Универсальный искпас для эмоджи
     */
    private String blankEmojiXPath = "//div[@class='emoji-%s ']/div";

    /**
     * Всплывающее окно с надписью о логировании неправильного числа часов
     */
    private String modalFadeShow = "//div[@id='modal-more-less-hours']";
    /**
     * Кнопка "Отмена" модального окна, сообщающего о нестандартном числе залогированных часов
     */
    private String cancelModal = ".//button[contains(text(), 'Отмена')]";

    /**
     * Кликает кнопку "Отмена" модального окна, сообщающего о нестандартном числе залогированных часов
     */
    @Step("Кликает кнопку \"Отмена\" модального окна")
    public void clickCancelModal8hours() {
        $x(modalFadeShow).$x(cancelModal).click();
    }

    /**
     * Проверка того, что модальное окно о нестандартно залогированном времени появилось
     */
    @Step("Проверка того, что модальное окно о времени появилось")
    public void checkIfModal8hoursWindowAppeared() {
        $x(modalFadeShow).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    /**
     * Нажимает кнопку эмоджи. Они могут быть: happy, inspired, ok, neutral, sad, tired, upset, angry
     *
     * @param emoji вид эмоджи
     */
    @Step("Нажимает кнопку эмоджи")
    public void clickEmoji(Emo emoji) {
        $x(String.format(blankEmojiXPath, emoji)).click();
    }
   public enum Emo {
       happy, inspired, ok, neutral, sad, tired, upset, angry
    }
}
