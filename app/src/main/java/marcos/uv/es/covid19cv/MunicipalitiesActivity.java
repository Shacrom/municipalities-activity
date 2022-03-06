package marcos.uv.es.covid19cv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MunicipalitiesActivity extends AppCompatActivity {

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            // Implement the listener!
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterMunicipios adapter = new AdapterMunicipios(this);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(adapter);
    }



}

