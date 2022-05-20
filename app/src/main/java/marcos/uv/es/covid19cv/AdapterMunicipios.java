package marcos.uv.es.covid19cv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class AdapterMunicipios extends RecyclerView.Adapter<AdapterMunicipios.ViewHolder> implements Filterable {
    private static ArrayList<Municipio> municipios;
    private static ArrayList<Municipio> municipiosFiltered;
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

    public static void setMunicipios(ArrayList<Municipio> municipios) {
        AdapterMunicipios.municipios = municipios;
        AdapterMunicipios.municipiosFiltered = new ArrayList<>(municipios);
    }

    public void AlphabeticSort(){
        Collections.sort(municipios, new AlphabeticComparator());
    }

    public void IncidenceSort(){
        Collections.sort(municipios, new IncidenceComparator());
    }

    public void reverseAlphabeticSort(){
        Collections.sort(municipios, Collections.reverseOrder(new AlphabeticComparator()));
    }

    public void reverseIncidenceSort(){
        Collections.sort(municipios, Collections.reverseOrder(new IncidenceComparator()));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<Municipio> filteredList = new ArrayList<>();
                String charString = charSequence.toString();
                if (charString == null || charString.length() == 0) {
                    filteredList = municipiosFiltered;
                } else {
                    for (Municipio row : municipiosFiltered) {
                        if (row.getMunicipio().toLowerCase().contains(charString.toLowerCase())) {;
                            filteredList.add(row);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //municipios.clear();
                municipios = (ArrayList<Municipio>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public class AlphabeticComparator implements Comparator<Municipio>{
        @Override
        public int compare(Municipio m1, Municipio m2) {
            return m1.getMunicipio().compareToIgnoreCase(m2.getMunicipio());
        }
    }

    public class IncidenceComparator implements Comparator<Municipio>{
        @Override
        public int compare(Municipio m1, Municipio m2) {
            return m1.getIncidencia14dias().compareTo(m2.getIncidencia14dias());
        }
    }

    public void Init() {
        municipios = new ArrayList<Municipio>();
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
        private final LinearLayout cardView;
        //private ItemClickListener mClickListener;

        public ViewHolder(View view) {
            super(view);
            municipalityName = (TextView) view.findViewById(R.id.nameMunicipality);
            codigoPostal = (TextView) view.findViewById(R.id.codigoPostal);
            cardView = (LinearLayout) view.findViewById(R.id.card);
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
            intent.putExtra("incidenciaPCR", String.valueOf(municipios.get(position).getIncidencia()));
            intent.putExtra("casosPCR14dias", String.valueOf(municipios.get(position).getCasosPcr14dias()));
            intent.putExtra("incidenciaPCR14dias", String.valueOf(municipios.get(position).getIncidencia14dias()));
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
        //if(municipiosFiltered != null && municipiosFiltered.size() != 0) {
            holder.cardView.setTag(position);
            Municipio municipio = municipios.get(position);

            if (municipio.getIncidencia14dias() > 1500)
                holder.cardView.setBackground(ContextCompat.getDrawable(context,R.drawable.high_color));
            else if (municipio.getIncidencia14dias() > 1000)
                holder.cardView.setBackground(ContextCompat.getDrawable(context,R.drawable.mid_color));
            else
                holder.cardView.setBackground(ContextCompat.getDrawable(context,R.drawable.low_color));


            holder.municipalityName.setText(municipio.getMunicipio());
            holder.codigoPostal.setText("CP: " + String.valueOf(municipio.getCodMunicipio()));
        //}
    }

}
