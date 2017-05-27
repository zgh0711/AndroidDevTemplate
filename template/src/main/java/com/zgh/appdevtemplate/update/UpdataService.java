package com.zgh.appdevtemplate.update;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.zgh.appdevtemplate.R;

import java.io.File;

public class UpdataService extends IntentService implements UpdataUtils.UpdataListener {

    private static final int NOTIFICATION_ID = 1;
    private static final int MAX_PROGRESS = 100;
    private static final String TAG = "UpdataService";

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    private static Context mContext;
    private static String mUrl;

    public UpdataService() {
        super(TAG);
    }

    /**
     * 启动服务
     *
     * @param context 上下文
     */
    public static void startService(Context context,String url) {
        mContext = context;
        mUrl = url;
        Intent intent = new Intent(context, UpdataService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        UpdataUtils downloadUtils = new UpdataUtils();
        downloadUtils.download(mUrl, this);
    }

    @Override
    public void onUpdataStart() {
        showNotification(0);
    }

    @Override
    public void onUpdataloading(int percent) {
        showNotification(percent);
    }

    @Override
    public void onUpdataFinish() {
        Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
        mNotificationManager.cancel(NOTIFICATION_ID);
        mContext.startActivity(getInstallIntent());
    }

    /**
     * 建立和刷新通知
     *
     * @param progress 进度
     */
    private void showNotification(int progress) {
        if (mBuilder == null) {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
            mBuilder.setTicker("正在下载...");
            mBuilder.setContentTitle("正在下载...");
            mBuilder.setWhen(System.currentTimeMillis());
        }
        mBuilder.setProgress(MAX_PROGRESS, progress, false);
        mBuilder.setContentText(progress + "%");
        Notification notification = mBuilder.build();
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 获取安装意图
     *
     * @return i
     */
    private Intent getInstallIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(UpdataConstant.APK_PATH)), "application/vnd.android.package-archive");
        return intent;
    }
}
