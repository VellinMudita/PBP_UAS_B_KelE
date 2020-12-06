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

public class AdapterMenuCheckout extends RecyclerView.Adapter<AdapterMenuCheckout.ViewHolder> {

    private List<GetSetMenuCheckout> itemLists;
    private Context context;

    public AdapterMenuCheckout(List<GetSetMenuCheckout> itemLists, Context context){
        this.itemLists = itemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_payment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetSetMenuCheckout getSetMenu = itemLists.get(position);

        holder.tvNama.setText(getSetMenu.getNama_makanan());
        holder.tvHarga.setText(getSetMenu.getHarga());
        holder.tvJumlah.setText(getSetMenu.getJumlah());
        holder.tvTotal.setText(getSetMenu.getTotal());
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

        public TextView tvHarga, tvNama, tvJumlah, tvTotal;
        public ImageView imageViewGambar;

        public ViewHolder(View view){
            super(view);

            tvHarga = view.findViewById(R.id.tvHarga);
            tvNama = view.findViewById(R.id.tvNama);
            tvJumlah = view.findViewById(R.id.tvJumlah);
            tvTotal = view.findViewById(R.id.tvTotal);
            imageViewGambar = view.findViewById(R.id.imageViewGambar);
        }
    }
}
