package physique;

import metier.Badge;
import metier.Personne;

public interface PersonneDataService {
    Personne add(Personne personne) throws Exception;
    boolean remove(Personne personne) throws Exception;
    boolean update(Personne personne) throws Exception;
    Personne getByIdPersonne(long idPersonne) throws Exception;
    Personne getByBadge(Badge badge) throws Exception;
    Personne getByNomPrenom(String nom, String prenom) throws Exception;
}
