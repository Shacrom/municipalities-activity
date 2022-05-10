package marcos.uv.es.covid19cv;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReportDetails extends AppCompatActivity {
    TextView municipalityName;
    TextView startSyn;
    TextView contact;
    TextView IDCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        Intent intent = getIntent();
        municipalityName = findViewById(R.id.reportMunicipalityName);
        startSyn = findViewById(R.id.reportStartSyn);
        contact = findViewById(R.id.reportContact);
        IDCode = findViewById(R.id.reportIDCODE);

        municipalityName.append(intent.getStringExtra("munipality"));
        startSyn.append(intent.getStringExtra("startSyn"));
        contact.append(intent.getStringExtra("contact"));
        IDCode.append(intent.getStringExtra("IDCode"));

        Toast.makeText(this, municipalityName.getText() + " seleccionado", Toast.LENGTH_SHORT).show();

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
