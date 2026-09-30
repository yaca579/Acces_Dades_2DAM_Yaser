import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PracticaXifrat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int clau = 3; // Clau per defecte si l'usuari no n'introdueix una de vàlida

        // Permetre introduir la clau per consola
        System.out.print("Introdueix la clau de xifrat/desxifrat (desplaçament enter): ");
        if (scanner.hasNextInt()) {
            clau = scanner.nextInt();
        } else {
            System.out.println("Entrada no vàlida. S'utilitzarà la clau per defecte (" + clau + ").");
        }

        String fitxerEntrada = "entrada.txt";
        String fitxerXifrat = "xifrat.txt";
        String fitxerDesxifrat = "desxifrat.txt";

        // Missatges de progrés per consola
        System.out.println("\n--- Iniciant procés de xifrat ---");
        xifrarFitxer(fitxerEntrada, fitxerXifrat, clau);

        System.out.println("\n--- Iniciant procés de desxifrat ---");
        desxifrarFitxer(fitxerXifrat, fitxerDesxifrat, clau);

        scanner.close();
    }

    /**
     * Llegeix el fitxer d'entrada, inverteix cada línia,
     * aplica el xifrat Cèsar i ho escriu al fitxer de sortida.
     */
    public static void xifrarFitxer(String origen, String desti, int clau) {
        try (
            BufferedReader br = new BufferedReader(new FileReader(origen));
            BufferedWriter bw = new BufferedWriter(new FileWriter(desti))
        ) {
            String linia;
            int liniesProcessades = 0;

            while ((linia = br.readLine()) != null) {
                // 1. Invertir la línia
                String invertida = new StringBuilder(linia).reverse().toString();

                // 2. Aplicar xifrat Cèsar 
                StringBuilder xifrada = new StringBuilder();
                for (char c : invertida.toCharArray()) {
                    xifrada.append((char) (c + clau));
                }

                // 3. Escriure al fitxer xifrat
                bw.write(xifrada.toString());
                bw.newLine();
                liniesProcessades++;
            }

            System.out.println(" Exit: S'han xifrat " + liniesProcessades + " línies a '" + desti + "'.");

        } catch (FileNotFoundException e) {
            System.err.println(" Error: No s'ha trobat el fitxer d'entrada '" + origen + "'. " + e.getMessage());
        } catch (IOException e) {
            System.err.println(" Error d'Entrada/Sortida durant el xifrat: " + e.getMessage());
        }
    }

    /**
     * Llegeix el fitxer xifrat, desfà el desplaçament Cèsar,
     * torna a invertir cada línia i escriu el missatge original a desxifrat.txt.
     */
    public static void desxifrarFitxer(String origen, String desti, int clau) {
        try (
            BufferedReader br = new BufferedReader(new FileReader(origen));
            BufferedWriter bw = new BufferedWriter(new FileWriter(desti))
        ) {
            String linia;
            int liniesProcessades = 0;

            while ((linia = br.readLine()) != null) {
                // 1. Desfer xifrat Cèsar 
                StringBuilder desxifradaCesar = new StringBuilder();
                for (char c : linia.toCharArray()) {
                    desxifradaCesar.append((char) (c - clau));
                }

                // 2. Tornar a invertir la línia per recuperar l'ordre original
                String liniaOriginal = desxifradaCesar.reverse().toString();

                // 3. Escriure al fitxer desxifrat
                bw.write(liniaOriginal);
                bw.newLine();
                liniesProcessades++;
            }

            System.out.println(" Exit: S'han desxifrat " + liniesProcessades + " línies a '" + desti + "'.");

        } catch (FileNotFoundException e) {
            System.err.println(" Error: No s'ha trobat el fitxer xifrat '" + origen + "'. " + e.getMessage());
        } catch (IOException e) {
            System.err.println(" Error d'Entrada/Sortida durant el desxifrat: " + e.getMessage());
        }
    }
}