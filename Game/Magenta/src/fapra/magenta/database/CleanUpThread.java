package fapra.magenta.database;

import android.content.Context;
import android.util.Log;

public class CleanUpThread extends Thread {
	private KeyValueRepository keyValueRepo;
	private LinesRepository linesRepo;
	
	public CleanUpThread(Context context) {
		this.keyValueRepo = new KeyValueRepository(context);
		this.linesRepo = new LinesRepository(context);
	}
	
	@Override
	public void run() {
		super.run();
		try {
			String lastUploadedId = this.keyValueRepo.getValue("lastUploadedId");
			if(this.linesRepo.rowCountGreaderId(lastUploadedId) == 0) {
				this.linesRepo.deleteLinesToId(lastUploadedId);
				this.keyValueRepo.deleteKey("lastUploadedId");
			}
		} catch (Exception e) {
			Log.d("cleanup thread", "nothing to delete");
		} finally {
			this.keyValueRepo.close();
			this.linesRepo.close();
		}
	}
}
