package pl.krzystooof.stanwodwpolsce.ui.Search;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import pl.krzystooof.stanwodwpolsce.R;
import pl.krzystooof.stanwodwpolsce.data.DataFromSource;

class mRecyclerAdapter extends RecyclerView.Adapter {
    ArrayList<DataFromSource> data;
    public mRecyclerAdapter(ArrayList<DataFromSource> data) {
        this.data = data;
    }

    //3 types of views implemented: search, title, item

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //Search
        if (viewType == 1) {
            View listItem = layoutInflater.inflate(R.layout.recycler_search, parent, false);
            return new mViewHolderSearch(listItem);
        }
        //title
        else if (viewType == 2 || viewType == 0) {
            View listItem = layoutInflater.inflate(R.layout.recycler_title, parent, false);
            return new mViewHolderTitle(listItem);
        }
        //Item
        View listItem = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new mViewHolderItem(listItem);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        //Log.i(LogTag, "BindViewHolder: position = " + position + ", viewType = " + viewType);
        //hello
         if (viewType == 0) {
            mViewHolderTitle mHolder = (mViewHolderTitle) holder;
            mHolder.titleText.setText("Cześć!");
            mHolder.layout.setBackground(ContextCompat.getDrawable(mHolder.layout.getContext(), R.drawable.gradient_background1));
        }
        //Search
        else if (viewType == 1) {
            final mViewHolderSearch mHolder = (mViewHolderSearch) holder;
             mHolder.layout.setBackground(ContextCompat.getDrawable(mHolder.layout.getContext(), R.drawable.gradient_background2));
            // set on change listener
        }
        //title
        else if (viewType == 2) {
            mViewHolderTitle mHolder = (mViewHolderTitle) holder;
            mHolder.titleText.setText("Zobacz co mamy:");
            mHolder.layout.setBackground(ContextCompat.getDrawable(mHolder.layout.getContext(), R.drawable.gradient_background3));
        }
        //Item
        else if (viewType == 3){
            mViewHolderItem mHolder = (mViewHolderItem) holder;
            //item in array = position - 3 (1search + 2title)
            int index = position - 3;
            DataFromSource dataObject = data.get(index);
            mHolder.riverText.setText(dataObject.getRiverName());
            mHolder.heightText.setText(dataObject.getWaterAmount()+ " cm");
            mHolder.stationText.setText(dataObject.getStationName());
        }
    }

    @Override
    public int getItemCount() {
        int dataSize = data.size();

        //no offers - no views
        if (dataSize == 0) return 0;

        //offers + additional (1 search, 2 title)
        return dataSize + 1 + 2;
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();

        //no offers
        if (itemCount == 0) return 0;

        else {
            if (position < 3) return position;
            return 3;
        }
    }

    public static class mViewHolderSearch extends RecyclerView.ViewHolder {
        public EditText editText;
        public ConstraintLayout layout;

        public mViewHolderSearch(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.constraintSearch);
            editText = itemView.findViewById(R.id.editTextSearch);
            editText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static class mViewHolderTitle extends RecyclerView.ViewHolder {
        public TextView titleText;
        public ConstraintLayout layout;

        public mViewHolderTitle(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.textViewTitle);
            layout = itemView.findViewById(R.id.constraintTitle);
        }
    }

    public static class mViewHolderItem extends RecyclerView.ViewHolder {
        public TextView riverText;
        public TextView stationText;
        public TextView heightText;
        public TextView additionalText;
        public ConstraintLayout layout;

        public mViewHolderItem(@NonNull View itemView) {
            super(itemView);
            riverText = itemView.findViewById(R.id.river);
            stationText = itemView.findViewById(R.id.station);
            heightText = itemView.findViewById(R.id.height);
            additionalText = itemView.findViewById(R.id.additionalInfo);
            layout = itemView.findViewById(R.id.constraintItem);
        }
    }
}

//if data size = 0 placeholder like allegro when loading - set custom elements and set background to color primary dark
//if larger - search, title ("Zobacz co mamy:"), wyniki