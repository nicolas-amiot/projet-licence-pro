package metier;

public interface PersonneService {
    Personne add(Personne personne);
    boolean remove(Personne personne);
    boolean update(Personne personne);
    Personne getByIdPersonne(long idPersonne);
    Personne getByBadge(Badge badge);
    Personne getByNomPrenom(String nom, String prenom);
}
