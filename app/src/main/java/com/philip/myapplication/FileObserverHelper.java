package com.philip.myapplication;

import android.os.FileObserver;
import android.util.Log;

import com.philip.myapplication.UI.LogListItem;

import java.io.File;


public class FileObserverHelper extends FileObserver {

    private static final String TAG = FileObserverHelper.class.getSimpleName();
    private static final String FILE_SEPERATER = File.separator;

    private int mMask;
    private final String PATH;
    private String mEventHappen = null;
    private static String currentAction = "";

    public FileObserverHelper(String path) {
        this(path, ALL_EVENTS);
    }

    public FileObserverHelper(String path, int mask) {
        super(path, mask);
        mMask = mask;
        PATH = path;
    }

    @Override
    public void onEvent(int event, final String specPath) {
        switch (event) {
            case ACCESS:
                mEventHappen = "ACCESS";
                break;
            case MODIFY:
                mEventHappen = "MODIFY";
                break;
            case ATTRIB:
                mEventHappen = "ATTRIB";
                break;
            case CLOSE_WRITE:
                mEventHappen = "CLOSE_WRITE";
                break;
            case CLOSE_NOWRITE:
                mEventHappen = "CLOSE_NOWRITE";
                break;
            case OPEN:
                mEventHappen = "OPEN";
                break;
            case MOVED_FROM:
                mEventHappen = "MOVED_FROM";
                break;
            case MOVED_TO:
                mEventHappen = "MOVED_TO";
                break;
            case CREATE:
                mEventHappen = "CREATE";
                break;
            case DELETE:
                mEventHappen = "DELETE";
                break;
            case DELETE_SELF:
                mEventHappen = "DELETE:SELF";
                break;
            case MOVE_SELF:
                mEventHappen = "MOVE:SELF";
                break;
            default:
                mEventHappen = "Unknown";
                break;
        }

        if(specPath != null && !"unknown".equalsIgnoreCase(mEventHappen)) {
            //Simple filter for duplicate event
            if(!currentAction.equalsIgnoreCase(mEventHappen)) {
                SysLog.getInstance().addLog(new LogListItem(PATH + FILE_SEPERATER + specPath, "Attempt to " + mEventHappen));
                currentAction = mEventHappen;
            }
            Log.d(TAG, "File path: " + specPath + " , by Event: " + mEventHappen);
        } else {
            Log.d(TAG, "File path: null , by Event: " + mEventHappen);
        }
    }
    public String getPath () {
        return PATH;
    }

}
