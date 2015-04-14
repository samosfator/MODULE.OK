package ua.samosfator.moduleok.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ua.samosfator.moduleok.R;

public class FragmentUtils {

    private static List<Fragment> addedFragments = new ArrayList<>();
    private static String TAG = "FragmentUtils";

    public static void showFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {

        hideFragments(fragmentTransaction);

        if (fragment.isAdded()) {
            Log.d(TAG, "showing... " + fragment.getClass().getSimpleName());

            fragmentTransaction.show(fragment);
        } else {
            Log.d(TAG, "adding... " + fragment.getClass().getSimpleName());

            removeOldSameFragments(fragmentTransaction, fragment);

            fragmentTransaction.add(R.id.main_container, fragment);
            fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
            fragmentTransaction.show(fragment);

            addedFragments.add(fragment);
        }
        fragmentTransaction.commit();
    }

    private static void hideFragments(FragmentTransaction fragmentTransaction) {
        for (Fragment addedFragment : addedFragments) {
            fragmentTransaction.hide(addedFragment);
        }
    }

    private static void removeOldSameFragments(FragmentTransaction fragmentTransaction, Fragment targetFragment) {
        for (Fragment addedFragment : addedFragments) {
            if (addedFragment.getClass().getSimpleName().equals(targetFragment.getClass().getSimpleName())) {
                Log.d(TAG, "removing old same... " + addedFragment.getClass().getSimpleName());
                fragmentTransaction.remove(addedFragment);
            }
        }
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        Log.d(TAG, "removing... " + fragment.getClass().getSimpleName());
        new Handler(Looper.getMainLooper()).post(() -> {
            fragmentManager.beginTransaction().remove(fragment).hide(fragment).commit();
            fragmentManager.executePendingTransactions();
        });
    }
}