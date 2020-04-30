package pl.krzystooof.stanwodwpolsce.Recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import pl.krzystooof.stanwodwpolsce.R;
import pl.krzystooof.stanwodwpolsce.data.DataFromSource;
import pl.krzystooof.stanwodwpolsce.ui.Search.SearchRecyclerAdapter;


public class mRecycler {

    String LogTag = "mRecycler";

    private RecyclerView recyclerView;
    private mRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private Context context;
    ArrayList<String> favourites;

    public mRecycler(View root, ArrayList<DataFromSource> data, ArrayList<String> favourites, Context context) {
        this.context = context;
        this.favourites = favourites;
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SearchRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
        itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter,data));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        Log.i(LogTag, "created, items = " + adapter.getItemCount());
    }

    public void notifyDataSetChanged() {
        adapter.notifyAdapterDataSetChanged();
    }

    public int getItemCount() {
        return adapter.getItemCount();
    }
    public int getVisibleItem(){
        return linearLayoutManager.findLastCompletelyVisibleItemPosition();
    }
    public void setVisibleItem(int number){
        linearLayoutManager.scrollToPosition(number);
    }

    public void showSnackbar(String text, boolean durationLong) {
        Snackbar snackbar;
        if (durationLong) snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG);
        else snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_SHORT);
        snackbar.show();
        Log.i(LogTag, "Snackbar " + text + " shown");
    }

    public boolean checkFavourite(String id){
        for(String string : favourites){
            if (string.equals(id))return true;
        }
        return false;
    }
    public void addFavourite(String id){
        favourites.add(id);
    }
    public void removeFavourite(String id){
        for(int i = 0; i<favourites.size();i++){
            if (favourites.get(i).equals(id)){
                favourites.remove(i);
                i--;
            }
        }
    }


    class TouchHelper extends ItemTouchHelper.SimpleCallback {
        private Drawable backgroundColor;
        private Drawable icon;
        private mRecyclerAdapter adapter;
        private int swipeableViewType = 3;
        private ArrayList<DataFromSource> data;

        public TouchHelper(mRecyclerAdapter adapter, ArrayList<DataFromSource> data) {
            super(0, ItemTouchHelper.LEFT);
            this.adapter = adapter;
            backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_item_swipe);
            this.data = data;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (viewHolder.getItemViewType() == swipeableViewType) {
                //set element back to its original position
                adapter.notifyItemChanged(position);
                //left
                if (direction == 4) {
                    String message;
                    int dataIndex = position-3;
                    DataFromSource station = data.get(dataIndex);
                    String stationId = station.getStationId();
                    String stationName = station.getStationName();
                    if (checkFavourite(stationId)){
                        removeFavourite(stationId);
                        showSnackbar("Usunięto z ulubionych - " + stationName, false);
                        message = " removed from favourites: " + stationId;
                    }
                    else {
                        addFavourite(stationId);
                        showSnackbar("Dodano do ulubionych - " + stationName, false);
                        message = " added to favourites: " + stationId;
                    }
                    Log.i(LogTag, "mRecyclerTouchHelper: item no " + position + " swiped left," + message);
                }
            }
        }

        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (viewHolder.getItemViewType() == swipeableViewType) {
                super.onChildDraw(c, recyclerView, viewHolder, dX,
                        dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int position = viewHolder.getAdapterPosition();
                int dataIndex = position-3;
                String stationId = data.get(dataIndex).getStationId();
                if (checkFavourite(stationId)){
                    icon = ContextCompat.getDrawable(context, R.drawable.baseline_star_white_18dp);
                }
                else {
                    icon = ContextCompat.getDrawable(context, R.drawable.baseline_star_border_18dp);
                }

                int backgroundCornerOffset = 20;
                int maxOffset = 250;

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if (dX < 0) { // Swiping to the left
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    backgroundColor.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    backgroundColor.draw(c);
                    //set max swipe position
                    if (dX < -maxOffset) {
                        onChildDraw(c, recyclerView, viewHolder, -maxOffset,
                                dY, actionState, false);
                    }
                }
                icon.draw(c);
            }
        }
    }
}
