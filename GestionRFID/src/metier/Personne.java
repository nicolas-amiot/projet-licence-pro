package metier;

public class Personne {

    long idPersonne;
    String nom;
    String prenom;
    Badge badge;

    public Personne() {
    }

    public Personne(String nom, String prenom, Badge badge) {
        this.nom = nom;
        this.prenom = prenom;
        this.badge = badge;
    }

    public long getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(long idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

}
