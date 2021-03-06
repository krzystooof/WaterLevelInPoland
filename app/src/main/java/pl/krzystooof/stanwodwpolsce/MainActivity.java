package pl.krzystooof.stanwodwpolsce;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pl.krzystooof.stanwodwpolsce.ui.Info.InfoFragment;
import pl.krzystooof.stanwodwpolsce.ui.Saved.SavedFragment;
import pl.krzystooof.stanwodwpolsce.ui.Search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment = manager.findFragmentByTag("SearchFragmentTAG");
        if (fragment == null) {
            transaction.add(
                    R.id.nav_host_fragment,
                    new SearchFragment(),
                    "SearchFragmentTAG");

        }
        else {
            transaction.replace(
                    R.id.nav_host_fragment,
                    new SearchFragment(),
                    "SearchFragmentTAG");
        }
        fragment = manager.findFragmentByTag("SavedFragmentTAG");
        if (fragment == null) {
            transaction.add(
                    R.id.nav_host_fragment,
                    new SavedFragment(),
                    "SavedFragmentTAG");
        }
        else {
            transaction.replace(
                    R.id.nav_host_fragment,
                    new SavedFragment(),
                    "SavedFragmentTAG");
        }
        fragment = manager.findFragmentByTag("SavedFragmentTAG");
        if (fragment == null) {
            transaction.add(R.id.nav_host_fragment,
                    new InfoFragment(),
                    "InfoFragmentTAG");
        }
        else {
            transaction.replace(
                    R.id.nav_host_fragment,
                    new InfoFragment(),
                    "InfoFragmentTAG");
        }

    }

}
