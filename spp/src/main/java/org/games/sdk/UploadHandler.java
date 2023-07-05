package org.games.sdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * @author gaoyu
 * @date 2022/3/8
 */
public class UploadHandler {

    private String[] permissions = {"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"};
    private Activity context;
    private Uri photoUri;

    private ValueCallback<Uri[]> filePathCallback;

    public UploadHandler(Activity context) {
        this.context = context;
    }

    public boolean onShowFileChooser(WebView mWebView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
        if (checkPermission(permissions)) {
            Intent intent2 = new Intent("android.intent.action.CHOOSER");
            intent2.putExtra("android.intent.extra.INITIAL_INTENTS", new Intent[]{getCameraIntent()});
            intent2.putExtra("android.intent.extra.INTENT", createIntent(fileChooserParams));
            intent2.putExtra("android.intent.extra.TITLE", "chooser");

            context.startActivityForResult(intent2, 9999);
            this.filePathCallback = filePathCallback;
        } else {
            ActivityCompat.requestPermissions(context, permissions, 1);
            filePathCallback.onReceiveValue(null);
        }
        return true;
    }

    public Intent createIntent(WebChromeClient.FileChooserParams fileChooserParams) {
        String[] acceptTypes;
        String str = "*/*";
        if (!(fileChooserParams == null || (acceptTypes = fileChooserParams.getAcceptTypes()) == null || acceptTypes.length <= 0)) {
            String str2 = acceptTypes[0];
            str = str2;
        }
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.addCategory("android.intent.category.OPENABLE");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(str);
        return intent;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            if (filePathCallback != null) {
                filePathCallback.onReceiveValue(null);
                photoUri = null;
                filePathCallback = null;
            }
            return;
        }
        if (requestCode == 9999) {
            Uri[] uris = null;
            if (data == null) {
                uris = new Uri[]{photoUri};
            } else {
                if (data.getClipData() != null) {
                    // 多张图片选择
                    int count = data.getClipData().getItemCount();
                    uris = new Uri[count];
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        uris[i] = imageUri;
                    }
                } else if (data.getData() != null) {
                    // 单张图片选择
                    uris = new Uri[]{data.getData()};
                }
            }
            filePathCallback.onReceiveValue(uris);
            photoUri = null;
            filePathCallback = null;
        }
    }

    private Intent getCameraIntent() {
        Intent intent = new Intent();
        photoUri = getCameraUri(new File(context.getExternalCacheDir(), "temp.jpg"));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", photoUri);
        return intent;
    }

    private Uri getCameraUri(File file) {
        return Build.VERSION.SDK_INT >= 24 ? FileProvider.getUriForFile(context, context.getPackageName(), file) : Uri.fromFile(file);
    }

    private boolean checkPermission(String[] ps) {
        try {
            for (String str : ps) {
                if (ContextCompat.checkSelfPermission(context, str) != 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            if (Build.VERSION.SDK_INT >= 23) {
                return false;
            }
        }
        return true;
    }

}
