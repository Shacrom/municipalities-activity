package marcos.uv.es.covid19cv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ReportDB extends SQLiteOpenHelper {
    public ReportDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor InsertReport(){
        return null;
    }
    public Cursor UpdateReport(){
        return null;
    }
    public Cursor DeleteReport(){
        return null;
    }
    public Cursor FindReportsByMunicipality(){
        return null;
    }
}
