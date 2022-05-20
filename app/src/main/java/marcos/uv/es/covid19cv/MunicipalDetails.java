package marcos.uv.es.covid19cv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.accessibility.AccessibilityViewCommand;
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
    private TextView casosPCR;
    private TextView incidenciaPCR;
    private TextView casosPCR14dias;
    private TextView incidenciaPCR14dias;
    private TextView defunciones;
    private TextView tasaDefuncion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipal_details);

        Intent intent = getIntent();
        casosPCR = findViewById(R.id.casosPCR);
        incidenciaPCR = findViewById(R.id.incidenciaPCR);
        casosPCR14dias = findViewById(R.id.casosPCR14dias);
        incidenciaPCR14dias = findViewById(R.id.incidenciaPCR14dias);
        defunciones = findViewById(R.id.defunciones);
        tasaDefuncion = findViewById(R.id.tasaDefuncion);

        this.setTitle(intent.getStringExtra("municipio"));

        casosPCR.append(intent.getStringExtra("casosPCR"));
        incidenciaPCR.append(intent.getStringExtra("incidenciaPCR"));
        casosPCR14dias.append(intent.getStringExtra("casosPCR14dias"));
        incidenciaPCR14dias.append(intent.getStringExtra("incidenciaPCR14dias"));
        defunciones.append(intent.getStringExtra("defunciones"));
        tasaDefuncion.append(intent.getStringExtra("tasaDefuncion"));

        ReportDbHelper db = new ReportDbHelper(getApplicationContext());
        Cursor reportsByMunicipality = db.FindReportsByMunicipality(intent.getStringExtra("municipio"));

        RecyclerView recyclerView = findViewById(R.id.reportlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterReport reportsAdapter = new AdapterReport(this, reportsByMunicipality, 0);
        recyclerView.setAdapter(reportsAdapter);

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
        menu.findItem(R.id.open_page).setVisible(false);
        menu.findItem(R.id.app_bar_search).setVisible(false);
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

                Uri localization = Uri.parse( "https://www.google.com/maps/place/"+ String.valueOf(getIntent().getStringExtra("municipio")).replace(" ","+") + "/" );
                startActivity( new Intent( Intent.ACTION_VIEW, localization ) );
                Toast.makeText(this, "Abriendo maps", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}