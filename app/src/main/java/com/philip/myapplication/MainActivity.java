package com.philip.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.FileObserver;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.philip.myapplication.UI.LogListItem;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements SysLog.ILogObserver {

    private final String TAG = MainActivity.class.getSimpleName();

    private FileObserver mObserver;
    private ListView mListView = null;
    private SharedPreferences mPrefSet;
    private CustomArrayAdapter mArrayAdapter = null;
    private ViewGroup mBackgroundLayout = null;

    private static final int RESULT_SETTING = 1;

    private static final String FILE_SEPERATER = File.separator;
    private final int NUMBER_OF_BUTTONS = 3;

    private static ArrayList <LogListItem> mRenderList = new ArrayList <LogListItem> ();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String username;
        String path;
        int monitingAction;

        SysLog.getInstance().registerObserver(this);

        setContentView(R.layout.activity_main);

        mBackgroundLayout = (ViewGroup) findViewById(R.id.background_layout);



        mPrefSet = PreferenceManager.getDefaultSharedPreferences(this);

        username = mPrefSet.getString("prefUsername", "");

        String monitingActionStr = mPrefSet.getString("monitoring_type", Integer.toString(FileObserverHelper.ALL_EVENTS));
        monitingAction = Integer.parseInt(monitingActionStr);
        if (monitingAction == 0) {monitingAction = FileObserverHelper.ALL_EVENTS;}

        path = mPrefSet.getString("monitoring_diretory", "Pictures");
        if(path != null || !"".equals(path)) {
            path = FILE_SEPERATER + path + FILE_SEPERATER;
        }

        mArrayAdapter = new CustomArrayAdapter(this, SysLog.getInstance().getLog());


        mListView = (ListView)this.findViewById(R.id.logging);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new OnItemClickHandler());


        mObserver = new FileObserverHelper(Environment.getExternalStorageDirectory().getPath() + path, monitingAction);


        int buttonWidth = getWindow().getAttributes().width / NUMBER_OF_BUTTONS;
        final Button startButton = (Button)this.findViewById(R.id.start);
        Button stopButton = (Button)this.findViewById(R.id.stop);
        Button clearButton = (Button)this.findViewById(R.id.clear);

        startButton.setWidth(buttonWidth);
        stopButton.setWidth(buttonWidth);
        clearButton.setWidth(buttonWidth);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mObserver.startWatching();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.already_start), Toast.LENGTH_SHORT);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mObserver.stopWatching();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysLog.getInstance().clearLog();
            }
        });


        if(SysLog.getInstance() != null) {
            try {
                SysLog.getInstance().registerObserver(this);
            } catch (Exception e) {
                Log.e(TAG, "Failure in register observer" + e.toString());
            }
        }



    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.listener_settings: {
                //Toast.makeText(this, "setting opening...", Toast.LENGTH_SHORT).show();
                Intent setIntent = new Intent(this, UserSettingActivity.class);
                startActivity(setIntent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SETTING:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mObserver.stopWatching();
    }

    @Override
    public void onLogChanged(ArrayList <LogListItem> Log) {
        if(mArrayAdapter != null) {
            UpdateList(Log);
        }
    }

    private void UpdateList(final ArrayList <LogListItem> log) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "sizeqweewq: " + Integer.toString(log.size()));
                if (log.size() > 0) {
                    mBackgroundLayout.setVisibility(View.GONE);
                    if (mListView != null) {
                        mListView.setVisibility(View.VISIBLE);
                        try {
                            mRenderList = (ArrayList<LogListItem>) log.clone();
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                        for (LogListItem item : mRenderList) {
                            if(mArrayAdapter.getCount() >= SysLog.getInstance().MAX_ENTRY) {
                                mArrayAdapter.clear();
                            }
                            mArrayAdapter.add(item);
                            mArrayAdapter.notifyDataSetChanged();
                        }
                        mArrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (mListView != null) {
                        mArrayAdapter.clear();
                        mArrayAdapter.notifyDataSetChanged();
                        mListView.setVisibility(View.GONE);
                    }
                    mBackgroundLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}


