package marcos.uv.es.covid19cv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {

    private TextView municipalityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Spinner spinner = findViewById(R.id.planets_spinner);
        final String[] select_planets =
                getApplicationContext().getResources().getStringArray(R.array.sintomas);
        ArrayList<SymtomModel> categoryModelArrayList = new ArrayList<>();

        for (String s : select_planets) {
            SymtomModel categoryModel = new SymtomModel();
            categoryModel.setTitle(s);
            categoryModel.setSelected(false);
            categoryModelArrayList.add(categoryModel);
        }

        AdapterListSpinner myAdapter = new AdapterListSpinner(ReportActivity.this, 0, categoryModelArrayList);
        spinner.setAdapter(myAdapter);

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        Map<String, ?> category = sharedPreferences.getAll();
        Toast.makeText(getApplicationContext(), "" + category, Toast.LENGTH_LONG).show();

        Intent intent = getIntent();
        municipalityName = findViewById(R.id.municipality);

        try {
            municipalityName.append(intent.getStringExtra("municipio"));
        }catch (Exception e){
            System.out.println("No municipio");
        }


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