package com.ahmadrezagh671.pixabay.Utilities;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class AppUtil {

    public static int getAppVersion(Context context){
        try{
            return (int) (context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES).getLongVersionCode());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getAppVersionStr(Context context){
        try{
            return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str; // Handle null or empty strings
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void openLink(String url, Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Unable to open link.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void downloadFile(String url, String fileName, Activity activity){
        Context context = activity.getApplicationContext();
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Pixabay/" + fileName);
        long reference = manager.enqueue(request);

        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
    }
}
