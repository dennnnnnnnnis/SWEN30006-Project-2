package src.map.utilities;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLFileReader implements MapFileReader{

    @Override
    public String readMapFile(String filename) {
        // create a new DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // create a new DocumentBuilder
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            // parse an XML file into a DOM tree
            org.w3c.dom.Document document = builder.parse(new File(filename));

            // get the root element of the document
            org.w3c.dom.Element root = document.getDocumentElement();

            // get all the row elements
            NodeList rows = root.getElementsByTagName("row");

            // create a new map to store the cell values
            StringBuilder sb = new StringBuilder();

            // loop over all the row elements
            for (int i = 0; i < rows.getLength(); i++) {
                // get the current row element
                org.w3c.dom.Element row = (org.w3c.dom.Element) rows.item(i);

                // get all the cell elements in this row
                NodeList cells = row.getElementsByTagName("cell");

                // loop over all the cell elements in this row
                for (int j = 0; j < cells.getLength(); j++) {
                    // get the current cell element
                    org.w3c.dom.Element cell = (Element) cells.item(j);

                    // get the text content of the cell element
                    String cellValue = cell.getTextContent();

                    // put the cell value into the map
                    switch (cellValue) {
                        case "WallTile":
                            sb.append("x");
                            break;
                        case "PillTile":
                            sb.append(".");
                            break;
                        case "PathTile":
                            sb.append("p");
                            break;
                        case "PacTile":
                            sb.append("a");
                            break;
                        case "TX5Tile":
                            sb.append("t");
                            break;
                        case "TrollTile":
                            sb.append("r");
                            break;
                        case "GoldTile":
                            sb.append("g");
                            break;
                        case "IceTile":
                            sb.append("i");
                            break;
                        case "PortalWhiteTile":
                            sb.append("w");
                            break;
                        case "PortalYellowTile":
                            sb.append("y");
                            break;
                        case "PortalDarkGoldTile":
                            sb.append("c");
                            break;
                        case "PortalDarkGrayTile":
                            sb.append("d");
                        default:
                            sb.append(" ");
                            break;
                    }
                }
            }
            // System.out.println(sb.toString());
            return sb.toString();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
