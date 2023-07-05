package org.games.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.games.app.MainActivity;


//insert class

public class AppFunctionHelp {

    //insert var

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static String btnTag = "btn_home";
    private boolean isTouch = false;
    private boolean isMove = false;

    private float touchX;
    private float touchY;
    private static ImageView btn_home;
    private static long startTime = 0;
    private static long endTime = 0;
    private static boolean isCreate = false;
    private static View layout_home;


    private static AppFunctionHelp mInstace = null;

    public static AppFunctionHelp getInstance() {
        if (null == mInstace) {
            mInstace = new AppFunctionHelp();
        }
        return mInstace;
    }


    public boolean onTouch(View view, MotionEvent motionEvent) {
        //insert code

        String tag = (String) view.getTag();
        if (tag.equals(btnTag) == false) {
            return true;
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                isMove = false;
                touchX = motionEvent.getRawX() - btn_home.getTranslationX();
                touchY = motionEvent.getRawY() - btn_home.getTranslationY();
                System.out.println("ACTION_DOWN,x = " + touchX + ",y = " + touchY);

                float w = MainActivity.g_this.getWindow().getAttributes().width;
                float h = MainActivity.g_this.getWindow().getAttributes().height;
                System.out.println("ACTION_DOWN,w = " + w + ",h = " + h);
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouch) {
                    float moveX = motionEvent.getRawX();
                    float moveY = motionEvent.getRawY();
                    System.out.println("ACTION_DOWN,x = " + moveX + ",y = " + moveY);
                    float difx = moveX - touchX;
                    float dify = moveY - touchY;
                    System.out.println("ACTION_DOWN,dx = " + difx + ",dy = " + dify);

                    btn_home.setTranslationX(difx);
                    btn_home.setTranslationY(dify);
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                System.out.println("ACTION_UP");

                endTime = System.currentTimeMillis();
                //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                if ((endTime - startTime) > 0.1 * 1000L) {
                    isMove = true;
                } else {
                    isMove = false;
                }
                System.out.println("执行顺序up");


                if (!isMove) {
                    isMove = false;
                    System.out.println("send click event");
                    this.ShowbackHomeLayer();
                }
                break;
        }
        return true;
    }

    public static void ShowbackHomeLayer() {
        //insert code

        System.out.println("backHome2");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.g_this);
        builder.setTitle("是否确认返回大厅?");
        builder.setMessage("确认后将退出当前游戏");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击确定触发的事件
                NativeJavaClass.backHome();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show(); //构建AlertDialog并显示

    }


    public static void showHomeButton() {
        //insert code

        System.out.println("showHomeButton");
        /*MainActivity.g_this.runOnUiThread(new Runnable() {
            @Override
            public void run() {*/
        if (!isCreate) {
            LayoutInflater layoutInflater = MainActivity.g_this.getLayoutInflater();
            Resources r = MainActivity.g_this.getResources();
            int id = r.getIdentifier("hall_button", "layout", MainActivity.g_this.getPackageName());
            layout_home = layoutInflater.inflate(id, null);
            MainActivity.g_this.addContentView(layout_home, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            btn_home = (ImageView) layout_home.findViewWithTag(btnTag);
            btn_home.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return AppFunctionHelp.getInstance().onTouch(v, event);
                }
            });
            isCreate = true;
        }
        layout_home.setVisibility(View.VISIBLE);
         /*   }
        });*/
    }

    public static void hideHomeButton() {
        //insert code

        System.out.println("hideHomeButton");
        MainActivity.g_this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isCreate) {
                    layout_home.setVisibility(View.INVISIBLE);
                    btn_home.setX(0);
                    btn_home.setY(0);
                }

            }
        });

    }

    public void WhatsAppShareUrl(String Url) {
        //insert code

        if (Url != null) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, Url);
            try {
                MainActivity.g_this.startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.g_this, "Whatsapp have not been installed.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void WhatsAppOpenNumMessages(String Num) {
        //insert code

        if (Num != null) {
            try {
                Uri uri = Uri.parse("smsto:" + Num);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                MainActivity.g_this.startActivity(Intent.createChooser(i, ""));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.g_this, "Whatsapp have not been installed.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void setOrientation(String dir) {
        //insert code
        MainActivity.g_this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (dir.equals("V")) {
                        MainActivity.g_this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    } else {
                        MainActivity.g_this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void GetTime() {
        //insert code

    }

    private void SaveTime() {
        //insert code

    }
}
