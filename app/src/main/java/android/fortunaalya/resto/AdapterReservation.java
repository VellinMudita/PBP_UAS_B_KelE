package android.fortunaalya.resto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterReservation extends RecyclerView.Adapter<AdapterReservation.ViewHolder> {

    private List<GetSetReservation> itemLists;
    private Context context;

    public AdapterReservation(List<GetSetReservation> itemLists, Context context){
        this.itemLists = itemLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_reservation, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetSetReservation getSetMenu = itemLists.get(position);

        Locale localeID = new Locale("in", "ID");
        holder.tvMeja.setText(getSetMenu.getNo_meja());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime datetime = LocalDateTime.parse(getSetMenu.getTanggal_mulai(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime datetime2 = LocalDateTime.parse(getSetMenu.getTanggal_selesai(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String newstring = datetime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
            String newstring2 = datetime2.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
            holder.tvAwal.setText(newstring);
            holder.tvAkhir.setText(newstring2);
        }else{
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getSetMenu.getTanggal_mulai());
                Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getSetMenu.getTanggal_selesai());
                String newstring = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", localeID).format(date);
                String newstring2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", localeID).format(date2);
                holder.tvAwal.setText(newstring);
                holder.tvAkhir.setText(newstring2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMeja, tvAwal, tvAkhir;

        public ViewHolder(View view){
            super(view);

            tvMeja = view.findViewById(R.id.tvMeja);
            tvAwal = view.findViewById(R.id.tvAwal);
            tvAkhir = view.findViewById(R.id.tvAkhir);
        }
    }
}
