package pl.krzystooof.stanwodwpolsce.ui.home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.krzystooof.stanwodwpolsce.data.dataFromSource;

class mRecyclerAdapter extends RecyclerView.Adapter {
    ArrayList<dataFromSource> data;
    public mRecyclerAdapter(ArrayList<dataFromSource> data) {
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
