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
        final String[] select_planets =
                getApplicationContext().getResources().getStringArray(R.array.sintomas);
        ArrayList<SymtomModel> categoryModelArrayList = new ArrayList<>();

        for (String s : select_planets) {
            SymtomModel categoryModel = new SymtomModel();
            categoryModel.setTitle(s);
            categoryModel.setSelected(false);
            categoryModelArrayList.add(categoryModel);
        }

        Intent intent = getIntent();
        municipalityName = findViewById(R.id.municipality);
        codeID = findViewById(R.id.diagnosticCode);
        dateSyn = findViewById(R.id.symptomDate);

        try {
            municipalityName.append(intent.getStringExtra("municipio"));
            codeID.append(intent.getStringExtra("IDCode"));
            dateSyn.append(intent.getStringExtra("startSyn"));

            if(intent.getStringExtra("contact").equals("yes"))
                radioButton = (RadioButton) findViewById(R.id.yesButton);
            else
                radioButton = (RadioButton) findViewById(R.id.noButton);

            radioButton.setChecked(true);

        }catch (Exception e){
            System.out.println("No municipio o fecha");
            codeID.setText(UUID.randomUUID().toString());

        }

        gd = (GridView) findViewById(R.id.gridView);
        mAdapter = new GridViewAdapter(categoryModelArrayList,this);
        gd.setAdapter(mAdapter);
        gd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*GridViewAdapter.ViewHolder holder = (GridViewAdapter.ViewHolder) view.getTag();
                holder.cb.toggle();
                GridViewAdapter.getIsSelected().put(i,holder.cb.isChecked());
                if (holder.cb.isChecked() == true) {
                    checkNum++;
                    Toast.makeText(ReportActivity.this, "Sintoma seleccionado: " + holder.cb.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReportActivity.this, "Sintoma desseleccionado: " + holder.cb.getText(), Toast.LENGTH_SHORT).show();
                    checkNum--;

                }*/
            }
        });

        saveReport = (Button) findViewById(R.id.newReport);
        radioGroup = (RadioGroup) findViewById(R.id.radio);

        ReportDbHelper db = new ReportDbHelper(getApplicationContext());
        saveReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectId = radioGroup.getCheckedRadioButtonId();
                Report newReport;
                radioButton = (RadioButton) findViewById(selectId);

                if(radioButton.getText().equals("Yes"))
                    newReport = new Report(String.valueOf(codeID.getText()), String.valueOf(dateSyn.getText()) ,mAdapter.getList(),true,String.valueOf(municipalityName.getText()));
                else
                    newReport = new Report(String.valueOf(codeID.getText()), String.valueOf(dateSyn.getText()) ,mAdapter.getList(),false,String.valueOf(municipalityName.getText()));

                db.InsertReport(db, newReport);

                Toast.makeText(ReportActivity.this, "Nuevo reporte guardado\n" + newReport, Toast.LENGTH_SHORT).show();



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