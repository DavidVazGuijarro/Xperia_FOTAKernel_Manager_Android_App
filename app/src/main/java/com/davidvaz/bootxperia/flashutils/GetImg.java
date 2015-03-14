package com.davidvaz.bootxperia.flashutils;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import com.davidvaz.bootxperia.R;

/**
 * Created by davidvaz on 26/1/14.
 */
public class GetImg {
    private String LOG_TAG = "XRM";

    private static String BOOT_BASE_URL = "http://davidvazguijarro.uni.me/davidvazguijarro/boot/boot/";
    

    private String[] codenames, props;
    private String deviceName;
    private SaveDir dir;

    private Boolean supported = false;

    private Context c;

    public GetImg(Context context) {
        c = context;
        codenames = context.getResources().getStringArray(R.array.supported_device_codename);
        props = context.getResources().getStringArray(R.array.supported_device_prop);
        dir = new SaveDir();

        for (int i = 0; i < props.length; i++) {
            if (props[i].equalsIgnoreCase(Build.DEVICE)) {
                deviceName = codenames[i];
                supported = true;
            }
        }
        Log.d(LOG_TAG, "prepare a download method for " + deviceName);
    }

    public Boolean isSupported() {
        return supported;
    }

    public void downloadboot() {
        String url = BOOT_BASE_URL + deviceName + "/boot.img";
        Log.d(LOG_TAG, "downloading " + url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("kernel for " + deviceName);
        request.setTitle("kernel.img");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        final File boot = new File(dir.bootPath());
        Log.d(LOG_TAG, "Saving at " + dir.bootPath());
        if (boot.exists() || boot.isFile()){
            AlertDialog.Builder tex = new AlertDialog.Builder(c);
            tex.setTitle("FILE EXISTS");
            tex.setMessage(c.getString(R.string.alert_exists_boot_image));
            tex.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boot.delete();
                }
            });
            tex.show();
        }
        request.setDestinationUri(Uri.parse("file://" + dir.bootPath()));
        DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast done = Toast.makeText(c,
                "kernel image downloading to \n"
                + boot.getPath(),
                Toast.LENGTH_LONG);
        done.show();
    }
}
