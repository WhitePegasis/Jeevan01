package com.example.jeevan.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jeevan.DoctorFragments.docFragment01;
import com.example.jeevan.DoctorFragments.docFragment02;
import com.example.jeevan.DoctorFragments.docFragment03;


public class docMainFragmentAdapter extends FragmentStateAdapter {


    public docMainFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new docFragment01();
            case 1: return new docFragment02();
            default: return  new docFragment03();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}