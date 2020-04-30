package pl.krzystooof.stanwodwpolsce.ui.Search;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Comparator;

import pl.krzystooof.stanwodwpolsce.R;
import pl.krzystooof.stanwodwpolsce.Recycler.mRecycler;
import pl.krzystooof.stanwodwpolsce.data.DataFromSource;
import pl.krzystooof.stanwodwpolsce.data.ManageData;
import pl.krzystooof.stanwodwpolsce.data.mSharedPref;

public class SearchFragment extends Fragment {


    String LogTag = "SearchFragment";
    mRecycler recycler;
    private SearchViewModel searchViewModel;
    ArrayList<DataFromSource> data;
    ArrayList<String> favourites;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);
        Log.i(LogTag, "view model: onCreate");

        String jsonUrl = "https://danepubliczne.imgw.pl/api/data/hydro/";
        data = new ArrayList<>();
        favourites = new ArrayList<>();

        //start downloading data
        new GetData(jsonUrl, data).execute();

        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                recycler = new mRecycler(root, data, favourites, getContext());
            }
        });
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(LogTag, "view model: onPause");
        mSharedPref sharedPref = new mSharedPref(getContext());
        sharedPref.saveData(data);
        sharedPref.saveFavourites(favourites);
        sharedPref.saveAdapterPosition(recycler.getVisibleItem());
        sharedPref.savePaused(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(LogTag, "view model: onDetach");
        mSharedPref sharedPref = new mSharedPref(getContext());
        sharedPref.savePaused(false);
    }

    class GetData extends AsyncTask<String, String, String> {
        String jsonUrl;
        ArrayList<DataFromSource> data;
        mSharedPref sharedPref;
        boolean paused;

        public GetData(String jsonUrl, ArrayList<DataFromSource> data) {
            this.jsonUrl = jsonUrl;
            this.data = data;
            sharedPref = new mSharedPref(getContext());
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                ArrayList<DataFromSource> downloaded;
                paused = sharedPref.readPaused();
                if(paused){
                    downloaded = sharedPref.readData();
                    favourites = sharedPref.readFavourites();
                    Log.i(LogTag, "GetData: data retrieved from sharedPref, size = " + downloaded.size());
                }
                else {
                    downloaded = new ManageData().download(jsonUrl);
                    Log.i(LogTag, "GetData: data retrieved from jsonUrl, size = " + downloaded.size());
                }

                //sort data
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    downloaded.sort(new Comparator<DataFromSource>() {
                        @Override
                        public int compare(DataFromSource o1, DataFromSource o2) {
                            String o1Name = o1.getStationName();
                            String o2Name = o2.getStationName();
                            return o1Name.compareTo(o2Name);
                        }
                    });
                }
                data.clear();
                //add data to recycler's array
                for (DataFromSource dataFromSource : downloaded) {
                    data.add(dataFromSource);
                }
                Log.i(LogTag, "GetData: copied data to recycler's array");
            } catch (IOException e) {
                //no database connection
                recycler.showSnackbar("Brak połączenia", true);
                Log.i(LogTag, "GetData: No connection");
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recycler.notifyDataSetChanged();
            Log.i(LogTag, "GetData: notified about data change, recycler items = " + recycler.getItemCount());
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && paused){
                int visibleItem = sharedPref.readAdapterPosition();
                recycler.setVisibleItem(visibleItem);
                Log.i(LogTag, "GetData: recycler scrolled to position no " + visibleItem);
            }
        }
    }
}
