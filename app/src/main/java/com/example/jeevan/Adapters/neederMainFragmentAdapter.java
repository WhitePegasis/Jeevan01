package com.example.jeevan.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jeevan.NeederFragments.NeederFragment01;
import com.example.jeevan.NeederFragments.NeederFragment02;
import com.example.jeevan.NeederFragments.NeederFragment03;


/* this fragment adapter is for needers page it returns required fragment instance according to the tab position*/

public class neederMainFragmentAdapter extends FragmentStateAdapter {


    public neederMainFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    //show pages according to the tab position
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new NeederFragment01();
            case 1: return new NeederFragment02();
            default: return  new NeederFragment03();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
