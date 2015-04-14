package ua.samosfator.moduleok;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.fragment.subjects.DetailedSubjectsFragment;
import ua.samosfator.moduleok.fragment.modules.ModulesFragment;
import ua.samosfator.moduleok.fragment.last_and_total.LastTotalFragment;

public class FragmentsKeeper {

    private static List<Fragment> all = Collections.synchronizedList(new ArrayList<>());

    private static Fragment lastTotal = new LastTotalFragment();
    private static Fragment modules = new ModulesFragment();
    private static LoginFragment login = new LoginFragment();
    private static DetailedSubjectsFragment detailedSubjectsFragment = new DetailedSubjectsFragment();

    public static Fragment getLastTotal() {
        return lastTotal;
    }

    public static void setLastTotal(Fragment lastTotal) {
        FragmentsKeeper.lastTotal = lastTotal;
    }

    public static Fragment getModules() {
        return modules;
    }

    public static void setModules(Fragment modules) {
        FragmentsKeeper.modules = modules;
    }

    public static LoginFragment getLogin() {
        return login;
    }

    public static void setLogin(LoginFragment login) {
        FragmentsKeeper.login = login;
    }

    public static DetailedSubjectsFragment getDetailedSubjectsFragment() {
        return detailedSubjectsFragment;
    }

    public static void setDetailedSubjectsFragment(DetailedSubjectsFragment detailedSubjectsFragment) {
        FragmentsKeeper.detailedSubjectsFragment = detailedSubjectsFragment;
    }

    public static List<Fragment> getAll() {
        populateFragmentsList();
        return all;
    }

    private static void populateFragmentsList() {
        all.clear();
        all.add(lastTotal);
        all.add(modules);
        all.add(login);
    }
}
