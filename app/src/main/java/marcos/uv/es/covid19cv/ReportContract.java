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
        public static final String SYMPTOM_FEVER_OR_CHILLS = "symptomFeverOrChills";
        public static final String SYMPTOM_COUGH = "symptomCough";
        public static final String SYMPTOM_DIFICULTY_BREATHING = "symptomDificultyBreathing";
        public static final String SYMPTOM_FATIGUE = "symptomFatigue";
        public static final String SYMPTOM_MUSCLE_OR_BODY_ACHES = "symptomMuscleOrBodyAches";
        public static final String SYMPTOM_HEADACHE = "symptomHeadache";
        public static final String SYMPTOM_NEW_LOSS_OF_TASTE_OR_SMELL = "symptomNewLossOfTasteOrSmell";
        public static final String SYMPTOM_SORE_THROAT = "symptomSoreThroat";
        public static final String SYMPTOM_CONGESTION_OR_RUNNY_NOSE = "symptomCongestionOrRunnyNose";
        public static final String SYMPTOM_NAUSEA_OR_VOMITING = "symptomNauseaOrVomiting";
        public static final String SYMPTOM_DIARRHEA = "symptomDiarrhea";
        public static final String CONTACT = "contact";
        public static final String MUNICIPALITY = "municipality";
    }

}
