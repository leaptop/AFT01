package properties;

import org.aeonbits.owner.ConfigFactory;
import properties.interfaces.CredentialsProperties;

/**
 * Класс, содержащий ссылку для доступа к файлу .property.
 *
 * @author Степан Алексеев
 */
public class Properties {
    /**
     * Создаём объект MainProperties на основе интерфейса MainProperties для доступа к переменным из файла .property.
     */
    public static CredentialsProperties credentialsProperties = ConfigFactory.create(CredentialsProperties.class);
}
