import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.Date;
import java.io.IOException;
import java.io.File;

public class RobotTest {

    public static void main(String[] args) {
        try {
            /*Robot robot = new Robot();
            // this will open new notepad by pressing windows + r and then type notepad
            //
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_R);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_R);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_N);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_O);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_T);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_E);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_P);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_A);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_D);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_ENTER);*/

            Properties prop = System.getProperties();
            System.out.println(prop.getProperty("os.name"));
            System.out.println(prop.getProperty("os.arch"));
            System.out.println(prop.getProperty("user.name"));
            String pdir = "C:\\Documents and Settings\\kasun\\Desktop\\";
            boolean success = (new File(pdir.concat("20091225"))).mkdir();
            Date d = new Date();
            

            if (success){
                System.out.println("folder created successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}