package com.ahmadrezagh671.pixabay.Utilities;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {

    public static Fragment openFragment(FragmentManager fragmentManager, int fragmentContainerView_id, Fragment newfragment, Fragment lastFragment){
        Log.i("FragmentUtil", "open fragment: " + newfragment + "\nlast fragment was: " + lastFragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (lastFragment != null)
            fragmentTransaction.hide(lastFragment);

        if (fragmentManager.getFragments().contains(newfragment))
            fragmentTransaction.show(newfragment);
        else
            fragmentTransaction.add(fragmentContainerView_id,newfragment);

        lastFragment = newfragment;
        fragmentTransaction.commit();

        return lastFragment;
    }
}
