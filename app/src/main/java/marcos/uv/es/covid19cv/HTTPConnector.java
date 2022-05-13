package marcos.uv.es.covid19cv;

import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

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

public class HTTPConnector  extends AsyncTask<String, Void, ArrayList> {
    @Override
    protected ArrayList doInBackground(String... params) {
        ArrayList municipios=new ArrayList<Municipio>();
        //Perform the request and get the answer

        String url = "https://dadesobertes.gva.es/es/api/3/action/datastore_search?resource_id=382b283c-03fa-433e9967-9e064e84f936&limit=1000";
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterMunicipios adapter = new AdapterMunicipios(this);
        recyclerView.setAdapter(adapter);
    }
}
