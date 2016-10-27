package com.example.admin.w2d3downloadfile;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

public class MyService extends Service {

    private static final String TAG = "TAG_";
    private DownloadManager downloadManager;
    private String downloadUrl;
    private long reference;
    public static final String fileName = "example.jpg";

    public MyService() {
        downloadUrl = "http://futcreativo.mx/wp-content/uploads/2015/09/memes-clasico.jpg";
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();



        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(downloadUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setTitle("Download Notification").setDescription("Downloading...");
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        reference = downloadManager.enqueue(request);



        Log.d(TAG, "onCreate: download enqueued");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
