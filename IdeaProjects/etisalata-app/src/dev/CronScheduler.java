package dev;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Dec 11, 2011
 * Time: 7:18:21 PM
 * To change this template use File | Settings | File Templates.
 */

public class CronScheduler {
    // Debuglogger for trace log
    //Translogger for trans log
    //private static Logger DebugLogger = Logger.getLogger(CronScheduler.class);
    //private static Logger TransLogger = Logger.getLogger("debug");
    private static Logger logger = Logger.getLogger(CronScheduler.class);

        public void run() throws Exception {
            //Logger log = LoggerFactory.getLogger(CronTriggerExample.class);

            logger.info("------- Initializing -------------------");

            // First we must get a reference to a scheduler
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();

            logger.info("------- Initialization Complete --------");

            logger.info("------- Scheduling Jobs ----------------");

            // jobs can be scheduled before sched.start() has been called

            logger.info("PropReader.cronTimer");

            // job 1 will run every 20 seconds
            JobDetail job = new JobDetail("job1", "group1", MainServer.class);
            CronTrigger trigger = new CronTrigger("trigger1", "group1", "job1",
                    "group1", PropReader.cronTimer);
            sched.addJob(job, true);
            Date ft = sched.scheduleJob(trigger);
            logger.info(job.getFullName() + " has been scheduled to run at: " + ft
                    + " and repeat based on expression: "
                    + trigger.getCronExpression());

            // job 2 will run every other minute (at 15 seconds past the minute)


            logger.info("------- Starting Scheduler ----------------");

            // All of the jobs have been added to the scheduler, but none of the
            // jobs
            // will run until the scheduler has been started
            sched.start();

            logger.info("------- Started Scheduler -----------------");
            logger.info("Started Scheduler");
            //logger.info("------- Waiting five minutes... ------------");
            /*try {
                //wait five minutes to show jobs
                Thread.sleep(3L * 1000L);
                //executing...
            } catch (Exception e) {
            }*/
            //logger.info("------- Shutting Down ---------------------");
            //sched.shutdown(true);
            //logger.info("------- Shutdown Complete -----------------");
            SchedulerMetaData metaData = sched.getMetaData();
            logger.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

            //while(true){}
        }

        public static void main(String[] args) throws Exception {

            CronScheduler example = new CronScheduler();
            example.run();
        }

    }

