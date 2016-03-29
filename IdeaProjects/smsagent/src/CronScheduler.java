/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 24, 2010
 * Time: 9:35:12 PM
 * To change this template use File | Settings | File Templates.
 */


import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.log4j.Logger;

public class CronScheduler {
    // Debuglogger for trace log
    //Translogger for trans log
    private static Logger DebugLogger = Logger.getLogger(CronScheduler.class);
    private static Logger TransLogger = Logger.getLogger("Trans");
    
        public void run() throws Exception {
            //Logger log = LoggerFactory.getLogger(CronTriggerExample.class);

            DebugLogger.debug("------- Initializing -------------------");

            // First we must get a reference to a scheduler
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();

            DebugLogger.debug("------- Initialization Complete --------");

            DebugLogger.debug("------- Scheduling Jobs ----------------");

            // jobs can be scheduled before sched.start() has been called

            // job 1 will run every 20 seconds
            JobDetail job = new JobDetail("job1", "group1", Manipulator.class);
            CronTrigger trigger = new CronTrigger("trigger1", "group1", "job1",
                    "group1", LogLoader.cron_timer);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);
            DebugLogger.debug(job.getFullName() + " has been scheduled to run at: " + ft
                    + " and repeat based on expression: "
                    + trigger.getCronExpression());

            // job 2 will run every other minute (at 15 seconds past the minute)


            DebugLogger.debug("------- Starting Scheduler ----------------");

            // All of the jobs have been added to the scheduler, but none of the
            // jobs
            // will run until the scheduler has been started
            sched.start();

            DebugLogger.debug("------- Started Scheduler -----------------");

            //DebugLogger.debug("------- Waiting five minutes... ------------");
            /*try {
                //wait five minutes to show jobs
                Thread.sleep(3L * 1000L);
                //executing...
            } catch (Exception e) {
            }*/
            //DebugLogger.debug("------- Shutting Down ---------------------");
            //sched.shutdown(true);
            //DebugLogger.debug("------- Shutdown Complete -----------------");
            SchedulerMetaData metaData = sched.getMetaData();
            DebugLogger.debug("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

            //while(true){}
        }

        public static void main(String[] args) throws Exception {

            CronScheduler example = new CronScheduler();
            example.run();
        }

    }
