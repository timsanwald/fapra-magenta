package fapra.magenta.menu;

import fapra.magenta.GameActivity;
import fapra.magenta.GameFragment;
import fapra.magenta.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuFragment extends Fragment {
    
    GameActivity activity;
    private View btnStartGame;
    private View btnShop;
    private View btnHighscore;
    private View btnOptions;
    private View btnCredits;
    private AutoFitTextView txtTitle;
    
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    
    @Override
    public void onStart() {
        super.onStart();
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View current = inflater.inflate(R.layout.activity_menu_view, container, false);
        
        if (current instanceof LinearLayout) {
            txtTitle = new AutoFitTextView(getActivity());
            // TODO make better scaling
            txtTitle.setTextSize(400);
            txtTitle.setMaxTextSize(200);
            txtTitle.setPadding(5, 5, 5, 5);
            txtTitle.setMinTextSize(20);
            txtTitle.setTextColor(Color.WHITE);
            txtTitle.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
            txtTitle.setLayoutParams(Params1);
            txtTitle.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    txtTitle.setText(getString(R.string.app_name));
                }
            });
            
            //correctWidth(txtTitle, container.getWidth());
            ((LinearLayout) current).addView(txtTitle);
            
            btnStartGame = inflater.inflate(R.layout.menu_button, null);
            ((LinearLayout) current).addView(btnStartGame);
            
            btnShop = inflater.inflate(R.layout.menu_button, null);
            ((LinearLayout) current).addView(btnShop);
            
            btnHighscore = inflater.inflate(R.layout.menu_button, null);
            ((LinearLayout) current).addView(btnHighscore);
            
            btnOptions = inflater.inflate(R.layout.menu_button, null);
            ((LinearLayout) current).addView(btnOptions);
            
            btnCredits = inflater.inflate(R.layout.menu_button, null);
            ((LinearLayout) current).addView(btnCredits);
        }
        
        btnStartGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(new GameFragment());
            }
        });
        changeText(btnStartGame, "Start Game");
        
        btnShop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(new ShopFragment());
            }
        });
        changeText(btnShop, "Shop");
        
        btnHighscore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(new HighscoreFragment(getActivity()));
            }
        });
        changeText(btnHighscore, "Highscore");
        
        btnOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(optionsFragment);
            }
        });
        changeText(btnOptions, "Preferences");
        
        btnCredits.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceMainFragment(new CreditsFragment());
            }
        });
        changeText(btnCredits, "Credits");
        
        return current;
    }
    
    public void changeText(View v, String text) {
        if (v instanceof ViewGroup) {
            int count = ((ViewGroup) v).getChildCount();
            View current = null;
            for (int i=0; i<count; i++) {
                current = ((ViewGroup) v).getChildAt(i);
                if (current instanceof TextView) {
                    ((TextView) current).setText(text);
                }
            }
        }
    }
}
