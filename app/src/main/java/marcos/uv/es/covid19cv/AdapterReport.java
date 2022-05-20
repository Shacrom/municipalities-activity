package marcos.uv.es.covid19cv;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.ViewHolder>{
    static Context context;
    private static ArrayList<Report> items = new ArrayList<>();

    public AdapterReport(Context c, Cursor reportsByMunicipality, int num) {
        context=c;
        Init(reportsByMunicipality);
    }

    private void Init(Cursor reportsByMunicipality) {
        final int codeIDIndex = reportsByMunicipality.getColumnIndex(ReportContract.ReportEntry.DIAGNOSTIC_CODE);
        final int dateIndex = reportsByMunicipality.getColumnIndex(ReportContract.ReportEntry.SYMPTOM_START_DATE);
        final int municipalityIndex = reportsByMunicipality.getColumnIndex(ReportContract.ReportEntry.MUNICIPALITY);
        final int contactIndex = reportsByMunicipality.getColumnIndex(ReportContract.ReportEntry.CONTACT);

        items.clear();

        while (reportsByMunicipality.moveToNext()){
            // Read the values of a row in the table using the indexes acquired above
            String diagnosticCode = reportsByMunicipality.getString(codeIDIndex);
            String name = reportsByMunicipality.getString(municipalityIndex);
            ArrayList<SymtomModel> symptoms = new ArrayList<SymtomModel>();
            String [] list_sintomas = context.getApplicationContext().getResources().getStringArray(R.array.sintomas);
            int j = 0;
            for(int i = reportsByMunicipality.getColumnIndex(ReportContract.ReportEntry.SYMPTOMS[0]);
                i <= reportsByMunicipality.getColumnIndex(ReportContract.ReportEntry.SYMPTOMS[ReportContract.ReportEntry.SYMPTOMS.length-1]);
                i++){
                symptoms.add(new SymtomModel(list_sintomas[j],reportsByMunicipality.getInt(i) > 0));
                j++;
            }
            boolean contact = reportsByMunicipality.getInt(contactIndex) > 0;
            String date = reportsByMunicipality.getString(dateIndex);

            items.add(new Report(diagnosticCode, date, symptoms,contact, name));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView diagnosticCode;
        private final TextView startSymptoms;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diagnosticCode = (TextView) itemView.findViewById(R.id.idCode);
            startSymptoms = (TextView) itemView.findViewById(R.id.startDate);
            itemView.setTag(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context,ReportActivity.class);
            intent.putExtra("municipio", items.get(position).getMunipality());
            intent.putExtra("startSyn", String.valueOf(items.get(position).getStartSyn()));
            if(items.get(position).isContact())
                intent.putExtra("contact", "yes");
            else
                intent.putExtra("contact", "no");
            intent.putExtra("IDCode", String.valueOf(items.get(position).getIDCode()));
            intent.putExtra("listSyn", items.get(position).getListSyn());
            context.startActivity(intent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_reports, viewGroup, false);

        // Put this line in the code of the onCreateViewHolder method
        return new ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull AdapterReport.ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        holder.diagnosticCode.setText(items.get(position).getIDCode());
        holder.startSymptoms.setText(items.get(position).getStartSyn());

    }
}
