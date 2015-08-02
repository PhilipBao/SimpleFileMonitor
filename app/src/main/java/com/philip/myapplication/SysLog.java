package com.philip.myapplication;


import android.content.Context;
import android.database.Observable;

import com.philip.myapplication.UI.LogListItem;

import java.util.ArrayList;

public class SysLog extends Observable<SysLog.ILogObserver> {
    private static final String TAG = SysLog.class.getSimpleName();

    private ArrayList <LogListItem> mLog = new ArrayList <LogListItem> ();


    private SysLog() {}

    public static synchronized SysLog getInstance() {return LoggingHolder.getSysLog();}

    private static class LoggingHolder {
        private static SysLog instance = null;

        public static SysLog getSysLog() {
            if(instance == null) {
                instance = new SysLog();
            }
            return instance;
        }
    }

    public synchronized void addLog(LogListItem item) {
        if (item != null) {
            mLog.add(item);
            logChanged(getLog());
        }
    }

    public void addLog(LogListItem [] items) {
        if(items != null && items.length > 0){
            for (LogListItem s_item: items) {
                addLog(s_item);
            }
        }
    }

    public synchronized ArrayList <LogListItem> getLog() {
        return mLog;
    }

    public Boolean clearLog() {
        mLog.clear();
        logChanged(getLog());
        return true;
    }

    private void logChanged(ArrayList <LogListItem> log) {
        for(SysLog.ILogObserver o : mObservers) {
            o.onLogChanged(log);
        }
    }

    public interface ILogObserver {
        void onLogChanged(ArrayList <LogListItem> Log);
    }
}
