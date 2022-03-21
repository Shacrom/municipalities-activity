package marcos.uv.es.covid19cv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MunicipalDetails extends AppCompatActivity {
    TextView municipalityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipal_details);

        Intent intent = getIntent();
        municipalityName = findViewById(R.id.municipalityName);
        municipalityName.append(intent.getStringExtra("municipio"));

        Toast.makeText(this, municipalityName.getText() + " seleccionado", Toast.LENGTH_SHORT).show();
    }
}