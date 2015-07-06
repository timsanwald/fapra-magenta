package fapra.magenta.menu;

import fapra.magenta.R;
import fapra.magenta.data.Highscore;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class HighscoreFragment extends Fragment {

    private final MyAdapter myAdapter;
    
    public HighscoreFragment(Activity activity) {
        super();
        myAdapter = new MyAdapter(activity);
    }
   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.highscore_layout, null);
        
        ListView list;
        for (int i=0; i<v.getChildCount(); i++) {
            if (v.getChildAt(i).getId() == R.id.highscore_list) {
                list = (ListView) v.getChildAt(i);
                list.setAdapter(myAdapter);
                break;
            }
        }
        
        return v;
    }
    
    private class MyAdapter extends BaseAdapter {

        private Context context;
        private Highscore highscore;

        public MyAdapter(Context context) {
            this.context = context;
            highscore = new Highscore(context);
        }

        @Override
        public int getCount() {
            return highscore.names.length;
        }

        @Override
        public Object getItem(int position) {
            return highscore;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TwoLineListItem twoLineListItem;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                twoLineListItem = (TwoLineListItem) inflater.inflate(
                        android.R.layout.simple_list_item_2, null);
            } else {
                twoLineListItem = (TwoLineListItem) convertView;
            }
            //TextView text1 = (TextView) twoLineListItem.findViewById(android.R.id.text2);
            TextView text1 = twoLineListItem.getText1();
            text1.setGravity(Gravity.CENTER);
            TextView text2 = twoLineListItem.getText2();
            text2.setTextColor(getResources().getColor(R.color.white));
            text2.setGravity(Gravity.CENTER);

            text1.setText(highscore.getName(position));
            text2.setText("" + highscore.getScore(position));

            return twoLineListItem;
        }
        
        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }
    
}
