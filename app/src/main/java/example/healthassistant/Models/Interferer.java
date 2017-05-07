package example.healthassistant.Models;

/**
 * Created by Neha on 5/6/2017.
 */

public class Interferer {

    private int med_id;
    private int interferer_id;
    private int minToInterferer;
    private int minFromInterferer;

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }

    public int getInterferer_id() {
        return interferer_id;
    }

    public void setInterferer_id(int interferer_id) {
        this.interferer_id = interferer_id;
    }

    public int getMinToInterferer() {
        return minToInterferer;
    }

    public void setMinToInterferer(int minToInterferer) {
        this.minToInterferer = minToInterferer;
    }

    public int getMinFromInterferer() {
        return minFromInterferer;
    }

    public void setMinFromInterferer(int minFromInterferer) {
        this.minFromInterferer = minFromInterferer;
    }
}
