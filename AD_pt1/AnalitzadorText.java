import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalitzadorText {
    public static void main(String[] args) {
        String rutaFitxer = "text.txt";
        
        long totalCaracters = 0;
        long totalLinies = 0;
        long totalParaules = 0;
        
        // Array de freqüències per a tots els caràcters UNICODE
        int[] frequenciaCaracters = new int[65536];
        
        boolean dinsParaula = false;
        boolean fitxerBuit = true;
        char ultimCaracter = ' ';

        // Try-with-resources per tancar el FileReader automàticament
        try (FileReader reader = new FileReader(rutaFitxer)) {
            int valorLlegit;
            
            while ((valorLlegit = reader.read()) != -1) {
                char caracter = (char) valorLlegit;
                fitxerBuit = false;
                ultimCaracter = caracter;
                
                // Gestió dels salts de línia
                if (caracter == '\n') {
                    totalLinies++;
                    dinsParaula = false;
                    continue; // Els salts de línia no compten com a caràcters habituals
                } else if (caracter == '\r') {
                    continue; // Ignorar el retorn de carro (sistemes Windows)
                }
                
                // Comptabilitzar caràcter (espais i tabulacions sí compten)
                totalCaracters++;
                
                // Comptabilitzar freqüència (excloent espais i tabulacions)
                if (caracter != ' ' && caracter != '\t') {
                    frequenciaCaracters[caracter]++;
                }
                
                // Detecció de paraules
                if (caracter == ' ' || caracter == '\t') {
                    dinsParaula = false;
                } else {
                    if (!dinsParaula) {
                        totalParaules++;
                        dinsParaula = true;
                    }
                }
            }
            
            // Si el fitxer té contingut i no acaba en '\n', comptem la línia actual
            if (!fitxerBuit && ultimCaracter != '\n') {
                totalLinies++;
            }
            
            // Cerca del caràcter més freqüent a l'array
            char caracterMesFrequent = ' ';
            int maxFreq = 0;
            for (int i = 0; i < frequenciaCaracters.length; i++) {
                if (frequenciaCaracters[i] > maxFreq) {
                    maxFreq = frequenciaCaracters[i];
                    caracterMesFrequent = (char) i;
                }
            }
            
            // Sortida dels resultats
            System.out.println("Nombre de caràcters: " + totalCaracters);
            System.out.println("Nombre de línies: " + totalLinies);
            System.out.println("Nombre de paraules: " + totalParaules);
            
            if (maxFreq > 0) {
                System.out.println("Caràcter més repetit: " + caracterMesFrequent);
            } else {
                System.out.println("No s'han trobat caràcters vàlids.");
            }

        } catch (FileNotFoundException e) {
            System.err.println("El fitxer no existeix.");
        } catch (IOException e) {
            System.err.println("S'ha produït un error de lectura.");
        } catch (SecurityException e) {
            System.err.println("No tens permisos per accedir al fitxer.");
        }
    }
}