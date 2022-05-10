package marcos.uv.es.covid19cv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class AdapterMunicipios extends RecyclerView.Adapter<AdapterMunicipios.ViewHolder> {
    private static ArrayList<Municipio> municipios;
    static Context context;
    //private ItemClickListener mClickListener;
    //private View.OnClickListener mOnItemClickListener;

    public AdapterMunicipios(ArrayList<Municipio> municipios) {
        this.municipios = municipios;
    }

    public AdapterMunicipios(Context c)
    {
        context=c;
        Init();
    }

    public void Init() {
        municipios = new ArrayList<Municipio>();
        InputStream is = context.getResources().openRawResource(R.raw.municipios_cv);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return municipios.size();
    }

    public ArrayList<Municipio> getMunicipios() {
        return municipios;
    }

    public Municipio getItemAtPosition(int position) {
        return municipios.get(position);
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView municipalityName;
        private final TextView codigoPostal;
        //private ItemClickListener mClickListener;

        public ViewHolder(View view) {
            super(view);
            municipalityName = (TextView) view.findViewById(R.id.nameMunicipality);
            codigoPostal = (TextView) view.findViewById(R.id.codigoPostal);
            // Put this line in the code of the ViewHolder constructor
            view.setTag(this);
            // Put this line in the code of the ViewHolder constructor
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context,MunicipalDetails.class);
            intent.putExtra("municipio", municipios.get(position).getMunicipio());
            intent.putExtra("casosPCR", String.valueOf(municipios.get(position).getCasosPCR()));
            intent.putExtra("incidenciaPCR", municipios.get(position).getIncidencia());
            intent.putExtra("casosPCR14dias", String.valueOf(municipios.get(position).getCasosPcr14dias()));
            intent.putExtra("incidenciaPCR14dias", municipios.get(position).getIncidencia14dias());
            intent.putExtra("defunciones", String.valueOf(municipios.get(position).getDefunciones()));
            intent.putExtra("tasaDefuncion", municipios.get(position).getTasaDefuncion());
            context.startActivity(intent);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_recycle, viewGroup, false);

        // Put this line in the code of the onCreateViewHolder method
        //view.setOnClickListener(mOnItemClickListener);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.municipalityName.setText(municipios.get(position).getMunicipio());
        holder.codigoPostal.setText("CP: " + String.valueOf(municipios.get(position).getCodMunicipio()));
    }

}
