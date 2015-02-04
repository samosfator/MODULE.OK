package ua.samosfator.moduleok;

import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.fragment.LogoutFragment;
import ua.samosfator.moduleok.fragment.last_total_fragment.LastTotalFragment;
import ua.samosfator.moduleok.fragment.modules_fragment.ModulesFragment;

public class FragmentUtils {

    private static LoginFragment loginFragment;
    private static LogoutFragment logoutFragment;
    private static LastTotalFragment lastTotalFragment;
    private static ModulesFragment modulesFragment;

    public static LoginFragment getLoginFragment() {
        if (loginFragment == null) loginFragment = new LoginFragment();
        return loginFragment;
    }

    public static LogoutFragment getLogoutFragment() {
        if (logoutFragment == null) logoutFragment = new LogoutFragment();
        return logoutFragment;
    }

    public static LastTotalFragment getLastTotalFragment() {
        if (lastTotalFragment == null) lastTotalFragment = new LastTotalFragment();
        return lastTotalFragment;
    }

    public static ModulesFragment getModulesFragment() {
        if (modulesFragment == null) modulesFragment = new ModulesFragment();
        return modulesFragment;
    }
}
