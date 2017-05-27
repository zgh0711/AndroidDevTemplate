package com.zgh.appdevtemplate.update;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdataUtils {
    private int mTotalSize = 1;
    private int mCurrentSize = 0;

    private UpdataListener mUpdataListener;
    private long           currentTime;

    public interface UpdataListener {
        void onUpdataStart();

        void onUpdataloading(int percent);

        void onUpdataFinish();
    }


    public void download(String url, final UpdataListener listener) {
        mUpdataListener = listener;
        File file = creatFile();//创建文件
        download(url, file);//下载文件
    }

    private void download(String path, File file) {
        HttpURLConnection conn = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setUseCaches(true);
            InputStream is = conn.getInputStream();
            mTotalSize = conn.getContentLength();
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 4];
            int len;
            mUpdataListener.onUpdataStart();//下载开始
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                mCurrentSize += len;

                long time = System.currentTimeMillis();//下载中...
                if (time - currentTime > 1000) {
                    currentTime = time;
                    int percent = getPercent();
                    Log.i("", "percent==" + percent);
                    mUpdataListener.onUpdataloading(percent);
                }
            }
            fos.flush();
            mUpdataListener.onUpdataFinish();//下载结束
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (fos != null) try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int getPercent() {
        return mCurrentSize * 100 / mTotalSize;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File creatFile() {
        String folder = UpdataConstant.SDCARD_PATH + File.separator + UpdataConstant.APK_FOLDER;
        String filePath = UpdataConstant.APK_PATH;
        //先创建文件夹
        File fileFolder = new File(folder);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        //创建文件
        return new File(filePath);
    }
}
