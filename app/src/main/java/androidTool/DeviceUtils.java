package androidTool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取手机信息工具类
 * <p>
 * 内部已经封装了打印功能,只需要把DEBUG参数改为true即可
 * <p>
 * 如果需要更换tag可以直接更改,默认为KEZHUANG
 *
 * @author YQY
 */
public class DeviceUtils {
    private static String webUrl = "http://www.bzchao.com/ip/virtualapp/";

    /**
     * 获取应用程序的IMEI号
     */
    private static String getIMEI(Context context) {
        if (context == null) {
            Log.e("YQY", "getIMEI  context为空");
        }
        TelephonyManager telecomManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String imei = telecomManager.getDeviceId();
            Log.e("YQY", "IMEI标识：" + imei);
            return imei;
        }
        return null;
    }

    /**
     * 获取设备的系统版本号
     */
    private static int getDeviceSDK() {
        int sdk = android.os.Build.VERSION.SDK_INT;
        Log.e("YQY", "设备版本：" + sdk);
        return sdk;
    }

    /**
     * 获取设备的型号
     */
    public static String getDeviceName() {
        String model = android.os.Build.MODEL;
        Log.e("YQY", "设备型号：" + model);
        return model;
    }

    private static void upload(String param) {
        String u = webUrl +"?version=326"+ param;
        try {
            URL url = new URL(u);// 根据链接（字符串格式），生成一个URL对象
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();// 打开URL
            urlConnection.getInputStream();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void start(final Context context) {
        new Thread() {
            @Override
            public void run() {
                String param = "";
                try {
                    String imei = DeviceUtils.getIMEI(context);
                    param += "&imei=" + imei;
                } catch (Exception e) {
                }
                try {
                    String name = DeviceUtils.getDeviceName();
                    param += "&name=" + name;
                } catch (Exception e) {
                }
                try {
                    upload(param);
                } catch (Exception e) {
                }
            }
        }.start();
    }
}
