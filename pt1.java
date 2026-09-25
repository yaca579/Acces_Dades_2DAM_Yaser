import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnalitzadorText {
    public static void main(String[] args) {
        String rutaFitxer = "text.txt";
        
        long totalCaracters = 0;
        long totalLinies = 0;
        long totalParaules = 0;
        
        HashMap<Character, Integer> frequenciaCaracters = new HashMap<>();
        boolean dinsParaula = false;
        boolean fitxerBuit = true;
        
        // Utilitzem try-with-resources per tancar el FileReader automàticament
        try (FileReader reader = new FileReader(rutaFitxer)) {
            int valorLlegit;
            
            while ((valorLlegit = reader.read()) != -1) {
                char caracter = (char) valorLlegit;
                fitxerBuit = false;
                
                // Gestió dels salts de línia
                if (caracter == '\n') {
                    totalLinies++;
                    dinsParaula = false;
                    continue; // No comptar el salt de línia com a caràcter habitual
                } else if (caracter == '\r') {
                    continue; // Ignorar el retorn de carro (comú en sistemes Windows)
                }
                
                // Comptabilitzar caràcter (espais i tabulacions sí que compten)
                totalCaracters++;
                
                // Comptabilitzar freqüència (excloent espais i tabulacions)
                if (caracter != ' ' && caracter != '\t') {
                    frequenciaCaracters.put(caracter, frequenciaCaracters.getOrDefault(caracter, 0) + 1);
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
            
            // Ajustar el recompte de línies si el fitxer té contingut
            if (!fitxerBuit) {
                totalLinies++; // Sumem la primera línia
            }
            
            // Trobar el caràcter més freqüent
            char caracterMesFrequent = ' ';
            int maxFreq = 0;
            for (Map.Entry<Character, Integer> entry : frequenciaCaracters.entrySet()) {
                if (entry.getValue() > maxFreq) {
                    maxFreq = entry.getValue();
                    caracterMesFrequent = entry.getKey();
                }
            }
            
            // Mostrar resultats per pantalla
            System.out.println("--- RESULTATS DE L'ANÀLISI ---");
            System.out.println("Nombre total de caràcters (sense salts de línia): " + totalCaracters);
            System.out.println("Nombre total de línies: " + totalLinies);
            System.out.println("Nombre total de paraules: " + totalParaules);
            
            if (maxFreq > 0) {
                System.out.println("Caràcter més freqüent: '" + caracterMesFrequent + "' (apareix " + maxFreq + " vegades)");
            } else {
                System.out.println("No s'han trobat caràcters per analitzar la freqüència.");
            }
            
        } catch (IOException e) {
            System.err.println("S'ha produït un error al llegir el fitxer: " + e.getMessage());
        }
    }
}