package marcos.uv.es.covid19cv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class ReportActivity extends AppCompatActivity {

    private TextView municipalityName, codeID, dateSyn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private GridView gd;
    private GridViewAdapter mAdapter;
    private Button saveReport;
    private int checkNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        //Spinner spinner = findViewById(R.id.planets_spinner);
        final String[] select_planets =
                getApplicationContext().getResources().getStringArray(R.array.sintomas);
        ArrayList<SymtomModel> categoryModelArrayList = new ArrayList<>();

        for (String s : select_planets) {
            SymtomModel categoryModel = new SymtomModel();
            categoryModel.setTitle(s);
            categoryModel.setSelected(false);
            categoryModelArrayList.add(categoryModel);
        }

        /*AdapterListSpinner myAdapter = new AdapterListSpinner(ReportActivity.this, 0, categoryModelArrayList);
        spinner.setAdapter(myAdapter);

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        Map<String, ?> category = sharedPreferences.getAll();
        Toast.makeText(getApplicationContext(), "" + category, Toast.LENGTH_LONG).show();
*/
        Intent intent = getIntent();
        municipalityName = findViewById(R.id.municipality);
        codeID = findViewById(R.id.diagnosticCode);
        dateSyn = findViewById(R.id.symptomDate);

        codeID.setText(UUID.randomUUID().toString());

        try {
            municipalityName.append(intent.getStringExtra("municipio"));
        }catch (Exception e){
            System.out.println("No municipio");
        }

        gd = (GridView) findViewById(R.id.gridView);
        mAdapter = new GridViewAdapter(categoryModelArrayList,this);
        gd.setAdapter(mAdapter);
        gd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridViewAdapter.ViewHolder holder = (GridViewAdapter.ViewHolder) view.getTag();
                holder.cb.toggle();
                GridViewAdapter.getIsSelected().put(i,holder.cb.isChecked());
                if (holder.cb.isChecked() == true) {
                    checkNum++;
                    Toast.makeText(ReportActivity.this, "Sintoma seleccionado: " + holder.cb.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReportActivity.this, "Sintoma desseleccionado: " + holder.cb.getText(), Toast.LENGTH_SHORT).show();
                    checkNum--;

                }
            }
        });

        saveReport = (Button) findViewById(R.id.newReport);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        saveReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectId = radioGroup.getCheckedRadioButtonId();
                Date date;
                Report newReport;
                radioButton = (RadioButton) findViewById(selectId);
                try {
                    date = new SimpleDateFormat("dd-MM-yyyy").parse(String.valueOf(dateSyn.getText()));
                    if(radioButton.getText().equals("Yes"))
                        newReport = new Report(String.valueOf(codeID.getText()), date ,mAdapter.getList(),true,String.valueOf(municipalityName.getText()));
                    else
                        newReport = new Report(String.valueOf(codeID.getText()), date ,mAdapter.getList(),false,String.valueOf(municipalityName.getText()));

                    Toast.makeText(ReportActivity.this, "Nuevo reporte guardado\n" + newReport, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(ReportActivity.this, "Nuevo reporte fallo \n", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.yesButton:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.noButton:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

}