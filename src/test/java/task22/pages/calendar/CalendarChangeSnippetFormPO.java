package task22.pages.calendar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;

import java.io.File;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * @author Алексеев Степан
 * @date 26.01.2023
 */
public class CalendarChangeSnippetFormPO {
    private String changeApprovingPeople = "//label[contains(text(), 'C кем согласовано')]/following-sibling::span";
    private String approvingNamesA = "//li[contains(@id,'select2-field-users-confirmation')]";
    private String approvingNamesB = "//li[@class='select2-results__option']";//а иногда имена так представлены в
    // DOM, однако такие элементы были not interactable
    private String inputForImagesXPath = "//input[@type='file' and not (contains(@accept,'image'))]";
    private String saveScheduleButton = "//div[@id='popup-change-schedule']//button[contains(text(), 'Сохранить')]";
    private String progressBarForFileSuccessfullyUploaded = "//div[text()='Файл успешно загружен']";

    public CalendarChangeSnippetFormPO waitForProgressBarForFileSuccessfullyUploaded() {
        $x(progressBarForFileSuccessfullyUploaded).shouldBe(Condition.visible);
        return this;
    }

    public CalendarChangeSnippetFormPO saveScheduleButtonClick() {
        $x(saveScheduleButton).click();
        return this;
    }

    public CalendarChangeSnippetFormPO uploadFileViaSelenium(String path) {
        By fileInput = By.xpath(inputForImagesXPath);
        WebDriverRunner.getWebDriver().findElement(fileInput).sendKeys(path);
        return this;
    }

    public CalendarChangeSnippetFormPO uploadFileViaSelenide(String path) {
        $x(inputForImagesXPath).uploadFile(new File(path));
        waitForProgressBarForFileSuccessfullyUploaded();
        return this;
    }

    /**
     * Произвольно выбирает человека из списка "Выберите согласующих лиц". Иногда коллекция не заполняется, т.к.
     * элементы представлены не в том формате, поэтому можно в цикле попробовать понажимать поле "Выберите
     * согласующих лиц", тогда DOM может поменяться. Если этого не произойдёт, можно попробовать работать с теми
     * элементами, которые есть. Счётчик count нужен, чтобы не уйти в бесконечный цикл.
     *
     * @return
     */
    public CalendarChangeSnippetFormPO chooseRandomApprovingPerson() {
        ElementsCollection ec = $$x(approvingNamesA);
//        int count = 0;
//        while (ec.size() < 1 && count++ < 30) {//Раскомментировать, если не получится заполнить коллекцию
//            clickChangeApprovingPeople();
//            clickChangeApprovingPeople();
//            ec = $$x(approvingNamesA);
//            System.out.println("clicked two times");
//        }
        if (ec.size() < 1) {
            ec = $$x(approvingNamesB);
        }
        Random random = new Random();
        int r = random.nextInt(ec.size());
        SelenideElement se = ec.get(r);
        se.hover().click();
        return this;
    }

    /**
     * Нажимает на поле "Выберите согласующих лиц"
     *
     * @return
     */
    public CalendarChangeSnippetFormPO clickChangeApprovingPeople() {
        $x(changeApprovingPeople).click();
        return this;
    }

}
