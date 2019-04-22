package mx.itson.cruddemo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    List<Prueba2> list = new ArrayList<>();
    ItemClickListener itemClickListener;

    public MyAdapter(List<Prueba2> list) {
        this.list = list;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row2,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        final Prueba2 prueba2 = list.get(position);

        holder.tv_nombre.setText(prueba2.getNombre());
        holder.tv_fecha.setText(prueba2.getFecha());
        holder.tv_distancia.setText(prueba2.getDistancia());
        holder.tv_vueltas.setText(prueba2.getVueltas());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.OnItemClick(position,prueba2);
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre, tv_fecha, tv_distancia, tv_vueltas, tv_delete;

        public MyHolder(View itemView) {
            super(itemView);

            tv_nombre = itemView.findViewById(R.id.tv_nombre_item);
            tv_fecha = itemView.findViewById(R.id.tv_fecha_item);
            tv_distancia = itemView.findViewById(R.id.tv_distancia_item);
            tv_vueltas = itemView.findViewById(R.id.tv_vueltas_item);
            tv_delete = itemView.findViewById(R.id.tv_delete_item);
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void UpdateData(int position,Prueba2 prueba2){

        list.remove(position);
        list.add(prueba2);
        notifyItemChanged(position);
        notifyDataSetChanged();
    }
}




