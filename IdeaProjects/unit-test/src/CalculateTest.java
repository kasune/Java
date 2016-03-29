/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 4, 2011
 * Time: 11:50:47 PM
 * To change this template use File | Settings | File Templates.
 */
import junit.framework.TestCase;

 public class CalculateTest extends TestCase {
   Calculate cal=new Calculate();
   Manipulate man = new Manipulate();

    public CalculateTest(String name) {
    super(name);
    }

    public void testSum() {
    assertEquals(2,cal.sum(1,1));
   }
     public void testMultiply(){
         assertEquals(6,cal.multi(2,3));
     }

     public void testgender(){
         assertEquals(0,man.gender("ale"));
     }
 }
