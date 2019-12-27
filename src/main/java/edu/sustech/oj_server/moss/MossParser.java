package edu.sustech.oj_server.moss;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MossParser {

    private URL url;
    private BufferedReader reader;

    private Integer upper_bound=50;

    public MossParser(URL url) throws IOException {
        this.url=url;
        InputStream in = url.openStream();
        this.reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public List<Info> parse() throws IOException {
        ArrayList<Info> infos = new ArrayList<>();
        Info result;
        while ((result = getTableRow())!=null){
            if (result.isTheSameUser() || result.getSim()<upper_bound) continue; //not significant enough or source from the same one
            infos.add(result);
        }
        reader.close();
        return infos;
    }
    public Info getTableRow() throws IOException {
        Pattern pattern = Pattern.compile("<TD><A HREF=\"([^ \"]+)\">([^<]+)</A>");
        String line;
        int p=0;
        int uid1=0, uid2=0, sim1=0, sim2=0;
        String uname1=null, uname2=null, url1=null, url2=null;
        while ((line = reader.readLine())!=null){
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()){
                String[] result = matcher.group(2).split(" ");
                String[] idname = result[0].split("/");

                int id=Integer.parseInt(idname[0]);
                String name=idname[1];
                int sim = Integer.parseInt(result[1].substring(1, result[1].length()-2));
                String url = matcher.group(1);

                if (p++==0){
                    uid1=id;
                    sim1=sim;
                    uname1=name;
                    url1=url;
                }else{
                    uid2=id;
                    sim2=sim;
                    uname2=name;
                    url2=url;
                }
                if (p>1) {
                    if (uid1>uid2) {
                        return new Info(uid1, uid2, uname1, uname2, sim1, new URL(url1));
                    }else{
                        return new Info(uid2, uid1, uname2, uname1, sim2, new URL(url2));
                    }
                }
            }
        }
        return null;
    }
}
