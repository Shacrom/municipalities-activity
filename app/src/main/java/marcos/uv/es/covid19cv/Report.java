package marcos.uv.es.covid19cv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Report {
    private String IDCode;
    private String startSyn;
    private ArrayList<SymtomModel> listSyn;
    private boolean contact;
    private String munipality;

    public Report(String IDCode, String startSyn, ArrayList<SymtomModel> listSyn, boolean contact, String munipality) {
        this.IDCode = IDCode;
        this.startSyn = startSyn;
        this.listSyn = listSyn;
        this.contact = contact;
        this.munipality = munipality;
    }

    public String getIDCode() {
        return IDCode;
    }

    public void setIDCode(String IDCode) {
        this.IDCode = IDCode;
    }

    public String getStartSyn() {
        return startSyn;
    }

    public void setStartSyn(String startSyn) {
        this.startSyn = startSyn;
    }

    public ArrayList<SymtomModel> getListSyn() {
        return listSyn;
    }

    public void setListSyn(ArrayList<SymtomModel> listSyn) {
        this.listSyn = listSyn;
    }

    public boolean isContact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }

    public String getMunipality() {
        return munipality;
    }

    public void setMunipality(String munipality) {
        this.munipality = munipality;
    }

    @Override
    public String toString() {
        String report;

        report = "Start symtom: " + startSyn + "\n";

        report += "Symtoms: " + "\n";

        for(int i = 0; i< listSyn.size(); i++) {
            if (listSyn.get(i).isSelected())
                report += listSyn.get(i).getTitle() + "\n";
        }

        if(contact)
            report += "Contact: Yes\n";
        else
            report += "Contact: No\n";

        report+= "Municipality: " + munipality + "\n";


        return report;
    }
}
