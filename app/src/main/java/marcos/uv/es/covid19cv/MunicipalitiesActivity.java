package marcos.uv.es.covid19cv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MunicipalitiesActivity extends AppCompatActivity {
    private HTTPConnector connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (connection == null || connection.getStatus() != AsyncTask.Status.RUNNING) {
            connection = new HTTPConnector();
            connection.execute();
        }

        /*RecyclerView recyclerView = findViewById(R.id.rlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterMunicipios adapter = new AdapterMunicipios(this);
        recyclerView.setAdapter(adapter);*/

        FloatingActionButton fab = findViewById(R.id.fbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MunicipalitiesActivity.this, ReportActivity.class));
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.open_page:
                // Do something when the user clicks on the new game
                Uri uri = Uri.parse( "https://www.twitter.com" );
                startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
                Toast.makeText(this, "Abriendo navegador", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.localization:
                Uri localization = Uri.parse( "https://www.google.com/maps/" );
                startActivity( new Intent( Intent.ACTION_VIEW, localization ) );
                Toast.makeText(this, "Abriendo maps", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class HTTPConnector  extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList municipios=new ArrayList<Municipio>();
            //Perform the request and get the answer

            String url = "https://dadesobertes.gva.es/es/api/3/action/datastore_search?resource_id=7fd9a2bf-ffee-4604-907e-643a8009b04e&limit=1000";
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                con.setRequestProperty("accept", "application/json;");
                con.setRequestProperty("accept-language", "es");
                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }

                Reader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }

                JSONArray jsonArray = (JSONArray) new JSONObject(writer.toString()).getJSONObject("result").getJSONArray("records");

                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("_id");
                    int codMunicipio = jsonObject.getInt("CodMunicipio");
                    String municipi = jsonObject.getString("Municipi");
                    int casosPCR = jsonObject.getInt("Casos PCR+");
                    String incidencia = jsonObject.getString("Incidència acumulada PCR+");
                    int casosPcr14dias = jsonObject.getInt("Casos PCR+ 14 dies");
                    String incidencia14dias = jsonObject.getString("Incidència acumulada PCR+14");
                    int defunciones = jsonObject.getInt("Defuncions");
                    String tasaDefuncion = jsonObject.getString("Taxa de defunció");

                    Municipio municipio = new Municipio(id, codMunicipio, municipi, casosPCR, incidencia, casosPcr14dias, incidencia14dias, defunciones, tasaDefuncion);
                    municipios.add(municipio);
                }

                reader.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            return municipios;
        }

        @Override
        protected void onPostExecute(ArrayList municipios) {
            // Create the RecyclerView

            RecyclerView recyclerView = findViewById(R.id.rlist);
            recyclerView.setLayoutManager(new LinearLayoutManager(MunicipalitiesActivity.this));
            AdapterMunicipios adapter = new AdapterMunicipios(MunicipalitiesActivity.this);
            adapter.setMunicipios(municipios);
            recyclerView.setAdapter(adapter);
        }
    }






}

