package com.test.countdownLatchTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//百米赛跑，四名运动员选手到达场地等待裁判口令，
// 裁判一声口令，选手听到后同时起跑，当所有选手到达重点之后裁判进行汇总排名
public class CountdownLatchTest {
    public static void main(String[] args) {
        //获取一个线程池
        ExecutorService service = Executors.newCachedThreadPool();
        //定义两个同步计数器
        final CountDownLatch cpCount = new CountDownLatch(1);
        final CountDownLatch ydyCount = new CountDownLatch(4);

        //循环创建并执行4个任务
        for (int i = 0; i < 4 ; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
                        //把当前线程加入到cpCount的等待线程中
                        cpCount.await();
                        System.out.println("选手" + Thread.currentThread().getName() + "已经接收到裁判的口令");
                        Thread.sleep((long) Math.random() * 10000);
                        System.out.println("选手" + Thread.currentThread().getName() + "已经到达重点");
                        //这个运动员已经跑到重点，运动员同步计数器要减一
                        ydyCount.countDown();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            //执行上面创建完成的runnable
            service.execute(runnable);
        }

        //裁判数据处理
        try {
            Thread.sleep((long)Math.random() * 10000);
            System.out.println("裁判" + Thread.currentThread().getName() + "即将发布口令");
            //发布口令
            cpCount.countDown();
            System.out.println("裁判" + Thread.currentThread().getName() + "已经发布口令，正在等待全部选手到达终点");
            //将裁判线程加入到运动员的同步计数器中
            ydyCount.await();
            System.out.println("所有选手都到达终点");
            System.out.println("裁判" + Thread.currentThread().getName() + "汇总成绩排名");
        }catch (Exception e){
            e.printStackTrace();
        }
        service.shutdown();
    }
}
