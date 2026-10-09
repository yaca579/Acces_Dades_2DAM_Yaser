import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LlegirZooXML {
    public static void main(String[] args) {
        try {
            File fitxerXML = new File("zoo.xml");
            if (!fitxerXML.exists()) {
                System.out.println("El fitxer 'zoo.xml' no existeix. Executa primer la Part 1.");
                return;
            }

            // 1. Carregar i parsejar el fitxer XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fitxerXML);

            doc.getDocumentElement().normalize();

            // Obtenir tots els nodes <animal>
            NodeList llistaAnimals = doc.getElementsByTagName("animal");

            // --- MOSTRAR TOTS ELS ANIMALS ---
            System.out.println("=== LLISTAT DE TOTS ELS ANIMALS ===");
            for (int i = 0; i < llistaAnimals.getLength(); i++) {
                Node node = llistaAnimals.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    imprimirAnimal(element);
                }
            }

            // --- BONUS 1: COMPTAR ANIMALS ---
            System.out.println("=== BONUS 1: COMPTADOR D'ANIMALS ===");
            System.out.println("Total d'animals registrats al zoo: " + llistaAnimals.getLength());
            System.out.println();

            // --- BONUS 2: MOSTRAR NOMÉS ELS QUE MENGEN 'CARN' ---
            System.out.println("=== BONUS 2: ANIMALS QUE MENGEN CARN ===");
            for (int i = 0; i < llistaAnimals.getLength(); i++) {
                Node node = llistaAnimals.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String aliment = element.getElementsByTagName("aliment").item(0).getTextContent();

                    if ("Carn".equalsIgnoreCase(aliment)) {
                        imprimirAnimal(element);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error en llegir el fitxer XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mètode per formatar i mostrar la informació d'un animal per consola.
     */
    private static void imprimirAnimal(Element element) {
        String id = element.getAttribute("id");
        String nom = element.getElementsByTagName("nom").item(0).getTextContent();
        String especie = element.getElementsByTagName("especie").item(0).getTextContent();
        String aliment = element.getElementsByTagName("aliment").item(0).getTextContent();
        String edat = element.getElementsByTagName("edat").item(0).getTextContent();

        System.out.println("Animal #" + id);
        System.out.println("Nom: " + nom);
        System.out.println("Espècie: " + especie);
        System.out.println("Aliment: " + aliment);
        System.out.println("Edat: " + edat);
        System.out.println();
    }
}
