package com.example.whatsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp.Fragments.CallsFragments;
import com.example.whatsapp.Fragments.StatusFragment;
import com.example.whatsapp.Fragments.chatsFragment;


public class FragmentsAdapter extends FragmentPagerAdapter{

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch ( position){
            case 0:return  new chatsFragment();
            case 1:return  new StatusFragment();
            case 2:return  new CallsFragments();
            default: return  new chatsFragment();

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tital= null;
        if(position==0){
            tital="CHATS";

        }
        if(position==1){
            tital="STATUS";

        }
        if(position==2){
            tital="Calls";

        }


        return tital;
    }
}