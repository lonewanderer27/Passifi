package com.ja_cabili.passifi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ja_cabili.passifi.TodayFragment;
import com.ja_cabili.passifi.FutureFragment;
import com.ja_cabili.passifi.AllFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TodayFragment();
            case 1:
                return new FutureFragment();
            case 2:
                return new AllFragment();
            default:
                return new TodayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}