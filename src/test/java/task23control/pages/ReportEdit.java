package task23control.pages;

import task22.pages.SuccessfulLoginPage;

import static com.codeborne.selenide.Selenide.$x;

/**
 * @author Алексеев Степан
 * @date 30.01.2023
 */
public class ReportEdit extends SuccessfulLoginPage {
    private String blankEmojiXPath = "//div[@class='emoji-%s ']";

    /**
     * Нажимает кнопку эмоджи. Они могут быть: happy, inspired, ok, neutral, sad, tired, upset, angry
     * @param emoji вид эмоджи
     */
    public void clickEmoji(String emoji){
        $x(String.format(blankEmojiXPath, emoji)).click();
    }
}
