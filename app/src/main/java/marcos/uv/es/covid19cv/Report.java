package marcos.uv.es.covid19cv;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Report {
    private String IDCode;
    private Date startSyn;
    private ArrayList<SymtomModel> listSyn;
    private boolean contact;
    private String munipality;

    public Report(String IDCode, Date startSyn, ArrayList<SymtomModel> listSyn, boolean contact, String munipality) {
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

    public Date getStartSyn() {
        return startSyn;
    }

    public void setStartSyn(Date startSyn) {
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
        return "Report{" +
                "IDCode='" + IDCode + '\'' +
                ", startSyn=" + startSyn +
                ", listSyn=" + listSyn +
                ", contact=" + contact +
                ", munipality='" + munipality + '\'' +
                '}';
    }
}
