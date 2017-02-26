package metier;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Badge {

    private long idBadge;
    private String numero;
    private String password;
    private Date dateCreation;
  
    public Badge() {
    }

    public Badge(String numero, String password, Date dateCreation) {
        this.numero = numero;
        this.password = password;
        this.dateCreation = dateCreation;
    }

    public long getIdBadge() {
        return idBadge;
    }

    public void setIdBadge(long idBadge) {
        this.idBadge = idBadge;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static String encode(String password) {
        StringBuilder sb = new StringBuilder();
        if (password != null && !password.isEmpty()) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b));
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Personne.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sb.toString();
    }
    
}
