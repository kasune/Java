package src;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 27, 2011
 * Time: 1:19:14 PM
 * To change this template use File | Settings | File Templates.
 */
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class Spring_test {
    public static void main(String[] args) {

    XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("config-test.xml"));
    Inject demo = (Inject) beanFactory.getBean("mybean");
    System.out.println(demo);

    }
}