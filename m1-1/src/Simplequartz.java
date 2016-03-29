import java.util.Date;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class Simplequartz {

    private static Logger logger = Logger.getLogger(Simplequartz.class);

    public Simplequartz() {
    }

    public void execute() throws Exception {


            // First we must get a reference to a scheduler
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();


            // computer a time that is on the next round minute
            Date runTime = TriggerUtils.getEvenMinuteDate(new Date());
            logger.debug(runTime);
            // define the job and tie it to our HelloJob class
            JobDetail job = new JobDetail("job1", "group1", HelloJob.class);

            // Trigger the job to run on the next round minute
            SimpleTrigger trigger =
                new SimpleTrigger("trigger1", "group1", runTime);

            // Tell quartz to schedule the job using our trigger
            sched.scheduleJob(job, trigger);

            // Start up the scheduler (nothing can actually run until the
            // scheduler has been started)
            sched.start();

            // wait long enough so that the scheduler as an opportunity to
            // run the job!
            try {
                // wait 90 seconds to show jobs
                Thread.sleep(90L * 1000L);
                // executing...
            } catch (Exception e) {
                logger.debug(e.toString());
            }

            // shut down the scheduler
            sched.shutdown(true);
            }

    public static void main(String args[]){
        Simplequartz sq=new Simplequartz();
        try {
        sq.log4j();
        sq.execute();
        }catch(Exception e){
            logger.debug(e.toString());
        }
    }

    public void log4j(){
        PropertyConfigurator.configure("D:\\beyondm\\study\\java\\m1-1\\conf\\log4j.properties");
        logger.debug("test debug");
        logger.info("Test Log");
    }
}