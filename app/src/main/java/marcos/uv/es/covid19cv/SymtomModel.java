package marcos.uv.es.covid19cv;

import java.io.Serializable;

public class SymtomModel implements Serializable {
    private boolean selected;
    private String title;

    public SymtomModel(String title, boolean selected) {
        this.selected = selected;
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
