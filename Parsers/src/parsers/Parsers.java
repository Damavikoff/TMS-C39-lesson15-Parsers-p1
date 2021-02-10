/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import custom.util.xml.CustomParser;
import custom.util.xml.ParserDOM;
import custom.util.xml.ParserSAX;

/**
 *
 * @author SharpSchnitzel
 */
public class Parsers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<customers>" +
                            "<customer></customer>" +
                            "<customer>" +
                                "<last_name>Korvin-Krukovskaya</last_name>" +
                                "<first_name>Sofya</first_name>" +
                                "<middle_name>Vasilyevna</middle_name>" +
                                "<birth_date>15-01-1850</birth_date>" +
                                "<position>Mathematician</position>" +
                            "</customer>" +
                            "<customer>" +
                                "<last_name>Washington</last_name>" +
                                "<first_name>George</first_name>" +
                                "<birth_date>30-04-1789</birth_date>" +
                                "<position>Mr. President</position>" +
                            "</customer>" +
                            "<customer>" +
                                "<last_name>Bonaparte</last_name>" +
                                "<middle_name></middle_name>" +
                                "<first_name>Napoléon</first_name>" +
                                "<birth_date>15-08-1769</birth_date>" +
                                "<position>Emperor</position>" +
                            "</customer>" +
                        "</customers>";
        
        CustomParser documentSAX = new ParserSAX(xml);
        CustomParser documentDOM = new ParserDOM(xml);
        System.out.println("### | SAX | ###");
        documentSAX.processXML();
        System.out.println("\n\n### | DOM | ###");
        documentDOM.processXML();
        
        //### | SAX | ###
        //[10-02-2021 21:12:05:453] Parsing...
        //[10-02-2021 21:12:05:457] Parsing completed.
        //
        //Reading results...
        //
        //-- Customer #1 --
        //| Last name is Korvin-Krukovskaya
        //| First name is Sofya
        //| Middle name is Vasilyevna
        //| Birth date is 15-01-1850
        //| Position is Mathematician
        //------ EOL ------
        //
        //-- Customer #2 --
        //| Last name is Washington
        //| First name is George
        //| Birth date is 30-04-1789
        //| Position is Mr. President
        //------ EOL ------
        //
        //-- Customer #3 --
        //| Last name is Bonaparte
        //| First name is Napoléon
        //| Birth date is 15-08-1769
        //| Position is Emperor
        //------ EOL ------
        //
        //...[10-02-2021 21:12:05:461] Reading completed.
        //
        //
        //### | DOM | ###
        //[10-02-2021 21:12:05:467] Parsing...
        //[10-02-2021 21:12:05:469] Parsing completed.
        //
        //Reading results...
        //
        //-- Customer #1 --
        //| Last name is Korvin-Krukovskaya
        //| First name is Sofya
        //| Middle name is Vasilyevna
        //| Birth date is 15-01-1850
        //| Position is Mathematician
        //------ EOL ------
        //
        //-- Customer #2 --
        //| Last name is Washington
        //| First name is George
        //| Birth date is 30-04-1789
        //| Position is Mr. President
        //------ EOL ------
        //
        //-- Customer #3 --
        //| Last name is Bonaparte
        //| First name is Napoléon
        //| Birth date is 15-08-1769
        //| Position is Emperor
        //------ EOL ------
        //
        //...[10-02-2021 21:12:05:470] Reading completed.
        
    }
    
}
