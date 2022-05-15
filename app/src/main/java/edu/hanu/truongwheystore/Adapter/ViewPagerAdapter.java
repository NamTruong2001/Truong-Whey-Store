package edu.hanu.truongwheystore.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import edu.hanu.truongwheystore.Fragment.CategoryFragment;
import edu.hanu.truongwheystore.Fragment.FavouriteFragment;
import edu.hanu.truongwheystore.Fragment.HomeFragment;
import edu.hanu.truongwheystore.Fragment.SearchFragment;
import edu.hanu.truongwheystore.Fragment.UserFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new SearchFragment();
            case 3:
                return new FavouriteFragment();
            case 4:
                return new UserFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
