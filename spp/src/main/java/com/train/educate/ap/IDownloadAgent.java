package com.train.educate.ap;

//insert class

public interface IDownloadAgent {
	void onDownloadStart();

	void onDownloadProgress(int progress);

	void onDownloadFinish();
}
