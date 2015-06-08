package fapra.magenta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		GameListener listener = new GameListener();
    	GameView view = new GameView(getActivity(), null, listener);
        return view;
    }
}
