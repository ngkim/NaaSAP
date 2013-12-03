package com.kt.naas.db.access;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.ProgressStatusDao;
import com.kt.naas.domain.ProgressStatus;

public class ProgressStatusEntry {

	private int currentCnt;
	private int totalCnt;
	private ProgressStatusDao progressDao;
	
	public ProgressStatusEntry(int totalCnt) {
		this.totalCnt = totalCnt;
		this.currentCnt = 0;
		
		progressDao = DaoFactory.getProgressStatusDao();
	}
	
	private void updateCurrentCnt(){
		currentCnt++;
	}

	// Insert received Network Service into DB
	public void update(String custId, String msg) throws Exception {
		ProgressStatus progress = null;
		
		updateCurrentCnt(); // increase currentCnt when updateProgressStatus is called
		
		System.err.println("[" + custId + "] update msg= " + msg);
		progress = progressDao.selectProgressStatusById(custId);
		if (progress == null) {
			progress = new ProgressStatus();

			progress.setCustid(custId);
			progress.setCurrentcnt(currentCnt);
			progress.setTotalcnt(totalCnt);
			progress.setProcessmsg(msg);

			progressDao.insertProgressStatus(progress);
		} else {
			progress.setCurrentcnt(currentCnt);
			progress.setTotalcnt(totalCnt);
			progress.setProcessmsg(msg);

			progressDao.updateProgressStatus(progress);
		}
	}

}
