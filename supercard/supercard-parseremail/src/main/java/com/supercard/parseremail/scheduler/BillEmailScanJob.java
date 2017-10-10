package com.supercard.parseremail.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercard.parseremail.ParseEmailEngine;
import com.supercard.parseremail.cardparse.*;
import com.supercard.repository.BillRepository;
import com.supercard.tour.BillEntity;
import org.apache.commons.mail.util.MimeMessageParser;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SubjectTerm;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public class BillEmailScanJob implements Job {



    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            ParseEmailEngine.run();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}