package com.supercard.parseremail;

import com.supercard.parseremail.scheduler.BillEmailScanJob;
import com.supercard.repository.BillRepository;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercard.parseremail.cardparse.*;
import com.supercard.tour.BillEntity;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.util.MimeMessageParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.*;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws Exception {


//        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//        scheduler.start();
//        scheduler.scheduleJob(
//                JobBuilder.newJob(BillEmailScanJob.class).withIdentity("dummyJobName", "group1").build(),
//                TriggerBuilder
//                        .newTrigger()
//                        .withIdentity("dummyTriggerName", "group1")
//                        .startNow()
//                        .withSchedule(
//                                CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//                        .build());

        ParseEmailEngine.run();

    }
}


