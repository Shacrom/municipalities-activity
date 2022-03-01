package marcos.uv.es.covid19cv;

public class Municipio {
    private int id;
    private int codMunicipio;
    private String municipio;
    private int casosPCR;
    private String incidencia;
    private int casosPcr14dias;
    private String incidencia14dias;
    private int defunciones;
    private String tasaDefuncion;

    public Municipio(int id, int codMunicipio, String municipio, int casosPCR, String incidencia, int casosPcr14dias, String incidencia14dias, int defunciones, String tasaDefuncion) {
        this.id = id;
        this.codMunicipio = codMunicipio;
        this.municipio = municipio;
        this.casosPCR = casosPCR;
        this.incidencia = incidencia;
        this.casosPcr14dias = casosPcr14dias;
        this.incidencia14dias = incidencia14dias;
        this.defunciones = defunciones;
        this.tasaDefuncion = tasaDefuncion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(int codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getCasosPCR() {
        return casosPCR;
    }

    public void setCasosPCR(int casosPCR) {
        this.casosPCR = casosPCR;
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }

    public int getCasosPcr14dias() {
        return casosPcr14dias;
    }

    public void setCasosPcr14dias(int casosPcr14dias) {
        this.casosPcr14dias = casosPcr14dias;
    }

    public String getIncidencia14dias() {
        return incidencia14dias;
    }

    public void setIncidencia14dias(String incidencia14dias) {
        this.incidencia14dias = incidencia14dias;
    }

    public int getDefunciones() {
        return defunciones;
    }

    public void setDefunciones(int defunciones) {
        this.defunciones = defunciones;
    }

    public String getTasaDefuncion() {
        return tasaDefuncion;
    }

    public void setTasaDefuncion(String tasaDefuncion) {
        this.tasaDefuncion = tasaDefuncion;
    }
}
