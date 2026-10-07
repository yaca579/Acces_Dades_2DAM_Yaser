import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GestioVideojocsApp {

    private static final String FITXER = "videojocs.dat";
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Videojoc> cataleg = carregarVideojocs();
        int opcio = 0;

        do {
            mostrarMenu();
            opcio = llegirEnter("Tria una opció: ");

            switch (opcio) {
                case 1 -> afegirVideojoc(cataleg);
                case 2 -> llistarVideojocs(cataleg);
                case 3 -> cercarVideojocPerTitol(cataleg);
                case 4 -> actualitzarVideojoc(cataleg);
                case 5 -> eliminarVideojoc(cataleg);
                case 6 -> {
                    desarVideojocs(cataleg);
                    System.out.println("\nGràcies per utilitzar l'aplicació. Fins aviat!");
                }
                default -> System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        } while (opcio != 6);

        sc.close();
    }

    
    // MÈTODES DEL MENÚ I INTERACCIÓ (CRUD)
   

    private static void mostrarMenu() {
        System.out.println("\n==================================");
        System.out.println("   GESTIÓ DE CATÀLEG DE VIDEOJOCS  ");
        System.out.println("==================================");
        System.out.println("1. Afegir videojoc");
        System.out.println("2. Llistar tots els videojocs");
        System.out.println("3. Cercar videojocs per títol");
        System.out.println("4. Actualitzar un videojoc");
        System.out.println("5. Eliminar un videojoc");
        System.out.println("6. Sortir del programa");
        System.out.println("==================================");
    }

    private static void afegirVideojoc(ArrayList<Videojoc> cataleg) {
        System.out.println("\n--- AFEGIR NOU VIDEOJOC ---");
        System.out.print("Títol: ");
        String titol = sc.nextLine().trim();

        System.out.print("Gènere (ex: Acció, Rol, Esport...): ");
        String genere = sc.nextLine().trim();

        int any = llegirEnter("Any de llançament: ");

        System.out.print("Plataforma (ex: PC, PlayStation, Xbox, Switch): ");
        String plataforma = sc.nextLine().trim();

        double preu = llegirDouble("Preu (€): ");

        Videojoc nou = new Videojoc(titol, genere, any, plataforma, preu);
        cataleg.add(nou);
        desarVideojocs(cataleg);

        System.out.println("✓ Videojoc afegit i desat correctament!");
    }

    private static void llistarVideojocs(ArrayList<Videojoc> cataleg) {
        System.out.println("\n--- LLISTAT DE VIDEOJOCS ---");
        if (cataleg.isEmpty()) {
            System.out.println("El catàleg està buit.");
            return;
        }

        for (int i = 0; i < cataleg.size(); i++) {
            System.out.printf("[%d] %s%n", (i + 1), cataleg.get(i));
        }
    }

    private static void cercarVideojocPerTitol(ArrayList<Videojoc> cataleg) {
        System.out.println("\n--- CERCAR VIDEOJOC PER TÍTOL ---");
        if (cataleg.isEmpty()) {
            System.out.println("El catàleg està buit.");
            return;
        }

        System.out.print("Introdueix el text a cercar: ");
        String textCerca = sc.nextLine().trim().toLowerCase();

        boolean trobat = false;
        for (int i = 0; i < cataleg.size(); i++) {
            Videojoc v = cataleg.get(i);
            if (v.getTitol().toLowerCase().contains(textCerca)) {
                System.out.printf("[%d] %s%n", (i + 1), v);
                trobat = true;
            }
        }

        if (!trobat) {
            System.out.println("No s'ha trobat cap videojoc que coïncideixi amb la cerca.");
        }
    }

    private static void actualitzarVideojoc(ArrayList<Videojoc> cataleg) {
        System.out.println("\n--- ACTUALITZAR VIDEOJOC ---");
        if (cataleg.isEmpty()) {
            System.out.println("El catàleg està buit.");
            return;
        }

        llistarVideojocs(cataleg);
        int index = llegirEnter("\nSelecciona el número del videojoc a modificar: ") - 1;

        if (index < 0 || index >= cataleg.size()) {
            System.out.println("Número no vàlid.");
            return;
        }

        Videojoc v = cataleg.get(index);
        System.out.println("\nS'està modificant: " + v.getTitol());

        System.out.print("Nou títol (deixa buit per mantenir '" + v.getTitol() + "'): ");
        String nouTitol = sc.nextLine().trim();
        if (!nouTitol.isEmpty()) v.setTitol(nouTitol);

        System.out.print("Nou gènere (deixa buit per mantenir '" + v.getGenere() + "'): ");
        String nouGenere = sc.nextLine().trim();
        if (!nouGenere.isEmpty()) v.setGenere(nouGenere);

        System.out.print("Nou any (deixa buit per mantenir '" + v.getAnyLlancament() + "'): ");
        String anyStr = sc.nextLine().trim();
        if (!anyStr.isEmpty()) {
            try {
                v.setAnyLlancament(Integer.parseInt(anyStr));
            } catch (NumberFormatException e) {
                System.out.println("Any no vàlid, es manté l'anterior.");
            }
        }

        System.out.print("Nova plataforma (deixa buit per mantenir '" + v.getPlataforma() + "'): ");
        String novaPlataforma = sc.nextLine().trim();
        if (!novaPlataforma.isEmpty()) v.setPlataforma(novaPlataforma);

        System.out.print("Nou preu (deixa buit per mantenir '" + v.getPreu() + " €'): ");
        String preuStr = sc.nextLine().trim();
        if (!preuStr.isEmpty()) {
            try {
                v.setPreu(Double.parseDouble(preuStr.replace(",", ".")));
            } catch (NumberFormatException e) {
                System.out.println("Preu no vàlid, es manté l'anterior.");
            }
        }

        desarVideojocs(cataleg);
        System.out.println("✓ Videojoc actualitzat i desat correctament!");
    }

    private static void eliminarVideojoc(ArrayList<Videojoc> cataleg) {
        System.out.println("\n--- ELIMINAR VIDEOJOC ---");
        if (cataleg.isEmpty()) {
            System.out.println("El catàleg està buit.");
            return;
        }

        llistarVideojocs(cataleg);
        int index = llegirEnter("\nSelecciona el número del videojoc a eliminar: ") - 1;

        if (index < 0 || index >= cataleg.size()) {
            System.out.println("Número no vàlid.");
            return;
        }

        Videojoc eliminat = cataleg.remove(index);
        desarVideojocs(cataleg);
        System.out.println("✓ S'ha eliminat '" + eliminat.getTitol() + "' del catàleg.");
    }

    
    // MÈTODES DE PERSISTÈNCIA BINÀRIA (.DAT)
    

    @SuppressWarnings("unchecked")
    public static ArrayList<Videojoc> carregarVideojocs() {
        ArrayList<Videojoc> cataleg = new ArrayList<>();
        File fitxer = new File(FITXER);

        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                cataleg = (ArrayList<Videojoc>) ois.readObject();
                System.out.println("✓ S'han carregat " + cataleg.size() + " videojocs des del fitxer " + FITXER);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("⚠️ Error en llegir el fitxer: " + e.getMessage());
            }
        } else {
            System.out.println("ℹ️ No s'ha trobat el fitxer " + FITXER + ". Es crearà un de nou en afegir dades.");
        }
        return cataleg;
    }

    public static void desarVideojocs(ArrayList<Videojoc> cataleg) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(cataleg);
        } catch (IOException e) {
            System.out.println("⚠️ Error en desar les dades: " + e.getMessage());
        }
    }

    
    // MÈTODES AUXILIARS DE LECTURA SEGURA

    

    private static int llegirEnter(String missatge) {
        while (true) {
            try {
                System.out.print(missatge);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Si us plau, introdueix un número enter vàlid.");
            }
        }
    }

    private static double llegirDouble(String missatge) {
        while (true) {
            try {
                System.out.print(missatge);
                String ent = sc.nextLine().trim().replace(",", ".");
                return Double.parseDouble(ent);
            } catch (NumberFormatException e) {
                System.out.println("Error: Si us plau, introdueix un número decimal vàlid.");
            }
        }
    }
}