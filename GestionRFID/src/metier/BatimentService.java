package metier;

import java.util.List;

public interface BatimentService {
    Batiment add(Batiment batiment);
    boolean remove(Batiment batiment);
    boolean update(Batiment batiment);
    Batiment getByIdBatiment(long idBatiment);
    Batiment getByNomBatiment(String nomBatiment);
    List<String> getListNomBatiment();
}
