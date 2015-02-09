package ua.samosfator.moduleok;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.fragment.last_total_fragment.LastTotalFragment;
import ua.samosfator.moduleok.fragment.modules_fragment.ModulesFragment;

public class FragmentsKeeper {

    private static List<Fragment> all = Collections.synchronizedList(new ArrayList<>());

    private static Fragment lastTotal = new LastTotalFragment();
    private static Fragment modules = new ModulesFragment();
    private static LoginFragment login = new LoginFragment();

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
