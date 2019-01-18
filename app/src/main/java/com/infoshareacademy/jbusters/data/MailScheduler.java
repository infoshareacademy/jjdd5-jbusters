package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class MailScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailScheduler.class);
    private static String REPORT_PATH = Paths.get(System.getProperty("jboss.server.temp.dir"), "report.pdf").toString();
    private static final Properties scheduleProperties = new Properties();
    private static final MailHandler mailHandler = new MailHandler();
    private static final ReportGenerator reportGenerator = new ReportGenerator();
    private static final String DAY_KEY = "day";
    private static final String DAY_DEFAULT_VALUE = "2";
    private static final String HOUR_KEY = "hour";
    private static final String HOUR_DEFAULT_VALUE = "08";
    private static final String MINUTE_KEY = "minute";
    private static final String MINUTE_DEFAULT_VALUE = "30";
    private static final String DATE_FORMAT = "EEEE',' HH:mm";
    private static final int PERIOD = 604800;   // seconds in week
    private static final int SECOND = 00;
    private static final int MILLISECOND = 00;
    private static ScheduledExecutorService scheduledExecutorService;
    private static ScheduledFuture<?> scheduledFuture;

    private static Date getDateStamp() {

        if (!Files.exists(StaticFields.getSchedulerPropertiesFile())) {
            LOGGER.warn("Scheduler file not found in path: {}", StaticFields.getSchedulerPropertiesFile().toString());
            try {
                LOGGER.info("Created new scheduler file in path: {}", StaticFields.getSchedulerPropertiesFile().toString());
                Files.createFile(StaticFields.getSchedulerPropertiesFile());
            } catch (IOException e) {
                LOGGER.error("New scheduler file could not be created in path: {}", StaticFields.getSchedulerPropertiesFile().toString());
            }
        }

        String url = StaticFields.getSchedulerPropertiesFile().toString();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(url);
        } catch (FileNotFoundException | NullPointerException e) {
            LOGGER.error("Scheduler file not found in path: {}", StaticFields.getSchedulerPropertiesFile().toString());
        }

        try {
            scheduleProperties.load(fis);
            fis.close();
        } catch (IOException | NullPointerException e) {
            LOGGER.error("Missing properties file in path: {}", StaticFields.getSchedulerPropertiesFile().toString());
            LOGGER.info("Date stamp default values will be set.");
        }

        String dayString = scheduleProperties.getProperty(DAY_KEY, DAY_DEFAULT_VALUE);
        String hourString = scheduleProperties.getProperty(HOUR_KEY, HOUR_DEFAULT_VALUE);
        String minuteString = scheduleProperties.getProperty(MINUTE_KEY, MINUTE_DEFAULT_VALUE);

        Date result = dateCompose(dayString, hourString, minuteString);

        return result;
    }

    public String getSetDateString() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        return dateFormat.format(getDateStamp());
    }

    private static long getDelay() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        LOGGER.info("Runtime date stamp set to: {}", dateFormat.format(getDateStamp()));

        Calendar now = Calendar.getInstance();
        now.get(Calendar.DAY_OF_WEEK);
        now.get(Calendar.HOUR_OF_DAY);
        now.get(Calendar.MINUTE);
        now.get(Calendar.SECOND);
        now.get(Calendar.MILLISECOND);
        Date runCompose = now.getTime();

        LOGGER.info("Current date stamp: {}", dateFormat.format(runCompose));

        long diffMilliseconds = getDateStamp().getTime() - runCompose.getTime();
        long diffSeconds = TimeUnit.MILLISECONDS.toSeconds(diffMilliseconds);
        long weekOfSeconds = TimeUnit.DAYS.toSeconds(7);
        long diffSecondsReverted = weekOfSeconds + diffSeconds;

        if (diffMilliseconds >= 0) {
            LOGGER.info("Delay of {} seconds set.", diffSeconds);
            return diffSeconds;
        } else {
            LOGGER.info("Delay of {} seconds set.", diffSecondsReverted);
            return diffSecondsReverted;
        }
    }

    private static void scheduler(String login, String[] recipients) throws MessagingException {

        stopScheduler();

        LOGGER.info("Proceeding with schedule task initiation.");

        Runnable task = () -> {
            try {
                reportGenerator.generateReport();
                LOGGER.info("Report generated under following path: {}", REPORT_PATH);
                mailHandler.sendMail(login, recipients);
            } catch (IOException | MessagingException e) {
                LOGGER.warn("Exception: {}", e);
            }
        };

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(task, getDelay(), PERIOD, TimeUnit.SECONDS);
    }

    public static void stopScheduler() {

        LOGGER.info("Checking if some schedule task is running.");

        if (scheduledFuture != null) {
            LOGGER.info("Scheduler instance exists.");
            if (!scheduledFuture.isCancelled()) {
                scheduledFuture.cancel(false);
                scheduledExecutorService.shutdown();
                LOGGER.warn("The following schedule task was found and terminated: {}", scheduledFuture);
            } else {
                LOGGER.info("No schedule tasks found.");
            }
        } else {
            LOGGER.info("Scheduler instance does not exist.");
        }
    }

    public boolean isSchedulerOn() {

        LOGGER.info("Checking scheduler status.");

        if (scheduledFuture != null) {
            LOGGER.info("Scheduler instance exists.");
            if (!scheduledFuture.isCancelled()){
                LOGGER.info("Scheduler is ON.");
                return true;
            } else {
                LOGGER.info("Scheduler is OFF.");
                return false;
            }
        } else {
            LOGGER.info("Scheduler instance does not exist.");
            return false;
        }
    }

    public void saveScheduleAndInit(String dayString, String hourString, String minuteString, String login, String[] recipients) throws IOException, MessagingException {

        if (!Files.exists(StaticFields.getSchedulerPropertiesFile())) {
            Files.createFile(StaticFields.getSchedulerPropertiesFile());
        }

        String url = StaticFields.getSchedulerPropertiesFile().toString();
        FileOutputStream fos = new FileOutputStream(url);

        scheduleProperties.setProperty(DAY_KEY, dayString);
        scheduleProperties.setProperty(HOUR_KEY, hourString);
        scheduleProperties.setProperty(MINUTE_KEY, minuteString);
        scheduleProperties.store(fos, "Schedule Properties");

        fos.close();

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        scheduler(login, recipients);

        LOGGER.info("New schedule [ {} ] stored and initiated.", dateFormat.format(dateCompose(dayString, hourString, minuteString)));
    }

    private static Date dateCompose(String dayString, String hourString, String minuteString) {

        int day = Integer.parseInt(dayString);
        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);

        Calendar schedule = Calendar.getInstance();
        schedule.set(Calendar.DAY_OF_WEEK, day);
        schedule.set(Calendar.HOUR_OF_DAY, hour);
        schedule.set(Calendar.MINUTE, minute);
        schedule.set(Calendar.SECOND, SECOND);
        schedule.set(Calendar.MILLISECOND, MILLISECOND);
        Date scheduleCompose = schedule.getTime();

        return scheduleCompose;
    }
}
