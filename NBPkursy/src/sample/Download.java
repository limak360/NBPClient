package sample;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class Download {

    String line = null;
    public String download(String adress) throws IOException{
        try (InputStream in = URI.create(adress).toURL().openStream()) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }
    }

    public String downloadTable(String adress) throws IOException{
        try (InputStream in = URI.create(adress).toURL().openStream()) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.substring(1,stringBuilder.length()-1);
        }
    }
}
