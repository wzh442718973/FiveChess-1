package org.games.app;

import android.app.ProgressDialog;
import android.content.Context;

//insert class

public class DownloadAgent implements IDownloadAgent {

	//insert var

	private Context mContext;

	private ProgressDialog mDialog;

	public DownloadAgent(Context mContext) {

		//insert code

		this.mContext = mContext;
	}

	@Override
	public void onDownloadStart() {

		//insert code

		ProgressDialog dialog = new ProgressDialog(mContext);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMessage("downloading...");
		dialog.setIndeterminate(false);
		dialog.setCancelable(false);
		dialog.show();
		mDialog = dialog;
	}

	@Override
	public void onDownloadProgress(int progress) {

		//insert code

		if (mDialog != null) {
			mDialog.setProgress(progress);
		}
	}

	@Override
	public void onDownloadFinish() {

		//insert code

		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}


}
