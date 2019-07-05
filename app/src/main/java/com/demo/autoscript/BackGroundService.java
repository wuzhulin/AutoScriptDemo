package com.demo.autoscript;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.demo.commandpattern.ClickExcuter;
import com.demo.commandpattern.CommandHandler;
import com.demo.commandpattern.RestExcuter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class BackGroundService extends Service {

    public static final String TAG = "BackGroundService";
    ThreadAdbshell thshell;
    int distance;
    private String commandStr;
    private ArrayList<String> data;

    //创建服务时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        thshell = new ThreadAdbshell(90, 185);
        distance = 1000;
    }

    //服务执行的操作
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         /*Log.d(TAG, "onStartCommand");
         distance=intent.getIntExtra("distance", 1000);
         thshell.setDistance(distance);
         thshell.setX(intent.getIntExtra("x", 90));
         thshell.setY(intent.getIntExtra("y", 185));
         if(!thshell.isAlive())
         {
        	 Log.d("线程是否正在执行", thshell.isAlive()+"");
        	 thshell.start();
         }*/


        if (!thshell.isAlive()) {
            Log.d("线程是否正在执行", thshell.isAlive() + "");
            thshell.run();
        }

        CommandHandler commandHandler = new CommandHandler();

        data = (ArrayList<String>) intent.getSerializableExtra("command");

        for (String commandStr:data) {
            if (commandStr.startsWith("t")) {
                commandHandler.addRequset(new RestExcuter(thshell, 1000));
            }
            if (commandStr.startsWith("x")) {
                int x = Integer.parseInt(commandStr.substring(2, commandStr.indexOf("*")));
                int y = Integer.parseInt(commandStr.substring(commandStr.indexOf("*")+1+2));
                commandHandler.addRequset(new ClickExcuter(thshell, x,y));
            }

        }




        /**

        //打开南威
        commandHandler.addRequset(new RestExcuter(thshell, 1000));
        commandHandler.addRequset(new ClickExcuter(thshell, 166, 1036));
        //登录南威
        commandHandler.addRequset(new RestExcuter(thshell, 2000));
        commandHandler.addRequset(new ClickExcuter(thshell, 577, 698));
        //
        commandHandler.addRequset(new RestExcuter(thshell, 3000));
        commandHandler.addRequset(new ClickExcuter(thshell, 300, 200));

        commandHandler.addRequset(new RestExcuter(thshell, 4000));
        commandHandler.addRequset(new ClickExcuter(thshell, 400, 200));

        commandHandler.addRequset(new RestExcuter(thshell, 5000));
        commandHandler.addRequset(new ClickExcuter(thshell, 500, 200));

        commandHandler.addRequset(new RestExcuter(thshell, 6000));
        commandHandler.addRequset(new ClickExcuter(thshell, 600, 200));

        commandHandler.addRequset(new RestExcuter(thshell, 7000));
        commandHandler.addRequset(new ClickExcuter(thshell, 700, 200));

        commandHandler.addRequset(new RestExcuter(thshell, 8000));
        commandHandler.addRequset(new ClickExcuter(thshell, 800, 200));

        commandHandler.addRequset(new RestExcuter(thshell, 1000));
        commandHandler.addRequset(new ClickExcuter(thshell, 900, 200));

        commandHandler.addRequset(new RestExcuter(thshell, 2000));
        commandHandler.addRequset(new ClickExcuter(thshell, 1000, 200));

        commandHandler.addRequset(new RestExcuter(thshell, 3000));
        commandHandler.addRequset(new ClickExcuter(thshell, 11000, 200));

         */

        commandHandler.handleRequest();

        return super.onStartCommand(intent, flags, startId);
    }

    //销毁服务时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        data.clear();
        Log.d(TAG, "onDestroy");
    }

    //bindService()启动Service时才会调用onBind()方法
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
