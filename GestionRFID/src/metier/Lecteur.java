package metier;

import com.sun.media.sound.DLSModulator;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lecteur {
    long idLecteur;
    String ip;
    public static InputStream is;
    public static OutputStream os;

    public Lecteur() {
    }

    public Lecteur(String ip) {
        this.ip = ip;
    }

    public long getIdLecteur() {
        return idLecteur;
    }

    public void setIdLecteur(long idLecteur) {
        this.idLecteur = idLecteur;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    //Permet de se connecter au port virtuel avec lequel on va interagir pour envoyer/recevoir les trames
    public void connect(String portName) throws Exception 
    {
        
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        //gÃ©nÃ©rer une liste des ports disponibles. Elle choisit ensuite un port dans la liste et appelle
        // CommPortIdentifier.open pour crÃ©er un objet CommPort qui est castÃ© ensuite en SerialPort.

        if (portIdentifier.isCurrentlyOwned()) // si le port est deja connectÃ©
        {
            System.out.println("Error: Port is currently in use");
        } 
        else 
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) 
            {
                //si le port est prÃ©sent mais pas connectÃ©
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                is = serialPort.getInputStream();
                os = serialPort.getOutputStream();
            } 
            else // si le port n'est pas prÃ©sent
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }
    
    //Active l'antenne Ã©lÃ©ctromagnÃ©tique pour manipulation des badges
    public static boolean AllumerAntenne()
    {
        boolean reussi=false;
        String t = "010510"; //Trame permettant d'allumer l'antenne 
        //ajout du crc Ã  la trame
        String crc = CalculCRC(t);
        t+=crc;
        //conversion en tableau de byte pour Ã©criture
        byte[] trame = hexStringToByteArray(t);
        
        //Ecriture de la trame
        try {
            os.write(trame);
        } catch (IOException ex) {
            Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Lecture de la trame rÃ©ponse
        byte[] trameRecue = new byte[15];
        int nbByteT = 0, nbByteAtt = 6, nbByte;
        while (nbByteT < nbByteAtt) 
        {
            try {
                nbByte = is.read(trameRecue, nbByteT, nbByteAtt - nbByteT);
                System.out.println("Taille:" + nbByte);
                nbByteT += nbByte;
            } catch (IOException ex) {
                Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Afficage de la trame reÃ§ue pour test
        System.out.println(getHexString(trameRecue));
        //Si le code de l'opÃ©ration est Ã©gale Ã  FF soit -1 en byte : l'opÃ©ration a rÃ©ussi
        if(trameRecue[3]==-1)
        {
            reussi = true;
        }
        
        return reussi;
    }
    
    //SÃ©lectionne le badge en contact avec le lecteur
    public static boolean SelectionBadge()
    {
        boolean ok=false;
        boolean reussi=false;
        
        String t = "010530"; //Trame permettant de sÃ©lectionner le badge
        //ajout du crc Ã  la trame
        String crc = CalculCRC(t);
        t+=crc;
        //conversion en tableau de byte pour Ã©criture
        byte[] trame = hexStringToByteArray(t);
        
        //Ecriture de la trame
        try {
            os.write(trame);
        } catch (IOException ex) {
            Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Lecture de la trame rÃ©ponse
        byte[] trameRecue = new byte[15];
        int nbByteT = 0, nbByteAtt = 10, nbByte;
        while (nbByteT < nbByteAtt) 
        {
            try {
                nbByte = is.read(trameRecue, nbByteT, nbByteAtt - nbByteT);
                System.out.println("Taille:" + nbByte);
                nbByteT += nbByte;
            } catch (IOException ex) {
                Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Afficage de la trame reÃ§ue pour test
        System.out.println(getHexString(trameRecue));
        //Si le code de l'opÃ©ration est Ã©gale Ã  FF soit -1 en byte : l'opÃ©ration a rÃ©ussi
        if(trameRecue[7]==-1)
        {
            reussi = true;
        }
        
        return reussi;
    }
    
    //Met en veille du badge en contact avec le lecteur pour pouvoir en manipuler d'autre
    public static boolean MiseVeilleBadge()
    {
        boolean reussi=false;
        String t = "010540"; //Trame permettant la mise en veille du badge
        //ajout du crc Ã  la trame
        String crc = CalculCRC(t);
        t+=crc;
        //conversion en tableau de byte pour Ã©criture
        byte[] trame = hexStringToByteArray(t);
        
        //Ecriture de la trame
        try {
            os.write(trame);
        } catch (IOException ex) {
            Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Lecture de la trame rÃ©ponse
        byte[] trameRecue = new byte[15];
        int nbByteT = 0, nbByteAtt = 6, nbByte;
        while (nbByteT < nbByteAtt) 
        {
            try {
                nbByte = is.read(trameRecue, nbByteT, nbByteAtt - nbByteT);
                System.out.println("Taille:" + nbByte);
                nbByteT += nbByte;
            } catch (IOException ex) {
                Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Afficage de la trame reÃ§ue pour test
        System.out.println(getHexString(trameRecue));
        //Si le code de l'opÃ©ration est Ã©gale Ã  FF soit -1 en byte : l'opÃ©ration a rÃ©ussi
        if(trameRecue[3]==-1)
        {
            reussi = true;
        }
        
        return reussi;
    }
    
    //Eteind l'antenne Ã©lÃ©ctromagnÃ©tique
    public static boolean EteindreAntenne()
    {
        boolean reussi=false;
        String t = "010512"; //Trame permettant d'Ã©teindre l'antenne
        //ajout du crc Ã  la trame
        String crc = CalculCRC(t);
        t+=crc;
        //conversion en tableau de byte pour Ã©criture
        byte[] trame = hexStringToByteArray(t);
        
        //Ecriture de la trame
        try {
            os.write(trame);
        } catch (IOException ex) {
            Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Lecture de la trame rÃ©ponse
        byte[] trameRecue = new byte[15];
        int nbByteT = 0, nbByteAtt = 6, nbByte;
        while (nbByteT < nbByteAtt) 
        {
            try {
                nbByte = is.read(trameRecue, nbByteT, nbByteAtt - nbByteT);
                System.out.println("Taille:" + nbByte);
                nbByteT += nbByte;
            } catch (IOException ex) {
                Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Afficage de la trame reÃ§ue pour test
        System.out.println(getHexString(trameRecue));
        //Si le code de l'opÃ©ration est Ã©gale Ã  FF soit -1 en byte : l'opÃ©ration a rÃ©ussi
        if(trameRecue[3]==-1)
        {
            reussi = true;
        }
        
        return reussi;
    }
    
    
    
    //Permet de lire le contenu d'un badge (zoneLecteur Ã  1 seul octet)
    public static String LectureBadge(String zoneLecteur) 
    {
        boolean ok=false;
        String idBadge="";
        ok = SelectionBadge();
        if(ok==true)
        {
            String t = "010652"+zoneLecteur; //Trame permettant de lire un badge
            //ajout du crc Ã  la trame
            String crc = CalculCRC(t);
            t+=crc;
            //conversion en tableau de byte pour Ã©criture
            byte[] trame = hexStringToByteArray(t);
        
            //Ecriture de la trame
            try {
                os.write(trame);
            } catch (IOException ex) {
                Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            //Lecture de la trame rÃ©ponse
            byte[] trameRecue = new byte[15];
            int nbByteT = 0, nbByteAtt = 10, nbByte;
            while (nbByteT < nbByteAtt) 
            {
                try {
                    nbByte = is.read(trameRecue, nbByteT, nbByteAtt - nbByteT);
                    System.out.println("Taille:" + nbByte);
                    nbByteT += nbByte;
                } catch (IOException ex) {
                    Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
            //Afficage de la trame reÃ§ue pour test
            System.out.println(getHexString(trameRecue));
            //Si le code de l'opÃ©ration est Ã©gale Ã  FF soit -1 en byte : l'opÃ©ration a rÃ©ussi
            if(trameRecue[7]==-1)
            {
                byte[] id = new byte[]{trameRecue[3],trameRecue[4],trameRecue[5],trameRecue[6]};
                idBadge = getHexString(id);
            }
            ok=false;
            ok=MiseVeilleBadge();
            if(ok==false)
            {
               System.out.println("Le badge n'a pas pu Ãªtre mise en veille : Un redÃ©marrage de l'antenne est nÃ©cessaire pour manipulation d'un autre badge !"); 
            }
        
        }
        else
        {
            System.out.println("Erreur le badge n'a pas pu Ã©tre sÃ©lÃ©ctionnÃ©");
        }
         return idBadge;
    }
    
      //Permet d'Ã©crire sur le badge (aEcrire doit correspondre Ã  4 octet, zoneLecteur Ã  1 seul octet)
    public static boolean EcritureBadge(String aEcrire, String zoneLecteur) 
    {
        boolean ok = false;
        boolean reussi=false;
        ok = SelectionBadge();
        if(ok==true)
        {
            String t = "010A50"+aEcrire+zoneLecteur; //Trame permettant d'Ã©crire sur le badge
            //ajout du crc Ã  la trame
            String crc = CalculCRC(t);
             t+=crc;
            //conversion en tableau de byte pour Ã©criture
            byte[] trame = hexStringToByteArray(t);
        
            //Ecriture de la trame
            try {
                os.write(trame);
            } catch (IOException ex) {
                Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            //Lecture de la trame rÃ©ponse
            byte[] trameRecue = new byte[15];
            int nbByteT = 0, nbByteAtt = 6, nbByte;
            while (nbByteT < nbByteAtt) 
            {
                try {
                    nbByte = is.read(trameRecue, nbByteT, nbByteAtt - nbByteT);
                    //System.out.println("Taille:" + nbByte);
                    nbByteT += nbByte;
                } catch (IOException ex) {
                    Logger.getLogger(Lecteur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
            //Afficage de la trame reÃ§ue pour test
            System.out.println(getHexString(trameRecue));
            //Si le code de l'opÃ©ration est Ã©gale Ã  FF soit -1 en byte : l'opÃ©ration a rÃ©ussi
            if(trameRecue[3]==-1)
            {
                reussi = true;
            }
            ok=false;
            ok=MiseVeilleBadge();
            if(ok==false)
            {
               System.out.println("Le badge n'a pas pu Ãªtre mise en veille : Un redÃ©marrage de l'antenne est nÃ©cessaire pour manipulation d'un autre badge !"); 
            }
        
        }
        else
        {
            System.out.println("Erreur le badge n'a pas pu Ã©tre sÃ©lÃ©ctionnÃ©");
        }
        
        return reussi;
    }

    
   //Permet de convertir une chaine hexadÃ©cimale en tableau de bytes.
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    
    //Permet de convertir un tableau de byte en chaine hexadÃ©cimale
     public static String getHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
     
     public static String CalculCRC(String t)
	{
            byte [] trame = hexStringToByteArray(t); 
            int Many = trame.length;
            String CRC;
            int i,NrBajtu;
            short C, crc = 0;
	    for (NrBajtu=0;NrBajtu<Many;NrBajtu++)
	    {
	        C=(short)(((crc>>8)^trame[NrBajtu])<<8);
	        for (i=0;i<8;i++)
	        	if (C < 0) C=(short)((C<<1)^0x1021); 
	            //if ((C & 0x8000) == 0x8000) C=(short)((C<<1)^0x1021); 
	            else C=(short)(C<<1);
	        	// la condition (C < 0) est l'Ã©quivalent de ((C & 0x8000) == 0x8000)
	        	// <=> C est de type short
	        crc=(short)(C^(crc<<8));
	        //System.out.format("%d:%x\n", NrBajtu, crc); //pour debug
	    }
            CRC=String.format("%04x", crc);
	    return CRC;
	}
    
}
