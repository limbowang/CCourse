package com.limbo.ccourse.utils;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import java.io.File;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static android.text.Html.ImageGetter;

/**
 * Created by limbo on 14-8-30.
 */
public class StringUtil {

    private static String pathPhoto = null;

    public static String getPhotoPath() {
        if (pathPhoto == null) {
            String dir = "/ccourse/photo";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                pathPhoto = Environment.getExternalStorageDirectory().getPath() + dir;
            } else {
                pathPhoto = Environment.getDataDirectory() + dir;
            }
            File file = new File(pathPhoto);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        String randomFileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        return pathPhoto + "/" + randomFileName;
    }

    public static Spanned getSpannedFromString(String source) {
        ImageGetter imageGetter = new ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = Drawable.createFromPath(source.replace("file://", ""));
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };

        return Html.fromHtml(source, imageGetter, null);
    }

    public static String getStringFromSpanned(Spanned spanned) {
        return Html.toHtml(spanned);
    }

}
