import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CrearZooXML {

    public static void main(String[] args) {
        try {
            // 1. Inicialitzar el constructor de documents XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // 2. Crear l'element arrel <zoo>
            Element rootElement = doc.createElement("zoo");
            doc.appendChild(rootElement);

            // 3. Afegir els animals a l'arbre XML
            afegirAnimal(doc, rootElement, "1", "Pingüí", "Aptenodytes forsteri", "Peix", "5");
            afegirAnimal(doc, rootElement, "2", "Lleó", "Panthera leo", "Carn", "8");
            afegirAnimal(doc, rootElement, "3", "Tigre", "Panthera tigris", "Carn", "6");

            // 4. Configurar el Transformer per guardar l'XML amb formatat (sangries)
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("zoo.xml"));

            // 5. Escriure les dades al fitxer
            transformer.transform(source, result);
            System.out.println("Fitxer 'zoo.xml' creat amb èxit.");

        } catch (Exception e) {
            System.err.println("Error en crear el fitxer XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mètode auxiliar per crear i afegir un element <animal> al node arrel.
     */
    private static void afegirAnimal(Document doc, Element root, String id, String nom, String especie, String aliment, String edat) {
        Element animal = doc.createElement("animal");
        animal.setAttribute("id", id);

        Element elemNom = doc.createElement("nom");
        elemNom.appendChild(doc.createTextNode(nom));
        animal.appendChild(elemNom);

        Element elemEspecie = doc.createElement("especie");
        elemEspecie.appendChild(doc.createTextNode(especie));
        animal.appendChild(elemEspecie);

        Element elemAliment = doc.createElement("aliment");
        elemAliment.appendChild(doc.createTextNode(aliment));
        animal.appendChild(elemAliment);

        Element elemEdat = doc.createElement("edat");
        elemEdat.appendChild(doc.createTextNode(edat));
        animal.appendChild(elemEdat);

        root.appendChild(animal);
    }
}