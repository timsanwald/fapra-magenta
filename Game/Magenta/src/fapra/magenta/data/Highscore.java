package fapra.magenta.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Highscore {
    private SharedPreferences preferences;
    public String names[];
    public long score[];

    public Highscore(Context context) {
        preferences = context.getSharedPreferences("Highscore", 0);
        names = new String[10];
        score = new long[10];
        reset();
        for (int x = 0; x < 10; x++) {
            names[x] = preferences.getString("name" + x, null);
            score[x] = preferences.getLong("score" + x, x);
            Log.d("Highscore", "x=" +x +" name=" +names[x] + " score=" + score[x]);
        }
    }

    public String getName(int x) {
        // get the name of the x-th position in the Highscore-List
        if (names[x] == null) {
            return (x +1) + ".";
        }
        return names[x];
    }

    public long getScore(int x) {
        // get the score of the x-th position in the Highscore-List
        return score[x];
    }

    public boolean inHighscore(long score) {
        // test, if the score is in the Highscore-List
        int position;
        for (position = 0; position < 10; position++) {
            ;
            if (this.score[position] <= score) {
                return true;
            }
        }

        return false;
    }

    public boolean addScore(String name, long score) {
        // add the score with the name to the Highscore-List
        int position;
        for (position = 0; position < 10 && this.score[position] > score; position++) {
            ;
        }

        if (position == 10) {
            return false;
        }

        for (int x = 9; x > position; x--) {
            names[x] = names[x - 1];
            this.score[x] = this.score[x - 1];
        }
        if (name == null) {
            this.names[position] = new String((position + 1) + ".");
        } else {
            this.names[position] = new String(name);   
        }
        this.score[position] = score;

        save();
        return true;
    }
    
    public void reset() {
        for (int i = 0; i<10 ; i++) {
            this.score[i] = (9-i) * 10000 + 10000;
            this.names[i] = null;
        }
    }
    
    public void save() {
        SharedPreferences.Editor editor = preferences.edit();
        for (int x = 0; x < 10; x++) {
            editor.putString("name" + x, this.names[x]);
            editor.putLong("score" + x, this.score[x]);
        }
        editor.commit();
    }
}