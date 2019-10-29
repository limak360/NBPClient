package sample;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Adresy {
    String tabelaA = "http://api.nbp.pl/api/exchangerates/tables/A";
    String tabelaB = "http://api.nbp.pl/api/exchangerates/tables/B";
    //date pobierac
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    String formattedDate = formatter.format(LocalDate.now());
    String cena7ostatnichDni;

    public Map adresy(){
        Map<String, String> adresy = new HashMap<>();
        adresy.put("tabelaA",tabelaA);
        adresy.put("tabelaB",tabelaB);
        adresy.put("ceny7ostatnichDni",cena7ostatnichDni);
        return adresy;
    }

    private String loPanie(String date){
        if(!(Integer.parseInt(date.substring(8,10))-7>0)) System.out.println("to masz problem i tyle elo");
            return date.substring(0,8)+(Integer.parseInt(date.substring(8,10))-9);
    }

    public String days(String code) {
        return "http://api.nbp.pl/api/exchangerates/rates/A/"+code+"/" + loPanie(formattedDate) + "/" + formattedDate;
    }

}
