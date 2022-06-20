package com.company;


import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String DOMAIN = "";
    public static  String API_KEY = "";


    public static void main(String[] args) {
        if (args.length > 0) {
            DOMAIN = args[0];
        } else {
            System.out.println("usage: java Main param1:url param2:API-Key");
        }
        String[] splittedDomain;
        splittedDomain = getSplittedDomain(DOMAIN);
        if (args.length > 1) {
            API_KEY = args[1];
            checkDomain(splittedDomain[0]);
        } else {
            System.out.println("Kein WHOISXML-API-Key vorhanden; check for free demo key: https://whois.whoisxmlapi.com/");
        }

        checkString(DOMAIN);
    }

    private static void checkDomain(String yourDomain) {

        String url = "https://domain-availability.whoisxmlapi.com/api/v1?"
                + "apiKey=" + API_KEY + "&domainName=" + yourDomain;
        if (!yourDomain.toLowerCase().equals(yourDomain)){
            System.out.println("Großbuchstaben sind im Path nicht erlaubt"); //"Capital letters are not allowed in the path"
        }
        try (java.util.Scanner s =
                     new java.util.Scanner(new java.net.URL(url).openStream())) {
            // if domainAvailability = "UNAVAILABLE" everything is ok, it seems to be a valid domain
            System.out.println(s.useDelimiter("\\A").next());
        } catch (MalformedURLException error) {
            System.out.println("Fehler bei der URL-Erstellung: " + yourDomain); //"Error during URL creation: "
        } catch (Exception exception) {
            System.out.println("Exception for: " + yourDomain); //"URL not valid: "
        }
    }

    private static String[] getSplittedDomain(String yourDomain){
        String[] splittedStrings = new String[0];

        if (yourDomain.contains("https://")){
             splittedStrings= yourDomain.split("https://") ;
        }
        if (yourDomain.contains("http://")) {
             splittedStrings = yourDomain.split("http://");
        }
        if (splittedStrings.length>1){
             splittedStrings = splittedStrings[1].split("/", 2);
        }
        else{
            splittedStrings = yourDomain.split("/", 2);
        }
        return splittedStrings;
    }
    private static void checkString(String url){
        if (url.contains("xn--")){
            System.out.println("Kann Punycode enthalten"); //"May contain punycode"
        }if (url.length() >255){
            System.out.println("URL hat mehr als die erlaubten 255 Zeichen"); //"URL has more than the allowed 255 characters"
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9#,$&'()*+\\-./~\\[\\]=?:;!_@]");
        Matcher m = p.matcher(url);
        while (m.find()){
            // findings are not allowed characters, if nothing is found, everything is fine
           System.out.println("Ungültiges Zeichen an Stelle: " + m.start() + " - " + m.end()); //"Invalid character in place: "
        }
    }
}