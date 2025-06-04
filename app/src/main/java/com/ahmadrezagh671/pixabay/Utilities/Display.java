package com.ahmadrezagh671.pixabay.Utilities;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Display {

    private static DisplayMetrics getDisplayMetrics(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getDisplayHeight(Activity activity){
        return getDisplayMetrics(activity).heightPixels;
    }
    public static int getDisplayWidth(Activity activity){
        return getDisplayMetrics(activity).widthPixels;
    }

    public static void setImageViewHeightAndWidth(ImageView imageView, int height, int width,Activity activity){
        int displayHeight = getDisplayHeight(activity);
        int maxHeight = (int) (displayHeight*0.6);
        int displayWidth = getDisplayWidth(activity);

        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        float ratio = (float) width / displayWidth;
        int final_height = (int) (height / ratio);
        int finalwidth = displayWidth;

        if (final_height > maxHeight){
            float ratio2 = (float) final_height / maxHeight;
            finalwidth = (int) (finalwidth / ratio2);
            final_height = maxHeight;
        }

        params.height = final_height;
        params.width = finalwidth;

        imageView.setLayoutParams(params);
    }

    public static void setImageViewWidthHalfScreen(ImageView imageView,Activity activity){
        int displayHeight = getDisplayHeight(activity);
        int height = (int) (displayHeight*0.3);

        float marginInPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                14*1.5f,
                getDisplayMetrics(activity)
        );

        int displayWidth = getDisplayWidth(activity);
        int width = (int) ((displayWidth*0.5) - marginInPx);

        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        params.height = height;
        params.width = width;

        imageView.setLayoutParams(params);
    }

}
