package fapra.magenta.menu;

import fapra.magenta.R;
import fapra.magenta.data.pickups.CoinPickUp;
import fapra.magenta.data.pickups.MoveForwardPickUp;
import fapra.magenta.data.pickups.StopTimePickUp;
import fapra.magenta.data.save.SaveGame;
import android.graphics.Color;
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
    private TextView coinValueView;
    private View coinUpgrade;
    private View timeUpgrade;
    private View forwardUpgrade;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.shop_menu_layout, container, false);
        main = v;
        
        final SaveGame sg = new SaveGame();
        sg.load(this.getActivity());
        
        for (int i=0; i< v.getChildCount(); i++) {
            if (v.getChildAt(i).getId() == R.id.shop_top_bar) {
                coinValueView = new TextView(this.getActivity());
                coinValueView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                coinValueView.setText("" + sg.coins);
                coinValueView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
                coinValueView.setTextColor(getResources().getColor(R.color.white));
                ((ViewGroup) v.getChildAt(i)).addView(coinValueView);
                break;
            }
        }
        
        timeUpgrade = createUpgradeItem(this.getResources().getDrawable(R.drawable.ic_time), "Increase the time the follower stops on pick up", inflater);
        timeUpgrade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sg.stopTimeStage + 1 <= StopTimePickUp.getMaxStage() && sg.coins > StopTimePickUp.getUpgradeCost(sg.stopTimeStage + 1)) {
                    sg.stopTimeStage++;
                    sg.coins -= StopTimePickUp.getUpgradeCost(sg.stopTimeStage);
                    StopTimePickUp.setStage(sg.stopTimeStage);
                    sg.save(getActivity());
                    updateView();
                } else {
                    Toast.makeText(getActivity(), "Insufficient coins", Toast.LENGTH_SHORT).show();
                }
            }
        });
        main.addView(timeUpgrade);
        
        coinUpgrade = createUpgradeItem(this.getResources().getDrawable(R.drawable.ic_coin), "Get more coins for the coin pick up", inflater);
        coinUpgrade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sg.coinPickupStage + 1 <= CoinPickUp.getMaxStage() && sg.coins > CoinPickUp.getUpgradeCost(sg.coinPickupStage + 1)) {
                    sg.coinPickupStage++;
                    sg.coins -= CoinPickUp.getUpgradeCost(sg.coinPickupStage);
                    CoinPickUp.setStage(sg.coinPickupStage);
                    sg.save(getActivity());
                    updateView();
                } else {
                    Toast.makeText(getActivity(), "Insufficient coins", Toast.LENGTH_SHORT).show();
                }
            }
        });
        main.addView(coinUpgrade);
        
        forwardUpgrade = createUpgradeItem(this.getResources().getDrawable(R.drawable.ic_forward), "Increase the number of automatic connections on pick up", inflater);
        forwardUpgrade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sg.moveForwardStage + 1 <= MoveForwardPickUp.getMaxStage() && sg.coins > MoveForwardPickUp.getUpgradeCost(sg.moveForwardStage + 1)) {
                    sg.moveForwardStage++;
                    sg.coins -= MoveForwardPickUp.getUpgradeCost(sg.moveForwardStage);
                    MoveForwardPickUp.setStage(sg.moveForwardStage);
                    sg.save(getActivity());
                    updateView();
                } else {
                    Toast.makeText(getActivity(), "Insufficient coins", Toast.LENGTH_SHORT).show();
                }
            }
        });
        main.addView(forwardUpgrade);
        
        this.updateView();
        return v;
    }
    
    
    private View createUpgradeItem(Drawable image, String description, LayoutInflater inflater) {
        ViewGroup upgradeItem = (ViewGroup) inflater.inflate(R.layout.upgrade_item, null);
        ((ImageView) upgradeItem.findViewById(R.id.upgrade_item_image)).setImageDrawable(image);
        ((TextView) upgradeItem.findViewById(R.id.upgrade_item_description)).setText(description);
        return upgradeItem;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }
    
    private void updateView() {
        SaveGame sg = new SaveGame();
        sg.load(this.getActivity());
        
        // enable possible upgrades
        // Update costs
        if (sg.moveForwardStage + 1 <= MoveForwardPickUp.getMaxStage() && sg.coins > MoveForwardPickUp.getUpgradeCost(sg.moveForwardStage + 1)) {
            forwardUpgrade.findViewById(R.id.upgrade_image).setVisibility(View.VISIBLE);
            ((TextView) (forwardUpgrade.findViewById(R.id.upgrade_item_cost))).setTextColor(Color.WHITE);
        } else {
            forwardUpgrade.findViewById(R.id.upgrade_image).setVisibility(View.INVISIBLE);
            ((TextView) (forwardUpgrade.findViewById(R.id.upgrade_item_cost))).setTextColor(Color.RED);
        }
        if (sg.coinPickupStage + 1 <= CoinPickUp.getMaxStage() && sg.coins > CoinPickUp.getUpgradeCost(sg.coinPickupStage + 1)) {
            coinUpgrade.findViewById(R.id.upgrade_image).setVisibility(View.VISIBLE);
            ((TextView) (coinUpgrade.findViewById(R.id.upgrade_item_cost))).setTextColor(Color.WHITE);
        } else {
            coinUpgrade.findViewById(R.id.upgrade_image).setVisibility(View.INVISIBLE);
            ((TextView) (coinUpgrade.findViewById(R.id.upgrade_item_cost))).setTextColor(Color.RED);
        }
        if (sg.stopTimeStage + 1 <= StopTimePickUp.getMaxStage() && sg.coins > StopTimePickUp.getUpgradeCost(sg.stopTimeStage + 1)) {
            timeUpgrade.findViewById(R.id.upgrade_image).setVisibility(View.VISIBLE);
            ((TextView) (timeUpgrade.findViewById(R.id.upgrade_item_cost))).setTextColor(Color.WHITE);
        } else {
            timeUpgrade.findViewById(R.id.upgrade_image).setVisibility(View.INVISIBLE);
            ((TextView) (timeUpgrade.findViewById(R.id.upgrade_item_cost))).setTextColor(Color.RED);
        }
        if (sg.stopTimeStage + 1 > StopTimePickUp.getMaxStage()) {
            //Maximum upgrade
            ((TextView) (timeUpgrade.findViewById(R.id.upgrade_item_cost))).setText("Max");
        } else {
            ((TextView) (timeUpgrade.findViewById(R.id.upgrade_item_cost))).setText("Cost: "
                    + StopTimePickUp.getUpgradeCost(sg.stopTimeStage + 1));
        }
        if (sg.moveForwardStage + 1 > MoveForwardPickUp.getMaxStage()) {
            ((TextView) (forwardUpgrade.findViewById(R.id.upgrade_item_cost))).setText("Max");
        } else {
            ((TextView) (forwardUpgrade.findViewById(R.id.upgrade_item_cost))).setText("Cost: "
                    + MoveForwardPickUp.getUpgradeCost(sg.moveForwardStage + 1));
        }
        
        if (sg.coinPickupStage + 1 > CoinPickUp.getMaxStage()) {
            ((TextView) (coinUpgrade.findViewById(R.id.upgrade_item_cost))).setText("Max");
        } else {
            ((TextView) (coinUpgrade.findViewById(R.id.upgrade_item_cost))).setText("Cost: "
                    + CoinPickUp.getUpgradeCost(sg.coinPickupStage + 1));
        }
        
        

        // Update Coins
        coinValueView.setText("" + sg.coins);

    }
}
