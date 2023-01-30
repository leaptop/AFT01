package task23control.pages;

import com.codeborne.selenide.Condition;
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
    private String modalFadeShow = "//div[@class='modal fade show']//h5[contains(text(),'Вы хотите залогировать " +
            "больше или меньше 8 часов, которые по графику запланированы у вас на сегодня')]";
    /**
     * Кнопка "Отмена" модального окна, сообщающего о нестандартном числе залогированных часов
     */
    private String cancelButtonOfModalWindow = "//div[@class='modal fade show']//button[contains(text(), 'Отмена')]";

    /**
     * Кликает кнопку "Отмена" модального окна, сообщающего о нестандартном числе залогированных часов
     */
    public void clickCancelModal8hours() {
        $x(cancelButtonOfModalWindow).click();
    }

    /**
     * Проверка того, что модальное окно о нестандартно залогированном времени появилось
     */
    public void checkIfModal8hoursWindowAppeared() {
        $x(modalFadeShow).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    /**
     * Нажимает кнопку эмоджи. Они могут быть: happy, inspired, ok, neutral, sad, tired, upset, angry
     *
     * @param emoji вид эмоджи
     */
    public void clickEmoji(String emoji) {
        $x(String.format(blankEmojiXPath, emoji)).click();
    }
}
