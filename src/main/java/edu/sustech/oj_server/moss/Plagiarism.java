package edu.sustech.oj_server.moss;

import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.dao.SimDao;
import edu.sustech.oj_server.dao.SourceCodeDao;
import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.entity.SourceCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


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

    public static String toLanguage(int languageId){
        String language;
        switch (languageId){
            case 0:
                language="c";
                break;
            case 1:
                language="cc";
                break;
            case 4:
            case 6:
                language="python";
                break;
            case 3:
            case 2:
            default:
                language="java";
        }
        return language;
    }
    public void run(int contestId) throws IOException {
        List<Problem> problems= pd.getProblemsInContest(contestId);
        ArrayList<Info> result = new ArrayList<>();
        for (int language=0; language<=6; ++language){//for each language
            for (Problem pb: problems){// for each problem
                URL url;
                if ((url=getMossURL(contestId, pb.getId(), language))==null) continue;
                MossParser mossParser = new MossParser(url);
                List<Info> infos = mossParser.parse();
                result.addAll(infos);
            }
        }
        result.forEach(info -> {sd.insert(info.getSolId(), info.getSimSolId(), info.getSim());});
    }
    private URL getMossURL(int contestId, int problem, int language) throws IOException {
        List<SourceCode> sourceCodes=scd.getAcceptedByCTL(contestId, problem, language);
        if (sourceCodes.size()==0) return null;
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
                break;
            }catch (MossException e){
                System.out.println("MossException: Retrying");
                e.printStackTrace();
            }catch (IOException e){
                System.out.println("Connection refused: Retrying");
                e.printStackTrace();
            }
        }
        return result;
    }

}
