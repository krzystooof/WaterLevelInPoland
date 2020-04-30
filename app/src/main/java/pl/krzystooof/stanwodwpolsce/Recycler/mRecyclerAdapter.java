package pl.krzystooof.stanwodwpolsce.Recycler;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
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
import pl.krzystooof.stanwodwpolsce.data.DataFromSource;
import pl.krzystooof.stanwodwpolsce.data.ManageData;


public class mRecyclerAdapter extends RecyclerView.Adapter {

    public mRecyclerAdapter() {
    }



    //types of views implemented: search(1), title(0,2,4), item(3)

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new mViewHolderItem(listItem);

    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void notifyAdapterDataSetChanged() {
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