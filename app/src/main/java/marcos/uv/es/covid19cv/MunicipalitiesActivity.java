package marcos.uv.es.covid19cv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MunicipalitiesActivity extends AppCompatActivity {
    private HTTPConnector connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (connection == null || connection.getStatus() != AsyncTask.Status.RUNNING) {
            connection = new HTTPConnector();
            connection.execute();
        }

        /*RecyclerView recyclerView = findViewById(R.id.rlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterMunicipios adapter = new AdapterMunicipios(this);
        recyclerView.setAdapter(adapter);*/

        FloatingActionButton fab = findViewById(R.id.fbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MunicipalitiesActivity.this, ReportActivity.class));
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
                Uri localization = Uri.parse( "https://www.google.com/maps/" );
                startActivity( new Intent( Intent.ACTION_VIEW, localization ) );
                Toast.makeText(this, "Abriendo maps", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}

