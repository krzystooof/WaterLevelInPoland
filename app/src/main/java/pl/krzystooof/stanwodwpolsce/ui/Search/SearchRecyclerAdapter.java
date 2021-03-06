package pl.krzystooof.stanwodwpolsce.ui.Search;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.krzystooof.stanwodwpolsce.R;
import pl.krzystooof.stanwodwpolsce.Recycler.mRecyclerAdapter;
import pl.krzystooof.stanwodwpolsce.data.DataFromSource;
import pl.krzystooof.stanwodwpolsce.data.ManageData;


public class SearchRecyclerAdapter extends mRecyclerAdapter {

    String LogTag = "SearchRecycler";

    ArrayList<DataFromSource> data;
    ArrayList<DataFromSource> backup;


    public SearchRecyclerAdapter(ArrayList<DataFromSource> data) {
        super();
        this.data = data;
        backup = new ArrayList<>(data);
    }



    //types of views implemented: search(1), title(0,2,4), item(3)

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
        else if (viewType == 2 || viewType == 0 || viewType == 4) {
            View listItem = layoutInflater.inflate(R.layout.recycler_title, parent, false);
            return new mViewHolderTitle(listItem);
        }
        //Item
        View listItem = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new mViewHolderItem(listItem);

    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Log.i(LogTag, "BindViewHolder: position = " + position + ", viewType = " + viewType + ", items = " + getItemCount());
        //hello
        if (viewType == 0) {
            mViewHolderTitle mHolder = (mViewHolderTitle) holder;
            mHolder.titleText.setText("Cześć!");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mHolder.layout.setBackground(ContextCompat.getDrawable(mHolder.layout.getContext(), R.drawable.gradient_background1));
            }
        }
        //Search
        else if (viewType == 1) {
            final mViewHolderSearch mHolder = (mViewHolderSearch) holder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mHolder.layout.setBackground(ContextCompat.getDrawable(mHolder.layout.getContext(), R.drawable.gradient_background2));
            }
            final Handler mHandler = new Handler();
            mHolder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.postDelayed(userStoppedTyping, 500);
                }
                Runnable userStoppedTyping = new Runnable() {

                    @Override
                    public void run() {
                        ArrayList<DataFromSource> newToShow = new ManageData().filer(mHolder.editText.getText().toString(),backup);
                        data.clear();
                        for(DataFromSource object : newToShow){
                            data.add(object);
                        }
                        notifyDataSetChanged();
                        mHolder.editText.setSelection(mHolder.editText.getText().length());
                    }
                };
            });
        }
        //title
        else if (viewType == 2) {
            mViewHolderTitle mHolder = (mViewHolderTitle) holder;
            if (getItemCount() > 4) mHolder.titleText.setText("Zobacz co mamy:");
            else mHolder.titleText.setText("Brak wyników");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mHolder.layout.setBackground(ContextCompat.getDrawable(mHolder.layout.getContext(), R.drawable.gradient_background3));
            }
        }
        //Item
        else if (viewType == 3) {
            mViewHolderItem mHolder = (mViewHolderItem) holder;
            //item in array = position - 3 (1search + 2title)
            int index = position - 3;
            DataFromSource dataObject = data.get(index);
            mHolder.riverText.setText(dataObject.getRiverName());
            mHolder.heightText.setText(dataObject.getWaterAmount() + " cm");
            mHolder.stationText.setText(dataObject.getStationName());
        }
        //end gradient
        else if (viewType == 4) {
            mViewHolderTitle mHolder = (mViewHolderTitle) holder;
            String text = " ";
            //make bigger if items not on all screen
            if (position < 10) {
                text += "\n\n\n\n\n\n\n\n";
                for (int i = 4; i < 10 - position; i++) text += "\n";
            }
            mHolder.titleText.setText(text);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mHolder.layout.setBackground(ContextCompat.getDrawable(mHolder.layout.getContext(), R.drawable.gradient_background_reversed));
            }
        }
    }

    @Override
    public int getItemCount() {
        int dataSize = data.size();
        //offers + additional (1 search, 3 title)
        return dataSize + 1 + 3;
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();

        if (position < 3) return position;
        else if (position == itemCount - 1) return 4;
        return 3;

    }
    @Override
    public void notifyAdapterDataSetChanged() {
        backup = new ArrayList<>(data);
        notifyDataSetChanged();
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