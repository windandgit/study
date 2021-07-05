package com.test.taskScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduledExecutorTest implements Runnable{
    private String taskName = "";

    public TaskScheduledExecutorTest(String taskName){
        super();
        this.taskName = taskName;
    }
    //创建线程的执行任务
    @Override
    public void run() {
        System.out.println("execute" + taskName);
    }

    public static void main(String[] args) {
        long initDelay1 = 1;
        long period1 =1;
        //创建线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        //从现在开始1秒钟之后，每隔1秒钟执行一次
        scheduledExecutorService.scheduleAtFixedRate(new TaskScheduledExecutorTest("job1"),initDelay1,period1, TimeUnit.SECONDS);

        long initDelay2 = 1;
        long period2 = 2;

        //从现在开始1秒钟之后每隔2秒钟执行一次
        scheduledExecutorService.scheduleAtFixedRate(new TaskScheduledExecutorTest("job2"),initDelay2,period2,TimeUnit.SECONDS);
    }
}
