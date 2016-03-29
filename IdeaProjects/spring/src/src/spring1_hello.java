package src;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Mar 14, 2011
 * Time: 3:42:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class spring1_hello {

    public static void main(String[] args) {
       try{

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
        BeanFactory factory = context;
           
        //XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("spring.xml"));

        spring1 myBean = (spring1) factory.getBean("SpringHelloBean");
        myBean.sayHello();

       }catch(Exception ioe){
           System.out.println("printing the error");
           ioe.printStackTrace();
       }
    }
}
