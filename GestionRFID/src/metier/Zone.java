package metier;

import java.util.List;
import java.util.Objects;

public class Zone {
    long idZone;
    String nomZone;
    boolean sensible;
    String horaireOuverture;
    String horaireFermeture;
    List<Lecteur> lecteurs;

    public Zone() {
    }

    public Zone(String nomZone, boolean sensible, String horaireOuverture, String horaireFermeture, List<Lecteur> lecteurs) {
        this.nomZone = nomZone;
        this.sensible = sensible;
        this.horaireOuverture = horaireOuverture;
        this.horaireFermeture = horaireFermeture;
        this.lecteurs = lecteurs;
    }

    public long getIdZone() {
        return idZone;
    }

    public void setIdZone(long idZone) {
        this.idZone = idZone;
    }

    public String getNomZone() {
        return nomZone;
    }

    public void setNomZone(String nomZone) {
        this.nomZone = nomZone;
    }

    public boolean getSensible() {
        return sensible;
    }

    public void setSensible(boolean sensible) {
        this.sensible = sensible;
    }

    public String getHoraireOuverture() {
        return horaireOuverture;
    }

    public void setHoraireOuverture(String horaireOuverture) {
        this.horaireOuverture = horaireOuverture;
    }

    public String getHoraireFermeture() {
        return horaireFermeture;
    }

    public void setHoraireFermeture(String horaireFermeture) {
        this.horaireFermeture = horaireFermeture;
    }

    public List<Lecteur> getLecteurs() {
        return lecteurs;
    }

    public void setLecteurs(List<Lecteur> lecteurs) {
        this.lecteurs = lecteurs;
    }
     
}
