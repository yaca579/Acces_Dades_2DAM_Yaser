import java.io.Serializable;

/**
 * Classe que representa un Videojoc del catàleg.
 * Implementa Serializable per permetre la persistència en fitxers binaris.
 */
public class Videojoc implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titol;
    private String genere;
    private int anyLlancament;
    private String plataforma;
    private double preu;

    // Constructor complet
    public Videojoc(String titol, String genere, int anyLlancament, String plataforma, double preu) {
        this.titol = titol;
        this.genere = genere;
        this.anyLlancament = anyLlancament;
        this.plataforma = plataforma;
        this.preu = preu;
    }

    // Getters i Setters
    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getAnyLlancament() {
        return anyLlancament;
    }

    public void setAnyLlancament(int anyLlancament) {
        this.anyLlancament = anyLlancament;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    @Override
    public String toString() {
        return String.format("Títol: %-25s | Gènere: %-12s | Any: %d | Plataforma: %-12s | Preu: %.2f €",
                titol, genere, anyLlancament, plataforma, preu);
    }
}