package edu.umd.cs.agileandroid;

import android.support.v4.app.Fragment;

/**
 * Created by Beijie on 4/6/2017.
 */

public class TeamActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return TeamFragment.newInstance();
    }
}
