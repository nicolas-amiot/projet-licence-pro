package metier;

import physique.PersonneDataService;
import physique.PhysiqueFactory;

public class PersonneServiceImpl implements PersonneService {

    private final PersonneDataService personneDataSrv;
    private final BadgeService badgeSrv;

    PersonneServiceImpl() {
        personneDataSrv = PhysiqueFactory.getPersonneDataService();
        badgeSrv = MetierFactory.getBadgeService();
    }

    @Override
    public Personne add(Personne personne) {
        Personne newPersonne = null;
        try {
            if (!personne.getNom().isEmpty() && !personne.getPrenom().isEmpty()) {
                newPersonne = personneDataSrv.add(personne);
                if(personne.getBadge() != null){
                    newPersonne.setBadge(badgeSrv.add(personne.getBadge(), newPersonne.getIdPersonne()));
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return newPersonne;
    }

    @Override
    public boolean remove(Personne personne) {
        boolean execute = false;
        try {
            if (personne.getIdPersonne() > 0) {
                execute = personneDataSrv.remove(personne);
                if(personne.getBadge() != null){
                    badgeSrv.remove(personne.getBadge());
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public boolean update(Personne personne) {
        boolean execute = false;
        try {
            if (personne.getIdPersonne() > 0 && !personne.getNom().isEmpty() && !personne.getPrenom().isEmpty()) {
                execute = personneDataSrv.update(personne);
                if(personne.getBadge() == null){
                    badgeSrv.remove(personne.getBadge());
                } else if(personne.getBadge().getIdBadge() > 0){
                    badgeSrv.update(personne.getBadge());
                } else {
                    badgeSrv.add(personne.getBadge(), personne.getIdPersonne());
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return execute;
    }

    @Override
    public Personne getByIdPersonne(long idPersonne) {
        Personne personne = null;
        try {
            if (idPersonne > 0) {
                personne = personneDataSrv.getByIdPersonne(idPersonne);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return personne;
    }

    @Override
    public Personne getByBadge(Badge badge) {
        Personne personne = null;
        try {
            if (badge.getIdBadge() > 0) {
                personne = personneDataSrv.getByBadge(badge);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return personne;
    }

    @Override
    public Personne getByNomPrenom(String nom, String prenom) {
        Personne personne = null;
        try {
            if (!nom.isEmpty() && !prenom.isEmpty()) {
                personne = personneDataSrv.getByNomPrenom(nom, prenom);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return personne;
    }

}
