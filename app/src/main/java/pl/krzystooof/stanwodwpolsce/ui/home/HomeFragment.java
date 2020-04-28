package pl.krzystooof.stanwodwpolsce.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;
import java.util.ArrayList;

import pl.krzystooof.stanwodwpolsce.R;
import pl.krzystooof.stanwodwpolsce.data.Download;
import pl.krzystooof.stanwodwpolsce.data.mData;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<mData> data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String jsonUrl = "https://danepubliczne.imgw.pl/api/data/hydro/";
        ArrayList<mData> data = new ArrayList<>();

        //start downloading data

        new GetData(jsonUrl, data).execute();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    class GetData extends AsyncTask<String, String, String> {
        String jsonUrl;
        ArrayList<mData> data;

        public GetData(String jsonUrl, ArrayList<mData> data) {
            this.jsonUrl = jsonUrl;
            this.data = data;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                ArrayList<mData> downloaded = new Download().run(jsonUrl);

                //add data to recycler's array
                for (mData mData : downloaded){
                    data.add(mData);
                }
            } catch (IOException e) {
                //no database connection
                //TODO show snackbar and Log
            }
            return null;
        }
    }
}
