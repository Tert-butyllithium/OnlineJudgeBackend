package edu.sustech.oj_server.moss;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
@Order(value = 1)
public class QuartzManager implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SchedulerFactory factory = new StdSchedulerFactory();

        Date runTime = DateBuilder.evenSecondDateAfterNow();

        JobDetail jobDetail = JobBuilder.newJob(EggTimer.class)
                .withIdentity("timer","timer_group")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("timer_trigger","timer_group")
                .startAt(new Date())
                .withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression("0 0 16 * * ?"))
                ).build();
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}