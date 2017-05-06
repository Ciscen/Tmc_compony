package com.auvgo.tmc.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by lc on 2017/4/15
 */

public class FileUtils {
    public static Bitmap scaleBitmap(Bitmap bm, float scale) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 截图
     */
    public static void captureScreen(Context context, View v) {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        String fileName = File.separator + System.currentTimeMillis() + ".png";
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap drawingCache = v.getDrawingCache();
        if (drawingCache != null) {
            FileOutputStream outputStream = getOutputStream(filePath + fileName);
            Bitmap bitmap = scaleBitmap(drawingCache, 0.1f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            bitmap.recycle();
            notifyFileSystemChanged(context, filePath + fileName);
        }
        v.destroyDrawingCache();
    }

    private static FileOutputStream getOutputStream(String s) {
        if (Environment.isExternalStorageEmulated()) {
            LogFactory.d("====FILE ABSOLUTE PATH====" + s);
            try {
                return new FileOutputStream(s);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通知文件系统
     *
     * @param context
     * @param path
     */
    private static void notifyFileSystemChanged(Context context, String path) {
        if (path == null)
            return;
        final File f = new File(path);
        if (Build.VERSION.SDK_INT >= 19 /*Build.VERSION_CODES.KITKAT*/) { //添加此判断，判断SDK版本是不是4.4或者高于4.4
            String[] paths = new String[]{path};
            MediaScannerConnection.scanFile(context, paths, null, null);
        } else {
            final Intent intent;
            if (f.isDirectory()) {
                intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                intent.setClassName("com.android.providers.media", "com.android.providers.media.MediaScannerReceiver");
                intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
                LogFactory.d("directory changed, send broadcast:" + intent.toString());
            } else {
                intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(new File(path)));
                LogFactory.d("file changed, send broadcast:" + intent.toString());
            }
            context.sendBroadcast(intent);
        }
    }
}
