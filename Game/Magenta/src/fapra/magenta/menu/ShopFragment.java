package fapra.magenta.menu;

import fapra.magenta.R;
import fapra.magenta.data.pickups.CoinPickUp;
import fapra.magenta.data.pickups.MoveForwardPickUp;
import fapra.magenta.data.pickups.StopTimePickUp;
import fapra.magenta.data.save.SaveGame;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ShopFragment extends Fragment {

    ViewGroup main;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.shop_menu_layout, container, false);
        main = v;
        
        final SaveGame sg = new SaveGame();
        sg.load(this.getActivity());
        
        for (int i=0; i< v.getChildCount(); i++) {
            if (v.getChildAt(i).getId() == R.id.shop_top_bar) {
                TextView coinValueView = new TextView(this.getActivity());
                coinValueView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                coinValueView.setText("" + sg.coins);
                coinValueView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
                coinValueView.setTextColor(getResources().getColor(R.color.white));
                ((ViewGroup) v.getChildAt(i)).addView(coinValueView);
                break;
            }
        }
        
        View timeItem = createUpgradeItem(this.getResources().getDrawable(R.drawable.ic_time), "Stops the time", inflater);
        timeItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sg.coins > StopTimePickUp.getUpgradeCost(sg.stopTimeStage + 1)) {
                    sg.stopTimeStage++;
                    sg.coins -= StopTimePickUp.getUpgradeCost(sg.stopTimeStage);
                    StopTimePickUp.setStage(sg.stopTimeStage);
                    sg.save(getActivity());
                } else {
                    Toast.makeText(getActivity(), "Insufficient coins", Toast.LENGTH_SHORT).show();
                }
            }
        });
        main.addView(timeItem);
        
        View coinView = createUpgradeItem(this.getResources().getDrawable(R.drawable.ic_coin), "More coins at pick-up", inflater);
        coinView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sg.coins > CoinPickUp.getUpgradeCost(sg.coinPickupStage + 1)) {
                    sg.coinPickupStage++;
                    sg.coins -= CoinPickUp.getUpgradeCost(sg.coinPickupStage);
                    CoinPickUp.setStage(sg.coinPickupStage);
                    sg.save(getActivity());
                } else {
                    Toast.makeText(getActivity(), "Insufficient coins", Toast.LENGTH_SHORT).show();
                }
            }
        });
        main.addView(coinView);
        
        View forwardView = createUpgradeItem(this.getResources().getDrawable(R.drawable.ic_forward), "More automatic connections", inflater);
        forwardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sg.coins > MoveForwardPickUp.getUpgradeCost(sg.moveForwardStage + 1)) {
                    sg.moveForwardStage++;
                    sg.coins -= MoveForwardPickUp.getUpgradeCost(sg.moveForwardStage);
                    MoveForwardPickUp.setStage(sg.moveForwardStage);
                    sg.save(getActivity());
                } else {
                    Toast.makeText(getActivity(), "Insufficient coins", Toast.LENGTH_SHORT).show();
                }
            }
        });
        main.addView(forwardView);
        
        
        return v;
    }
    
    
    private View createUpgradeItem(Drawable image, String description, LayoutInflater inflater) {
        ViewGroup upgradeItem = (ViewGroup) inflater.inflate(R.layout.upgrade_item, null);
        ((ImageView) upgradeItem.getChildAt(0)).setImageDrawable(image);
        ((TextView) upgradeItem.getChildAt(1)).setText(description);
        return upgradeItem;
    }
}
