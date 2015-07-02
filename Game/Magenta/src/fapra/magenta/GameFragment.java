package fapra.magenta;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameFragment extends Fragment {

    Game listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		listener = new Game();
    	GameView view = new GameView(getActivity(), null, listener);
        return view;
    }
	
	@Override
	public void onPause() {
	    super.onPause();
	    listener.dispose();
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	 // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        listener.setLandscape(true);
	        Log.d("Orientation", "true");
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        listener.setLandscape(false);
	        Log.d("Orientation", "false");
	    }
	}
}
