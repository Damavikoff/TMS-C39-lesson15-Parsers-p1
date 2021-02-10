/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom.util.xml;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author SharpSchnitzel
 */
public class ParserDOM implements CustomParser {
    
    private final List<String> allowedFields = Arrays.asList("last_name", "first_name", "middle_name", "birth_date", "position");
    private final HashMap<String, String> customer = new HashMap<>();
    private final ArrayList<HashMap> result = new ArrayList<>();
    private String document;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
    
    public ParserDOM (String xml) {
        this.document = xml;
    }
    
    
    @Override
    public void processXML() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document xml = dBuilder.parse(new InputSource(new StringReader(this.document)));
            System.out.println("[" + this.dateFormat.format(LocalDateTime.now()) + "] Parsing...");
            NodeList nodeList = xml.getElementsByTagName("customer");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.hasChildNodes()) {
                    NodeList subNodes = nNode.getChildNodes();
                    for (int j = 0; j < subNodes.getLength(); j++) {
                        Node sNode = subNodes.item(j);
                        if (sNode.getNodeType() == Node.ELEMENT_NODE && sNode.getChildNodes().getLength() == 1 && allowedFields.contains(sNode.getNodeName().toLowerCase())) {
                            this.customer.put(sNode.getNodeName().toLowerCase(), sNode.getTextContent());
                        }
                    }
                    this.result.add(new HashMap(this.customer));
                    this.customer.clear();
                }
            }
            
            System.out.println("[" + dateFormat.format(LocalDateTime.now()) + "] Parsing completed.");
            if (this.result.isEmpty()) {
                System.out.println("No suitable info found.");
            } else {
                System.out.println("\nReading results...\n");
                IntStream.range(0, this.result.size()).forEach(_idx -> {
                    System.out.println("-- Customer #" + (_idx + 1) + " --");
                    this.allowedFields.forEach(_tag -> {
                        if (this.result.get(_idx).containsKey(_tag)) {
                            System.out.println("| " + _tag.substring(0,1).toUpperCase() + _tag.substring(1).replaceAll("_", " ") + " is " + this.result.get(_idx).get(_tag));
                        }
                    });
                    System.out.println("------ EOL ------\n");
                });

                System.out.println("...[" + dateFormat.format(LocalDateTime.now()) + "] Reading completed.");
            }
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ParserDOM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setDocument(String document) {
        this.document = document;
    }
}
