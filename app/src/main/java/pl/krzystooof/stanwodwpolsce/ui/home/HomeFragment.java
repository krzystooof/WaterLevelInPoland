package pl.krzystooof.stanwodwpolsce.ui.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;

import pl.krzystooof.stanwodwpolsce.R;
import pl.krzystooof.stanwodwpolsce.data.DataFromSource;
import pl.krzystooof.stanwodwpolsce.data.ManageData;

public class HomeFragment extends Fragment {

    //TODO change edit text backgound color mEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

    private HomeViewModel homeViewModel;
    private ArrayList<DataFromSource> data;
    String LogTag = "SearchFragment ";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i(LogTag, "view model: created");

        String jsonUrl = "https://danepubliczne.imgw.pl/api/data/hydro/";
        ArrayList<DataFromSource> data = new ArrayList<>();

        //start downloading data
        new GetData(jsonUrl, data).execute();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    class mRecycler{
        private RecyclerView recyclerView;
        private mRecyclerAdapter adapter;
        private LinearLayoutManager linearLayoutManager;
        private ItemTouchHelper itemTouchHelper;

        mRecycler(View root, ArrayList<DataFromSource> data) {
            Context context = getContext();
            recyclerView = root.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new mRecyclerAdapter(data);
            recyclerView.setAdapter(adapter);
            itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);
            Log.i(LogTag, "recycler: created, items = " + adapter.getItemCount());
        }

        protected mRecyclerAdapter getAdapter() {
            return adapter;
        }

        public LinearLayoutManager getLinearLayoutManager() {
            return linearLayoutManager;
        }

        protected void showSnackbar(String text, boolean durationLong) {
            Snackbar snackbar;
            if (durationLong) snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG);
            else snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_SHORT);
            snackbar.show();
            Log.i(LogTag, "recycler: Snackbar " + text + " shown");
        }

        class TouchHelper extends ItemTouchHelper.SimpleCallback {
            ColorDrawable backgroundColorLeft;
            Drawable icon;
            private mRecyclerAdapter adapter;

            public TouchHelper(mRecyclerAdapter adapter) {
                super(0, ItemTouchHelper.LEFT);
                this.adapter = adapter;
                backgroundColorLeft = new ColorDrawable(Color.WHITE);
                icon = ContextCompat.getDrawable(getContext(), R.drawable.baseline_star_border_black_18dp);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (viewHolder.getItemViewType() == 0) {
                    //set element back to its original position
                    adapter.notifyItemChanged(position);
                    //left
                    if (direction == 4) {
                        // TODO add or remove from favourites
                        Log.i(LogTag, "mRecyclerTouchHelper: item no " + position + " swiped left");
                    }
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (viewHolder.getItemViewType() == 0) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX,
                            dY, actionState, isCurrentlyActive);
                    View itemView = viewHolder.itemView;

                    int backgroundCornerOffset = 20;

                    int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                    int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                    int iconBottom = iconTop + icon.getIntrinsicHeight();

                    if (dX < 0) { // Swiping to the left
                        int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                        int iconRight = itemView.getRight() - iconMargin;
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                        backgroundColorLeft.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                                itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        backgroundColorLeft.draw(c);
                    }
                    icon.draw(c);
                }
            }
        }
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

                //add data to recycler's array
                for (DataFromSource dataFromSource : downloaded){
                    data.add(dataFromSource);
                }
            } catch (IOException e) {
                //no database connection
                //TODO show snackbar and Log
            }
            return null;
        }
    }
}
