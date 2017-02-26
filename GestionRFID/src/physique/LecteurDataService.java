package physique;

import java.util.List;
import metier.Lecteur;
import metier.Zone;

public interface LecteurDataService {
    Lecteur add(Lecteur lecteur, long idZone) throws Exception;
    boolean remove(Lecteur lecteur) throws Exception;
    boolean removeByZone(Zone zone) throws Exception;
    boolean update(Lecteur lecteur) throws Exception;
    Lecteur getByIdLecteur(long idLecteur) throws Exception;
    Lecteur getByIp(String ip) throws Exception;
    List<String> getListIp() throws Exception;
}
