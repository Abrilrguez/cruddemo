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
import android.widget.ProgressBar;
import android.widget.Toast;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener {

    EditText et_nombre, et_fecha, et_distancia, et_vueltas,  et_update_nombre,  et_update_fecha,  et_update_distancia,  et_update_vueltas;
    Button btnFecha, btn_update_user, btn_update_cancel, btn_add;

    //Crear la lista de las pruebas
    private List<Prueba2> pruebas;

    //Crer views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    //Volley Rquest Queue
    private RequestQueue requestQueue;

    //The request counter to send ?page=1, ?page=2 requests
    private int requestCount =1;

    private static String URL_REGIST = "http://potrosport.pitalla.mx/registrarPrueba.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_fecha = (EditText) findViewById(R.id.et_fecha);
        et_distancia = (EditText) findViewById(R.id.et_distancia);
        et_vueltas = (EditText) findViewById(R.id.et_vueltas);

        btn_add = (Button) findViewById(R.id.btn_add);
        btnFecha = (Button) findViewById(R.id.btnFecha);
        btn_update_user = (Button) findViewById(R.id.btn_update_user);
        btn_update_cancel = (Button) findViewById(R.id.btn_update_cancel);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Incializar la lista
        pruebas = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        //Calling method to get  data to fetch data
        getData();

        //Adding an scroll change listener to reclycleview
        recyclerView.setOnScrollChangeListener(this);

        //inicializar adapter
        adapter = new RecyclerViewAdapter(pruebas, this);

        //anadir adaptador al recycleview
        recyclerView.setAdapter(adapter);
    }

    //Request para obtener json del servidor

    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(MainActivity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Prueba2 prueba2 = new Prueba2();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                prueba2.setNombre(json.getString(Config.TAG_ALUMNO));
                prueba2.setFecha(json.getString(Config.TAG_FECHA));
                prueba2.setDistancia(json.getString(Config.TAG_DISTANCIA));
                prueba2.setVueltas(json.getString(Config.TAG_VUELTA));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            pruebas.add(prueba2);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    //Overriden method to detect scrolling
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again
            getData();
        }
    }


    private void Registrar() {
        btn_add.setVisibility(View.GONE);

        final String alumno = this.et_nombre.getText().toString().trim();
        final String fecha = this.et_fecha.getText().toString().trim();
        final String distancia = this.et_distancia.getText().toString().trim();
        final String vuelta = this.et_vueltas.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");

                            if (succes.equals("1")) {
                                Toast.makeText(MainActivity.this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error al registrar!" + e.toString(), Toast.LENGTH_SHORT).show();
                            btn_add.setVisibility(View.VISIBLE);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("alumno", alumno);
                params.put("fecha", fecha);
                params.put("distancia", distancia);
                params.put("vuelta", vuelta);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
