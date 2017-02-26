package metier;

import java.util.List;
import java.util.Objects;

public class Batiment {
    long idBatiment;
    String nomBatiment;
    List<Zone> zones;

    public Batiment() {
    }

    public Batiment(String nomBatiment, List<Zone> zones) {
        this.nomBatiment = nomBatiment;
        this.zones = zones;
    }

    public long getIdBatiment() {
        return idBatiment;
    }

    public void setIdBatiment(long idBatiment) {
        this.idBatiment = idBatiment;
    }

    public String getNomBatiment() {
        return nomBatiment;
    }

    public void setNomBatiment(String nomBatiment) {
        this.nomBatiment = nomBatiment;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
    
}
