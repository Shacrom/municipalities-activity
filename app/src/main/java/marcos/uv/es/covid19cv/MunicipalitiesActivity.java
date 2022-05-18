package marcos.uv.es.covid19cv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private RecyclerView recyclerView;
    private AdapterMunicipios adapter;
    private Spinner spinner;
    private ArrayAdapter arrayAdapter;
    private RadioGroup radioGroup;
    private SearchView searchView;

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
        menu.findItem(R.id.localization).setVisible(false);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.open_page:
                // Do something when the user clicks on the new game
                Uri uri = Uri.parse( "https://coronavirus.san.gva.es" );
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ascButton:
                if (checked) {
                    switch (spinner.getSelectedItem().toString()) {
                        case "Incidencia":
                            adapter.IncidenceSort();
                            break;
                        case "Alfabetico":
                            adapter.AlphabeticSort();
                            break;
                    }
                }
                break;
            case R.id.desButton:
                if (checked) {
                    switch (spinner.getSelectedItem().toString()){
                        case "Incidencia":
                            adapter.reverseIncidenceSort();
                            break;
                        case "Alfabetico":
                            adapter.reverseAlphabeticSort();
                            break;
                    }
                }
                break;
        }

        adapter.notifyDataSetChanged();
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
                    Float incidencia = Float.valueOf(
                            jsonObject.getString("Incidència acumulada PCR+").replaceAll(
                            "\\s","").replaceAll(
                            ",","."));
                    int casosPcr14dias = jsonObject.getInt("Casos PCR+ 14 dies");
                    Float incidencia14dias = Float.valueOf(
                            jsonObject.getString("Incidència acumulada PCR+14").replaceAll(
                            "\\s","").replaceAll(
                            ",","."));
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

            radioGroup = (RadioGroup) findViewById(R.id.orderDesAsc);

            recyclerView = findViewById(R.id.rlist);
            recyclerView.setLayoutManager(new LinearLayoutManager(MunicipalitiesActivity.this));
            adapter = new AdapterMunicipios(MunicipalitiesActivity.this);
            adapter.setMunicipios(municipios);
            recyclerView.setAdapter(adapter);

            spinner = (Spinner) findViewById(R.id.orderSpinner);
            arrayAdapter = ArrayAdapter.createFromResource(MunicipalitiesActivity.this,R.array.order, android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (adapterView.getItemAtPosition(i).toString()){
                        case "Incidencia":
                            switch(radioGroup.getCheckedRadioButtonId()) {
                                case R.id.ascButton:
                                    adapter.IncidenceSort();
                                    break;
                                case R.id.desButton:
                                    adapter.reverseIncidenceSort();
                                    break;
                            }
                            break;
                        case "Alfabetico":
                            switch(radioGroup.getCheckedRadioButtonId()) {
                                case R.id.ascButton:
                                    adapter.AlphabeticSort();
                                    break;
                                case R.id.desButton:
                                    adapter.reverseAlphabeticSort();
                                    break;
                            }
                            break;

                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });





        }

    }






}

