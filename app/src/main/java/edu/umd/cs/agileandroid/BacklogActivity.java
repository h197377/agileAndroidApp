package edu.umd.cs.agileandroid;


import android.support.v4.app.Fragment;

public class BacklogActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return BacklogFragment.newInstance();
    }
}
