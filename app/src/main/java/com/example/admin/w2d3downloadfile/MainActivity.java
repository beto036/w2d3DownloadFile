package com.example.admin.w2d3downloadfile;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG_";

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                long receivedID = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
                DownloadManager mgr = (DownloadManager)
                        context.getSystemService(Context.DOWNLOAD_SERVICE);

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(receivedID);
                Cursor cur = mgr.query(query);
                int index = cur.getColumnIndex(
                        DownloadManager.COLUMN_STATUS);
                if(cur.moveToFirst()) {
                    if(cur.getInt(index) ==
                            DownloadManager.STATUS_SUCCESSFUL){
                        Intent intent2 = new Intent();
                        intent2.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                        startActivity(intent2);
                    }
                }
                cur.close();

            }
        };

        registerReceiver(receiver, intentFilter);
    }


    public void startDownload(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }


}
