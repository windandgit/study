package com.test.taskScheduling;

import java.util.Timer;
import java.util.TimerTask;

public class TaskTimerTest extends TimerTask {

    private String taskName = "";

    public TaskTimerTest(String taskName){
        super();
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println("execute" + taskName);
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        long delay1 = 1 * 1000;
        long period1 = 1000;
        //从现在开始每隔1秒钟执行一次job1
        timer.schedule(new TaskTimerTest("job1"),delay1,period1);

        long delay2 = 2 * 1000;
        long period2 = 2000;
        //从现在开始每隔2秒钟执行一次job2
        timer.schedule(new TaskTimerTest("job2"),delay2,period2);

    }
}
