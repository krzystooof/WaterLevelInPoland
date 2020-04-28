package pl.krzystooof.stanwodwpolsce.ui.home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.krzystooof.stanwodwpolsce.data.DataFromSource;

class mRecyclerAdapter extends RecyclerView.Adapter {
    ArrayList<DataFromSource> data;
    public mRecyclerAdapter(ArrayList<DataFromSource> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
