package com.infoshareacademy.jbusters.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.mail.MessagingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
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
    private static final Properties scheduleProperties = new Properties();
    private static final MailHandler mailHandler = new MailHandler();
    private static final String DAY_KEY = "day";
    private static final String HOUR_KEY = "hour";
    private static final String MINUTE_KEY = "minute";
    private static final int PERIOD = 604800;
    private static final int DAY = 4;       //from properties
    private static final int HOUR = 14;     //from properties
    private static final int MINUTE = 58;   //from properties
    private static final int SECOND = 00;
    private static final int MILLISECOND = 00;
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture<?> SCHEDULED_TASK;

    public long getDelay() {

        Calendar runTime = Calendar.getInstance();
        runTime.set(Calendar.DAY_OF_WEEK, DAY);
        runTime.set(Calendar.HOUR_OF_DAY, HOUR);
        runTime.set(Calendar.MINUTE, MINUTE);
        runTime.set(Calendar.SECOND, SECOND);
        runTime.set(Calendar.MILLISECOND, MILLISECOND);
        Date runTimeCompose = runTime.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE 'at' HH:mm");

        LOGGER.info("Runtime date stamp set to: {}", dateFormat.format(runTimeCompose));

        Calendar now = Calendar.getInstance();
        now.get(Calendar.DAY_OF_WEEK);
        now.get(Calendar.HOUR_OF_DAY);
        now.get(Calendar.MINUTE);
        now.get(Calendar.SECOND);
        now.get(Calendar.MILLISECOND);
        Date runCompose = now.getTime();

        LOGGER.info("Current date stamp: {}", dateFormat.format(runCompose));

        long diffMilliseconds = runTimeCompose.getTime() - runCompose.getTime();
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

    private void start() {

        //scheduler(getDelay(), PERIOD);
    }

    private void update(int period) {

        SCHEDULED_TASK.cancel(false);
        SCHEDULER.shutdown();
        //scheduler(getDelay(), PERIOD);
    }

    private void scheduler(String login, String pass, String[] recipients) {

        Runnable task = () -> {
            try {
                mailHandler.sendMail(login, pass, recipients);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        };

        SCHEDULED_TASK = SCHEDULER.scheduleAtFixedRate(task, getDelay(), PERIOD, TimeUnit.SECONDS);
    }

    public void saveScheduleAndInit(String dayString, String hourString, String minuteString, String login, String pass, String[] recipients) throws IOException, MessagingException {

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

        int day = Integer.parseInt(dayString);
        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);

        Calendar schedule = Calendar.getInstance();
        schedule.set(Calendar.DAY_OF_WEEK, day);
        schedule.set(Calendar.HOUR_OF_DAY, hour);
        schedule.set(Calendar.MINUTE, minute);
        Date scheduleCompose = schedule.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE 'at' HH:mm");

        scheduler(login, pass, recipients);

        LOGGER.info("New schedule [ {} ] stored and initiated.", dateFormat.format(scheduleCompose));
    }
}
