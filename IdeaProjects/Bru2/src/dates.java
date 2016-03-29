import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Mar 10, 2008
 * Time: 12:04:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class dates {
    public static String getStartDateOfMonth(int year, int month){
            Calendar calendar = new GregorianCalendar(year, month-1, 1);
            return (year + "-" + prependZeros(month, 2) + "-" + prependZeros(calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 2));
        }
        public static String getEndDateOfMonth(int year, int month){
            Calendar calendar = new GregorianCalendar(year, month-1, 1);
            return (year + "-" + prependZeros(month, 2) + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }

        private static String prependZeros(int value, int filedLength) {
            String fieldValue = String.valueOf(value);
            while(fieldValue.length() < filedLength) {
                fieldValue = "0" + fieldValue;
            }
            return fieldValue;
        }

    public static void main(String a[]){
        System.out.println("Start Date of Month: "+getStartDateOfMonth(2008,02));
        System.out.println("End Date of Month: "+getEndDateOfMonth(2008,02));

    }
}
