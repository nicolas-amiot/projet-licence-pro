package metier;

import java.util.Date;
import java.util.List;

public class Acces {
    Badge badge;
    Zone zone;
    List<Date> datePassages;

    public Acces() {
    }

    public Acces(Badge badge, Zone zone, List<Date> datePassages) {
        this.badge = badge;
        this.zone = zone;
        this.datePassages = datePassages;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<Date> getDatePassages() {
        return datePassages;
    }

    public void setDatePassages(List<Date> datePassages) {
        this.datePassages = datePassages;
    }
    
}
