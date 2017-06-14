package edu.umd.cs.agileandroid;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SprintActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return SprintFragment.newInstance();
    }
    public static Intent newIntent(Context context) {
        return new Intent(context, SprintActivity.class);
    }
}
