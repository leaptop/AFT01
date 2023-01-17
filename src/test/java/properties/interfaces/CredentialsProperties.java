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
@Config.Sources({"file:src/test/resources/properties/credentials.properties",
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
    /**
     * Логин из credentials.properties
     */
    @Key("name")
    String name();

    /**
     * Логин из переменной среды ОС
     */
    @Key("USERNAME2")
    String username();

    /**
     * Пароль из credentials.properties
     */
    @Key("password")
    String password();

    /**
     * URL из credentials.properties
     */
    @Key("url")
    String url();

    /**
     * @return неверное имя пользователя из переменной среды, заданное через edit configurations - environment variables
     */
    @Key("INCORRECTUSERNAME")
    String incorrectUserName();

    /**
     * @return неверный пароль из переменной среды, заданный через edit configurations - environment variables
     */
    @Key("INCORRECTPASSWORD")
    String incorrectPassword();

    /**
     * @return логин из переменной среды, заданный через edit configurations - environment variables
     */
    @Key("AUTOUSER")
    String autoUser();

    /**
     * @return пароль из файла пропертей
     */
    @Key("autoUserPassword")
    String autoUserPassword();


}
