package pl.krzystooof.stanwodwpolsce.ui.Search;

import android.os.AsyncTask;
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

import pl.krzystooof.stanwodwpolsce.R;
import pl.krzystooof.stanwodwpolsce.Recycler.mRecycler;
import pl.krzystooof.stanwodwpolsce.data.DataFromSource;
import pl.krzystooof.stanwodwpolsce.data.ManageData;

public class SearchFragment extends Fragment {


    String LogTag = "SearchFragment";
    mRecycler recycler;
    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);
        Log.i(LogTag, "view model: created");

        String jsonUrl = "https://danepubliczne.imgw.pl/api/data/hydro/";
        final ArrayList<DataFromSource> data = new ArrayList<>();

        //start downloading data
        new GetData(jsonUrl, data).execute();

        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                recycler = new mRecycler(root, data, getContext());
            }
        });
        return root;
    }


    class GetData extends AsyncTask<String, String, String> {
        String jsonUrl;
        ArrayList<DataFromSource> data;

        public GetData(String jsonUrl, ArrayList<DataFromSource> data) {
            this.jsonUrl = jsonUrl;
            this.data = data;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                ArrayList<DataFromSource> downloaded = new ManageData().download(jsonUrl);
                Log.i(LogTag, "GetData: data retrieved from jsonUrl, size = " + downloaded.size());

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
        }
    }
}
