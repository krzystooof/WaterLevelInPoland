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


public class mRecycler {

    String LogTag = "mRecycler";

    private RecyclerView recyclerView;
    private mRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private Context context;

    public mRecycler(View root, ArrayList<DataFromSource> data, Context context) {
        this.context = context;
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new mRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
        itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        Log.i(LogTag, "created, items = " + adapter.getItemCount());
    }

    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }
    public int getItemCount(){
        return adapter.getItemCount();
    }

    public void showSnackbar(String text, boolean durationLong) {
        Snackbar snackbar;
        if (durationLong) snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG);
        else snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_SHORT);
        snackbar.show();
        Log.i(LogTag, "Snackbar " + text + " shown");
    }

    class TouchHelper extends ItemTouchHelper.SimpleCallback {
        Drawable backgroundColor;
        Drawable icon;
        private mRecyclerAdapter adapter;

        public TouchHelper(mRecyclerAdapter adapter) {
            super(0, ItemTouchHelper.LEFT);
            this.adapter = adapter;
            backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_item_swipe);
            icon = ContextCompat.getDrawable(context, R.drawable.baseline_star_border_black_18dp);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (viewHolder.getItemViewType() == 3) {
                //set element back to its original position
                adapter.notifyItemChanged(position);
                //left
                if (direction == 4) {
                    // TODO add or remove from favourites, notify
                    Log.i(LogTag, "mRecyclerTouchHelper: item no " + position + " swiped left");
                }
            }
        }

        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (viewHolder.getItemViewType() == 3) {
                super.onChildDraw(c, recyclerView, viewHolder, dX,
                        dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;

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
