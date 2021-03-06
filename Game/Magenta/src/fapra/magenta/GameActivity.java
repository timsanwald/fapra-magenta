package fapra.magenta;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import fapra.magenta.apiClient.Client;
import fapra.magenta.database.CleanUpThread;
import fapra.magenta.menu.MenuFragment;

public class GameActivity extends FragmentActivity {
    
	Fragment currentFragment;
	private Client apiClient;
	private Thread apiClientThread;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.main_preferences, false);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_screen);

		// Check that the activity is using the layout version with
        // the fragment_container FrameLayout
		View gameView = findViewById(R.id.game_fragment);
        if (gameView != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            Fragment firstFragment = new MenuFragment(this);
            
			// In case this activity was started with special instructions from
			// an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.game_fragment, firstFragment).commit();
        }
	}
	
	public void replaceMainFragment(Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction().setTransition(
						FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	    transaction.replace(R.id.game_fragment, fragment);
	    transaction.addToBackStack(fragment.toString());
	    transaction.commit();
	}

    public void replaceMainFragmentNoBack(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.game_fragment, fragment);
        transaction.commit();
    }

	@Override
	public void onResume() {
		super.onResume();
		apiClient = new Client(this);

		apiClientThread = new Thread(apiClient);
		apiClientThread.start();
}

	@Override
	protected void onPause() {
		super.onPause();
		apiClientThread.interrupt();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		// clean up the database
		new CleanUpThread((Context) this).start();
	}
}
