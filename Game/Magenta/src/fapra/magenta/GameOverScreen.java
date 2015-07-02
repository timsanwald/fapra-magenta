package fapra.magenta;

import fapra.magenta.data.Highscore;
import fapra.magenta.menu.AutoFitTextView;
import fapra.magenta.simulation.Simulation;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameOverScreen extends Fragment {
    
    private Simulation simulation;
    
    public GameOverScreen(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AutoFitTextView txtScore = (AutoFitTextView) view.findViewById(R.id.game_over_score);
        if (txtScore != null) {
            txtScore.setPadding((int) (view.getWidth() * 0.25f), 5, (int) (view.getWidth() * 0.25f), 5);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_over_layout, null);
        
        AutoFitTextView txtTitle = (AutoFitTextView) v.findViewById(R.id.game_over_title);
        txtTitle.setTextSize(400);
        txtTitle.setMaxTextSize(200);
        txtTitle.setPadding(5, 5, 5, 5);
        txtTitle.setMinTextSize(20);
        txtTitle.setTextColor(Color.WHITE);
        
        AutoFitTextView txtScore = (AutoFitTextView) v.findViewById(R.id.game_over_score);
        txtScore.setText("Score: " + simulation.scoringListener.score);
        txtScore.setTextSize(400);
        txtScore.setMaxTextSize(200);
        txtScore.setMinTextSize(20);
        txtScore.setTextColor(Color.WHITE);
        
        Highscore highscore = new Highscore(getActivity());
        if (highscore.inHighscore(simulation.scoringListener.score)) {
            // TODO present add to highscore dialog
            v.findViewById(R.id.game_over_new_score).setVisibility(View.VISIBLE);
        }
        return v;
    }
}
