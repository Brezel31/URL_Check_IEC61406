package com.company;
/*
 * MIT License
 * Copyright <2022> <Peter Geiger>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
        checkCase(splittedDomain[0]);
        if (args.length > 1) {
            API_KEY = args[1];
            checkDomain(splittedDomain[0]);
        } else {
            System.out.println("No WHOISXML-API-Key available; check for free demo key: https://whois.whoisxmlapi.com/");
        }

        checkString(DOMAIN);
    }
    private static void checkCase(String yourDomain){
        if (!yourDomain.toLowerCase().equals(yourDomain)){
            System.out.println("Capital letters are not allowed in the path");
        }
    }

    private static void checkDomain(String yourDomain) {

        String url = "https://domain-availability.whoisxmlapi.com/api/v1?"
                + "apiKey=" + API_KEY + "&domainName=" + yourDomain;

        try (java.util.Scanner s =
                     new java.util.Scanner(new java.net.URL(url).openStream())) {
            // if domainAvailability = "UNAVAILABLE" everything is ok, it seems to be a valid domain
            System.out.println(s.useDelimiter("\\A").next());
        } catch (MalformedURLException error) {
            System.out.println("Error during URL creation:  " + yourDomain);
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
            System.out.println("May contain punycode");
        }if (url.length() >255){
            System.out.println("URL has more than the allowed 255 characters");
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9#,$&'()*+\\-./~\\[\\]=?:;!_@]");
        Matcher m = p.matcher(url);
        while (m.find()){
            // findings are not allowed characters, if nothing is found, everything is fine
           System.out.println("Invalid character in place: " + m.start() + " - " + m.end());
        }
    }
}
