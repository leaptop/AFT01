package properties.interfaces;
/**
 * Данная библиотека использует следующий подход: создание интерфейса, ассоциированного с проперти файлом.
 *
 * @author Алексеев Степан
 * @date 22.12.2022
 */

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
/** В случае @LoadPolicy(LoadType.FIRST) будет так:
 * Сначала OWNER будет пытаться загрузить проперти из "file:src/main/resources/properties/main.properties".
 * Если предыдущая попытка потерпит неудачу, то он попробует загрузить их из "system:properties" (Java System
 * AeonBitsProperties). Их можно загрузить как описано здесь:
 * https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
 * Дальше то же самое с переменными среды.
 * Только один файл пропертей будет использован (первый найденный). Остальные будут проигнорированы.
 * Если ничего не будет найдено, будет использованы значения помеченные  @DefaultValue.
 *
 * В случае @Config.LoadPolicy(Config.LoadType.MERGE) будет так:
 * Будет переопределение. Всё, что вытащится из каждого полседующего файла будет принято.
 * Первые значения будут использованы (они переопределят последующие).
 * http://owner.aeonbits.org/docs/loading-strategies/
 *
 * Можно просто использовать дефолтные значения. Последующую же конфигурацию через проперти файл оставить следующему
 * разработчику/пользователю.
 */
@Config.Sources({
        "file:src/test/resources/properties/credentials.properties",
        "system:properties",
        "system:env"
})
/**
 * Интерфейс для доступа к файлу .property.
 * Имя переменной должно быть таким же, как в файле .property. Если же оно отличается, то нужно
 * использовать аанотацию @Key.
 * На всякий случай можно использовать @DefaultValue("5") для установления значения по умолчанию.
 * @author Алексеев Степан
 */
public interface CredentialsProperties extends Config {
    String name();

    String password();

    String autoUserName();

    String autoUserPassword();

    String incorrectUserName();

    String incorrectPassword();

    String url();

    String urlLogin();

    String urlReportEdit();

    String urlLoginCheck();

    String urlCalendar();
}