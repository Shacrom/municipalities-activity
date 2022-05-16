package marcos.uv.es.covid19cv;

import android.provider.BaseColumns;

public final class ReportContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ReportContract() {}

    /* Inner class that defines the table contents */
    public static class ReportEntry implements BaseColumns {
        public static final String TABLE_NAME = "report";
        public static final String DIAGNOSTIC_CODE = "diagnosticCode";
        public static final String SYMPTOM_START_DATE = "symptomStartDate";
        public static final String CONTACT = "contact";
        public static final String MUNICIPALITY = "municipality";
        public static final String [] SYMPTOMS = {
                "symptomFeverOrChills",
                "symptomCough",
                "symptomDificultyBreathing",
                "symptomFatigue",
                "symptomMuscleOrBodyAches",
                "symptomHeadache",
                "symptomNewLossOfTasteOrSmell",
                "symptomSoreThroat",
                "symptomCongestionOrRunnyNose",
                "symptomNauseaOrVomiting",
                "symptomDiarrhea",
        };
    }

}
