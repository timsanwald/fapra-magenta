package fapra.magenta.menu;

import fapra.magenta.GameActivity;
import fapra.magenta.GameFragment;
import fapra.magenta.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
    
    GameActivity activity;
    private Button btnStartGame;
    private Button btnOptions;
    private Button btnCredits;
    private Button btnShop;
    
    private SharedPreferences preferences;
    private OptionsFragment optionsFragment;
    
    public MenuFragment(GameActivity activity) {
        this.activity = activity;
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        optionsFragment = new OptionsFragment();
        preferences.registerOnSharedPreferenceChangeListener(optionsFragment);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View current = inflater.inflate(R.layout.activity_menu_view, container, false);
        btnStartGame = (Button) current.findViewById(R.id.btn_game_start);
        btnStartGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(new GameFragment());
            }
        });
        
        btnOptions = (Button) current.findViewById(R.id.btn_options);
        btnOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(optionsFragment);
            }
        });
        
        btnShop= (Button) current.findViewById(R.id.btn_shop);
        btnShop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(new ShopFragment());
            }
        });
        
        btnCredits = (Button) current.findViewById(R.id.btn_credits);
        btnCredits.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(new CreditsFragment());
            }
        });

        return current;
    }
}
