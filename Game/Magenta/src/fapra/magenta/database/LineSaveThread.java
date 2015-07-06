package fapra.magenta.database;

import java.util.LinkedList;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import fapra.magenta.data.Line;
import fapra.magenta.simulation.Simulation;

public class LineSaveThread extends Thread {
	private LinesRepository linesRepo;
	private LinkedList<Line> lines;

	public LineSaveThread(Simulation simulation) {
		super();
		this.linesRepo = new LinesRepository((Context) simulation.activity);
		this.lines = simulation.lines;
	}

	@Override
	public void run() {
		for (Line line : this.lines) {
			if (line.isResearchable) {
				try {
					linesRepo.insertLine(line.toJSON().toString());
					Log.d("line saved", "saved line in db");
				} catch (JSONException e) {
					Log.e("parsing line", "failed");
				}
			}
		}
		
		linesRepo.close();
	}
}
