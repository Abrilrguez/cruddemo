package mx.itson.cruddemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.itson.cruddemo.Prueba2;
import mx.itson.cruddemo.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    private List<Prueba2> mData;

    public RecyclerViewAdapter(List<Prueba2> mData, Context mContext) {
        super();
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.item_row2, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {

        holder.tv_nombre.setText(mData.get(position).getNombre());
        holder.tv_fecha.setText(mData.get(position).getFecha());
        holder.tv_distancia.setText(mData.get(position).getDistancia());
        holder.tv_vueltas.setText(mData.get(position).getVueltas());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre, tv_fecha, tv_distancia, tv_vueltas, tv_delete;


        public MyViewHolder( View itemView) {
            super(itemView);

            tv_nombre = itemView.findViewById(R.id.tv_nombre_item);
            tv_fecha = itemView.findViewById(R.id.tv_fecha_item);
            tv_distancia = itemView.findViewById(R.id.tv_distancia_item);
            tv_vueltas = itemView.findViewById(R.id.tv_vueltas_item);
            tv_delete = itemView.findViewById(R.id.tv_delete_item);

        }
    }
}
