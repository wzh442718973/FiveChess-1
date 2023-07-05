package org.games.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.games.app.FileProvider;
import org.games.app.IDownloadAgent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//insert class

public class UpdateApp extends AsyncTask<Void, Integer, Long> {

	//insert var

	private Context mContext;
	private String url;
	private IDownloadAgent downloadAgent;
	private File downLoadFile;
	/**
	 * 内部使用
	 */
	private DownloadInfo downloadInfo;

	private long temp_lastTime;

	private final static int EVENT_START = 1;
	private final static int EVENT_PROGRESS = 2;

	public UpdateApp(Context context, String url, IDownloadAgent downloadAgent) {
		//insert code

		this.url = url;
		this.mContext = context;
		this.downloadAgent = downloadAgent;
		this.downloadInfo = new DownloadInfo();
		this.temp_lastTime = 0;
	}

	private void installApp(Context context, File file) {
		//insert code

		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT < 24) {
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		} else {
			Uri uri = FileProvider.getUriForFile(context, context.getPackageName() , file);
			intent.setDataAndType(uri, "application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	private File downloadApp(Context context, String downloadUrl) {
		//insert code

		URL url;
		try {
			url = new URL(downloadUrl);
		} catch(MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		HttpURLConnection connection = null;
		try{
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Accept", "application/*");
			connection.setConnectTimeout(10000);
			connection.connect();
			File file = new File(context.getExternalCacheDir(), "update_"+ System.currentTimeMillis()+".apk");
			downloadInfo.totalByte = connection.getContentLength();
			downloadInfo.startDownloadTime = System.currentTimeMillis();
			publishProgress(EVENT_START);
			copy(connection.getInputStream(), new FileOutputStream(file));
			return file;
		} catch (IOException e) {
			if(connection != null) {
				connection.disconnect();
			}
			e.printStackTrace();
			return null;
		}
	}


	private int copy(InputStream in, FileOutputStream out) throws IOException {
		//insert code

		int BUFFER_SIZE = 1024 * 100;
		byte[] buffer = new byte[BUFFER_SIZE];
		BufferedInputStream bis = new BufferedInputStream(in, BUFFER_SIZE);
		try {
			int bytes = 0;
			while(true) {
				int n = bis.read(buffer, 0, BUFFER_SIZE);
				if (n == -1) {
					break;
				}
				out.write(buffer, 0, n);
				bytes += n;
				downloadInfo.downloadedByte = bytes;
				publishProgress(EVENT_PROGRESS);
			}
			return bytes;
		} finally {
			out.close();
			bis.close();
			in.close();
		}
	}

	/**
	 * Runs on the UI thread before {@link #doInBackground}.
	 *
	 * @see #onPostExecute
	 * @see #doInBackground
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	/**
	 * <p>Runs on the UI thread after {@link #doInBackground}. The
	 * specified result is the value returned by {@link #doInBackground}.</p>
	 *
	 * <p>This method won't be invoked if the task was cancelled.</p>
	 *
	 * @param aLong The result of the operation computed by {@link #doInBackground}.
	 * @see #onPreExecute
	 * @see #doInBackground
	 */
	@Override
	protected void onPostExecute(Long aLong) {
		//insert code

		downloadAgent.onDownloadFinish();
		if(downLoadFile != null) {
			installApp(mContext, downLoadFile);
		}else{
			Log.e("error", "download File Failed");
		}
	}

	/**
	 * Runs on the UI thread after {@link #publishProgress} is invoked.
	 * The specified values are the values passed to {@link #publishProgress}.
	 *
	 * @param progress The values indicating progress.
	 * @see #publishProgress
	 * @see #doInBackground
	 */
	@Override
	protected void onProgressUpdate(Integer... progress) {
		//insert code

		switch (progress[0]) {
			case EVENT_START:
				downloadAgent.onDownloadStart();
				break;
			case EVENT_PROGRESS:
				long now = System.currentTimeMillis();
				if (now - temp_lastTime < 900) {
					break;
				}
				temp_lastTime = now;
				downloadAgent.onDownloadProgress((int)((downloadInfo.downloadedByte+0.0) / downloadInfo.totalByte *100));
				break;
		}
	}

	/**
	 * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
	 *
	 * <p>The default implementation simply invokes {@link #onCancelled()} and
	 * ignores the result. If you write your own implementation, do not call
	 * <code>super.onCancelled(result)</code>.</p>
	 *
	 * @param aLong The result, if any, computed in
	 * @see #cancel(boolean)
	 * @see #isCancelled()
	 */
	@Override
	protected void onCancelled(Long aLong) {
		//super.onCancelled(aLong);
	}

	/**
	 * This method is invoked by the default implementation of
	 *
	 * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
	 *
	 * @see #cancel(boolean)
	 * @see #isCancelled()
	 */
	@Override
	protected void onCancelled() {
		//insert code

		super.onCancelled();
	}

	/**
	 * Override this method to perform a computation on a background thread. The
	 * specified parameters are the parameters passed to {@link #execute}
	 * by the caller of this task.
	 * <p>
	 * This method can call {@link #publishProgress} to publish updates
	 * on the UI thread.
	 *
	 * @param voids The parameters of the task.
	 * @return A result, defined by the subclass of this task.
	 * @see #onPreExecute()
	 * @see #onPostExecute
	 * @see #publishProgress
	 */
	@Override
	protected Long doInBackground(Void... voids) {
		//insert code

		downLoadFile = downloadApp(mContext, url);
		return null;
	}


	class DownloadInfo {
		public int totalByte;
		public long startDownloadTime;
		public int downloadedByte;
	}
}
