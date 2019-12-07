package edu.sustech.oj_server.moss;

import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.dao.SimDao;
import edu.sustech.oj_server.dao.SourceCodeDao;
import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.entity.Sim;
import edu.sustech.oj_server.entity.SourceCode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Plagiarism {

    @Autowired
    private SourceCodeDao sourceCodeDao;

    @Autowired
    private SimDao simDao;

    @Autowired
    private ProblemDao problemDao;

    private static SimDao sd;
    private static ProblemDao pd;
    private static SourceCodeDao scd;

    @PostConstruct
    void init(){
        sd=simDao;
        pd=problemDao;
        scd=sourceCodeDao;
    }

    public static List<Sim> parse(URL url) throws IOException {
        ArrayList<Sim> result=new ArrayList<>();
        Document document = Jsoup.connect(String.valueOf(url)).get();
        Elements elements=document.getElementsByTag("tr");
        System.out.println(elements.size());
        int cnt=0;
        for (Element element: elements) {
            Elements res = element.getElementsByTag("td");
            if (res.size() == 0) continue;

            int leftId = 0;
            int rightId = 0;
            String leftUser = "";
            String rightUser = "";
            int leftSim = 0;
            int rightSim = 0;

            for (Element ele : res) {
                Pattern pattern = Pattern.compile("([^ ()])+ \\((\\d)+%\\)");
                Matcher m = pattern.matcher(ele.text());
                if (m.find()) {
                    String cur = m.group(0);
                    String[] curs = cur.split(" ");

                    String[] su = curs[0].split("/");

                    int sid = Integer.parseInt(su[0]);
                    String uid = su[1];

                    int similar = Integer.parseInt(curs[1].substring(1, curs[1].length() - 2));

                    if (leftId > 0) {
                        rightId = sid;
                        rightUser = uid;
                        rightSim = similar;
                    } else {
                        leftId = sid;
                        leftUser = uid;
                        leftSim = similar;
                    }
                }
            }
            if (rightUser.compareTo(leftUser) == 0) continue;
            result.add(leftId > rightId ? new Sim(leftId, rightId, leftSim) : new Sim(rightId, leftId, rightSim));
        }
        return result;
    }
    public static String toLanguage(int languageId){
        String language;
        switch (languageId){
            case 0:
                language="c";
                break;
            case 1:
                language="cc";
                break;
            case 2:
                language="java";
                break;
            case 3:
                language="java";
                break;
            case 4:
                language="python";
                break;
            case 6:
                language="python";
                break;
            default:
                language="java";
        }
        return language;
    }
    public void run(int contestId) throws IOException, MossException {
        List<Problem> problems= pd.getProblemsInContest(contestId);
        Map<Integer, Sim> map = new HashMap<>();
        for (int language=0; language<=6; ++language){//for each language

            for (Problem pb: problems){// for each problem
//                if (pb.getId()!=1014) continue;
                List<SourceCode> sourceCodes=scd.getSourceCodeByCTL(contestId, pb.getId(), language);
                if (sourceCodes.size()==0) continue;

                URL result;
                while (true){
                    try{
                        SocketClient socketClient = new SocketClient();
                        socketClient.init();
                        socketClient.setLanguage(toLanguage(language));
                        for (SourceCode sourceCode: sourceCodes){
                            socketClient.addContent(sourceCode);
                        }
                        result=socketClient.request();
                        System.out.println(result);
                        break;
                    }catch (MossException e){
                        //do nothing
                    }
                }

                List<Sim> sims;
                while (true){
                    try{
                        sims = parse(result);
                        break;
                    }catch (java.net.SocketTimeoutException e){
                        //do nothing
                    }
                }

                for(Sim sim: sims){
                    if (!map.containsKey(sim.getS_id())) {
                        map.put(sim.getS_id(), sim);
                    }else if (map.get(sim.getS_id()).getPer() < sim.getPer()) {
                        map.put(sim.getS_id(), sim);
                    }
                }
            }
        }
        //insert into DB
        for (Sim sim: map.values()){
            if (sim.getPer()>=70){
                sd.insert(sim.getS_id(),sim.getSim_s_id(),sim.getPer());
            }
        }
    }



    public static void main(String[] args) throws IOException {
        List<Sim> sims = parse(new URL("http://moss.stanford.edu/results/771062598/"));

//        for(Sim sim: sims){
//            System.out.println(sim);
//        }
    }
}
