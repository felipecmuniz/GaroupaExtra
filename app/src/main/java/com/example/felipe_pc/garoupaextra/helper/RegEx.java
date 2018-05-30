package com.example.felipe_pc.garoupaextra.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A broadcast receiver who listens for incoming SMS
 */

public class RegEx {

    private String texto;

   public RegEx(String texto){
       this.texto = texto;
   }

    public String buscarRegex(String pattern) {
        Pattern padrao = Pattern.compile(pattern);

        Matcher matcher = padrao.matcher(this.texto);
        String val = "";
        if (matcher.find()) {
            val = matcher.group(1);
            return val;
        }
        return "";
    }
}
