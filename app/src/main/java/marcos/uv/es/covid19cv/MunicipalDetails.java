package marcos.uv.es.covid19cv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MunicipalDetails extends AppCompatActivity {
    TextView municipalityName;
    TextView casosPCR;
    TextView incidenciaPCR;
    TextView casosPCR14dias;
    TextView incidenciaPCR14dias;
    TextView defunciones;
    TextView tasaDefuncion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipal_details);

        Intent intent = getIntent();
        municipalityName = findViewById(R.id.municipalityName);
        casosPCR = findViewById(R.id.casosPCR);
        incidenciaPCR = findViewById(R.id.incidenciaPCR);
        casosPCR14dias = findViewById(R.id.casosPCR14dias);
        incidenciaPCR14dias = findViewById(R.id.incidenciaPCR14dias);
        defunciones = findViewById(R.id.defunciones);
        tasaDefuncion = findViewById(R.id.tasaDefuncion);

        municipalityName.append(intent.getStringExtra("municipio"));
        casosPCR.append(intent.getStringExtra("casosPCR"));
        incidenciaPCR.append(intent.getStringExtra("incidenciaPCR"));
        casosPCR14dias.append(intent.getStringExtra("casosPCR14dias"));
        incidenciaPCR14dias.append(intent.getStringExtra("incidenciaPCR14dias"));
        defunciones.append(intent.getStringExtra("defunciones"));
        tasaDefuncion.append(intent.getStringExtra("tasaDefuncion"));

        Toast.makeText(this, municipalityName.getText() + " seleccionado", Toast.LENGTH_SHORT).show();

        ReportDbHelper db = new ReportDbHelper(getApplicationContext());
        Cursor reportsByMunicipality = db.FindReportsByMunicipality(intent.getStringExtra("municipio"));

        RecyclerView recyclerView = findViewById(R.id.reportlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterReport reportsAdapter = new AdapterReport(this, reportsByMunicipality, 0);
        recyclerView.setAdapter(reportsAdapter);

        /*RecyclerView recyclerView2 = findViewById(R.id.reportlist);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        AdapterMunicipios adapter = new AdapterMunicipios(this);
        recyclerView2.setAdapter(adapter);*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent report = new Intent(MunicipalDetails.this,ReportActivity.class);
                report.putExtra("municipio", String.valueOf(intent.getStringExtra("municipio")));
                MunicipalDetails.super.finish();
                startActivity(report);
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

                Uri localization = Uri.parse( "https://www.google.com/maps/place/"+ String.valueOf(municipalityName.getText()).replace(" ","+") + "/" );
                startActivity( new Intent( Intent.ACTION_VIEW, localization ) );
                Toast.makeText(this, "Abriendo maps", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}