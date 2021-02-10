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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author SharpSchnitzel
 */
public class ParserSAX extends DefaultHandler implements CustomParser {
    
    private final List<String> allowedFields = Arrays.asList("last_name", "first_name", "middle_name", "birth_date", "position");
    private String tagValue = "";
    private final HashMap<String, String> customer = new HashMap<>();
    private final ArrayList<HashMap> result = new ArrayList<>();
    private String document;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
    
    public ParserSAX (String xml) {
        this.document = xml;
    }

    @Override
    public void startDocument() throws SAXException {  
        System.out.println("[" + this.dateFormat.format(LocalDateTime.now()) + "] Parsing...");  
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {        
        if (allowedFields.contains(qName.toLowerCase()) && !this.tagValue.isEmpty()) {
            this.customer.put(qName.toLowerCase(), this.tagValue);
            this.tagValue = "";
        } else if (!allowedFields.contains(qName.toLowerCase())) {
            if (!this.customer.isEmpty()) {
                this.result.add(new HashMap(this.customer));
                this.customer.clear();
            }
        }
   }

    @Override
    public void endDocument() throws SAXException {
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
    }
    
    @Override
    public void characters( char[] ch, int start, int length ) throws SAXException {
        this.tagValue = new String(ch, start, length);
    }
    
   
    @Override
    public void processXML() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new InputSource(new StringReader(this.document)), this);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ParserSAX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setDocument(String document) {
        this.document = document;
    }
}
