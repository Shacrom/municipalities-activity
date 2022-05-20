package marcos.uv.es.covid19cv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Report.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ReportContract.ReportEntry.TABLE_NAME + " (" +
                    ReportContract.ReportEntry._ID + " INTEGER PRIMARY KEY," +
                    ReportContract.ReportEntry.DIAGNOSTIC_CODE + " TEXT," +
                    ReportContract.ReportEntry.SYMPTOM_START_DATE + " DATE," +
                    ReportContract.ReportEntry.SYMPTOMS[0] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[1] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[2] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[3] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[4] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[5] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[6] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[7] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[8] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[9] + " BOOLEAN, " +
                    ReportContract.ReportEntry.SYMPTOMS[10] + " BOOLEAN, " +
                    ReportContract.ReportEntry.CONTACT + " BOOLEAN, " +
                    ReportContract.ReportEntry.MUNICIPALITY + " TEXT)";



    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReportContract.ReportEntry.TABLE_NAME;

    public ReportDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public Cursor InsertReport(Report newReport){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReportContract.ReportEntry.DIAGNOSTIC_CODE,newReport.getIDCode());
        values.put(ReportContract.ReportEntry.SYMPTOM_START_DATE,String.valueOf(newReport.getStartSyn()));

        for (int i = 0; i < newReport.getListSyn().size(); i++)
            values.put(ReportContract.ReportEntry.SYMPTOMS[i],newReport.getListSyn().get(i).isSelected());

        values.put(ReportContract.ReportEntry.CONTACT,newReport.isContact());
        values.put(ReportContract.ReportEntry.MUNICIPALITY,newReport.getMunipality());

        long newRowId = db.insert(ReportContract.ReportEntry.TABLE_NAME,null,values);

        return null;
    }

    public int UpdateReport(Report reportUpdate){
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        ContentValues updateReport = new ContentValues();
        updateReport.put(ReportContract.ReportEntry.DIAGNOSTIC_CODE, reportUpdate.getIDCode());
        updateReport.put(ReportContract.ReportEntry.SYMPTOM_START_DATE, reportUpdate.getStartSyn());

        for (int i = 0; i < reportUpdate.getListSyn().size(); i++)
            updateReport.put(ReportContract.ReportEntry.SYMPTOMS[i],reportUpdate.getListSyn().get(i).isSelected());

        updateReport.put(ReportContract.ReportEntry.CONTACT, reportUpdate.isContact());
        updateReport.put(ReportContract.ReportEntry.MUNICIPALITY, reportUpdate.getMunipality());

// Filter results WHERE "title" = 'My Title'
        String selection = ReportContract.ReportEntry.DIAGNOSTIC_CODE + " = ?";
        String[] selectionArgs = {reportUpdate.getIDCode()};


        int count = db.update(
                ReportContract.ReportEntry.TABLE_NAME,   // The table to query
                updateReport,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs
        );

        return count;
    }
    public int DeleteReport(String codeID){
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = ReportContract.ReportEntry.DIAGNOSTIC_CODE + " = ?";
        String[] selectionArgs = {codeID};


        int count = db.delete(
                ReportContract.ReportEntry.TABLE_NAME,   // The table to query
                selection,              // The columns for the WHERE clause
                selectionArgs
        );

        return count;
    }

    public Cursor FindReportsByMunicipality(String municipalityName ){
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.

// Filter results WHERE "title" = 'My Title'
        String selection = ReportContract.ReportEntry.MUNICIPALITY + " = ?";
        String[] selectionArgs = {municipalityName};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                ReportContract.ReportEntry.MUNICIPALITY + " DESC";

        Cursor cursor = db.query(
                ReportContract.ReportEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;
    }
}
