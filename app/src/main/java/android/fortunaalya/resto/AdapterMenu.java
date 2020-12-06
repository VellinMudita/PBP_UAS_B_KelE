package android.fortunaalya.resto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ViewHolder> {

    private List<GetSetMenu> itemLists;
    private Context context;

    public AdapterMenu(List<GetSetMenu> itemLists, Context context){
        this.itemLists = itemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetSetMenu getSetMenu = itemLists.get(position);

        holder.tvNama.setText(getSetMenu.getNama_makanan());
        holder.tvHarga.setText(getSetMenu.getHarga());
        if (getSetMenu.getNama_makanan().equals("Ayam")){
            holder.imageViewGambar.setImageResource(R.drawable.ayam);
        }else if (getSetMenu.getNama_makanan().equals("Sate")){
            holder.imageViewGambar.setImageResource(R.drawable.sate);
        }

    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvHarga, tvNama;
        public ImageView imageViewGambar;

        public ViewHolder(View view){
            super(view);

            tvHarga = view.findViewById(R.id.tvHarga);
            tvNama = view.findViewById(R.id.tvNama);
            imageViewGambar = view.findViewById(R.id.imageViewGambar);
        }
    }
}
