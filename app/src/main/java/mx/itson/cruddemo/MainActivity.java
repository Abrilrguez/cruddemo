package mx.itson.cruddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText et_nombre, et_fecha, et_distancia, et_vueltas,  et_update_nombre,  et_update_fecha,  et_update_distancia,  et_update_vueltas;
    Button btnFecha, btn_update_user, btn_update_cancel, add;
    RecyclerView recyclerView;
    MyAdapter adapter;

    List<Prueba2> list = new ArrayList<>();

    AlertDialog.Builder builder;

    AlertDialog dialog;

    String nombre,fecha,distancia, vueltas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_fecha = (EditText) findViewById(R.id.et_fecha);
        et_distancia = (EditText) findViewById(R.id.et_distancia);
        et_vueltas = (EditText) findViewById(R.id.et_vueltas);

        add = (Button) findViewById(R.id.btn_add);
        btnFecha = (Button) findViewById(R.id.btnFecha);
        btn_update_user = (Button) findViewById(R.id.btn_update_user);
        btn_update_cancel = (Button) findViewById(R.id.btn_update_cancel);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = et_nombre.getText().toString();
                fecha = et_fecha.getText().toString();
                distancia = et_distancia.getText().toString();
                vueltas = et_vueltas.getText().toString();

                Prueba2 prueba2 = new Prueba2();

                prueba2.setNombre(nombre);
                prueba2.setFecha(fecha);
                prueba2.setDistancia(distancia);
                prueba2.setVueltas(vueltas);

                list.add(prueba2);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"User Add Success...",Toast.LENGTH_SHORT).show();

                et_nombre.setText("");
                et_fecha.setText("");
                et_distancia.setText("");
                et_vueltas.setText("");

            }
        });

        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(int position, Prueba2 prueba2) {

                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Update User Info");
                builder.setCancelable(false);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_update2,null,false);
                InitUpdateDialog(position,view);
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void InitUpdateDialog(final int position, View view) {

        et_update_nombre = view.findViewById(R.id.et_update_nombre);
        et_update_fecha = view.findViewById(R.id.et_update_fecha);
        et_update_distancia = view.findViewById(R.id.et_update_distancia);
        et_update_vueltas = view.findViewById(R.id.et_update_vueltas);

        btn_update_user = view.findViewById(R.id.btn_update_user);
        btn_update_cancel = view.findViewById(R.id.btn_update_cancel);

        et_update_nombre.setText(nombre);
        et_update_fecha.setText(fecha);
        et_update_distancia.setText(distancia);
        et_update_vueltas.setText(vueltas);


        btn_update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = et_update_nombre.getText().toString();
                fecha = et_update_fecha.getText().toString();
                distancia = et_update_distancia.getText().toString();
                vueltas = et_update_vueltas.getText().toString();

                Prueba2 prueba2 = new Prueba2();

                prueba2.setNombre(nombre);
                prueba2.setFecha(fecha);
                prueba2.setDistancia(distancia);
                prueba2.setVueltas(vueltas);

                adapter.UpdateData(position,prueba2);
                Toast.makeText(MainActivity.this,"User Updated..",Toast.LENGTH_SHORT).show();

            }
        });
        btn_update_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
