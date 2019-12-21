package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.PrivilegeDao;
import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.toolclass.TestCaseUpload;
import edu.sustech.oj_server.toolclass.TestCases;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/api/admin")
public class AdminProblemController {

    @Autowired
    private ProblemDao problemDao;

    @GetMapping("/problem")
    public ReturnType<?> getContests(@RequestParam(value = "id", required = false) Integer id,
                                     @RequestParam(value = "limit", required = false) Integer limit,
                                     @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                     @RequestParam(value = "keyword", required = false) String keyword) {
        if (id == null) {
            var res = problemDao.listAllProblemsForAdmin(keyword, offset, limit);
            return new ReturnType<>(new ReturnListType<>(res, problemDao.getNumForAdmin(keyword)));
        } else {
            var res = problemDao.getProblem(id);
            Objects.requireNonNull(res.getSamples()).addAll(problemDao.getExtraSamples(id));
            return new ReturnType<>(res);
        }
    }

    @PostMapping("/test_case")
    public ReturnType<?> PostTestCases(@ModelAttribute TestCaseUpload testCaseUpload, HttpServletRequest request) {
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType<>("login-required", "Please login in first");
        }
//        System.out.println(testCase);
        var file = testCaseUpload.getFile();
        byte[] buffer = new byte[1024];
        String id = UUID.randomUUID().toString();
        TestCases res = new TestCases();
        res.setId(id);
//        ArrayList<TestCases.Case> cases=new ArrayList<>();
        TreeMap<String, TestCases.Case> cases = new TreeMap<>();
        Path folder = Path.of("tmp" + File.separator + id);
        try {
            Files.createDirectories(folder);
            ZipInputStream zis = new ZipInputStream(file.getInputStream());
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String filename = ze.getName();
                if(ze.isDirectory()){
                    continue;
                }
                if (filename.endsWith(".in")) {
                    String filenameWithoutExt = filename.substring(0, filename.length() - 3);
                    if (cases.containsKey(filenameWithoutExt)) {
                        var tmp = cases.get(filenameWithoutExt);
                        tmp.setInput_name(filename);
                        tmp.setInput_size(ze.getSize());
                    } else {
                        var tmp = new TestCases.Case();
                        tmp.setInput_name(filename);
                        tmp.setInput_size(ze.getSize());
                        cases.put(filenameWithoutExt, tmp);
                    }
                } else if (filename.endsWith(".out")) {
                    String filenameWithoutExt = filename.substring(0, filename.length() - 4);
                    if (cases.containsKey(filenameWithoutExt)) {
                        var tmp = cases.get(filenameWithoutExt);
                        tmp.setOutput_name(filename);
                        tmp.setOutput_size(ze.getSize());
                    } else {
                        var tmp = new TestCases.Case();
                        tmp.setOutput_name(filename);
                        tmp.setOutput_size(ze.getSize());
                        cases.put(filenameWithoutExt, tmp);
                    }
                }
                File newFile = new File(folder.toString() + File.separator + filename);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnType<>("error", e.getMessage());
        }
        res.setInfo(new ArrayList<>(cases.values()));
        return new ReturnType<>(res);
    }

    @PostMapping("/problem")
    public ReturnType<?> createProblem(@RequestBody Problem problem, HttpServletRequest request) {
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType<>("login-required", "Please login in first");
        }

        addBlank(problem);
        if (Objects.requireNonNull(problem.getSamples()).size() == 0) {
            return new ReturnType<>("error", "No sample");
        }
        try {
            problemDao.insertProblem(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnType<>("error", e.getMessage());
        }

        ReturnType<?> e = moveTestData(problem);
        if (e != null) return e;
//        System.out.println(problem);
        return new ReturnType<>(null);
    }

    @PutMapping("/problem")
    public ReturnType<?> editProblem(@RequestBody Problem problem, HttpServletRequest request){
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType<>("login-required", "Please login in first");
        }
        addBlank(problem);
        if (Objects.requireNonNull(problem.getSamples()).size() == 0) {
            return new ReturnType<>("error", "No sample");
        }
        try {
            problemDao.updateProblem(problem);
        }catch (Exception e){
            e.printStackTrace();
            return new ReturnType<>("error",e.getMessage());
        }
        problemDao.clearExtraSamples(problem.getId());

        ReturnType<?> e = moveTestData(problem);
        if (e != null) return e;
        return new ReturnType<>(null);
    }

    private ReturnType<?> moveTestData(@RequestBody Problem problem) {
        if (Objects.requireNonNull(problem.getSamples()).size() > 1) {
            var tmp = problem.getSamples();
            for (int i = 1; i < tmp.size(); i++) {
                problemDao.InsertExtraSamples(problem.getId(), tmp.get(i).getInput(), tmp.get(i).getOutput());
            }
        }
        //Not, Here I move the file to a local path (SHOULD BE SERVICE)
        if (problem.getTest_case_id() != null && problem.getTest_case_id().length() > 0) {
            Path dest = Path.of("/home/judge/data/" + problem.getId());
            try {
                deletePath(dest);
                moveFolder(Path.of("tmp" + File.separator + problem.getTest_case_id()), dest);
            } catch (Exception e) {
                e.printStackTrace();
                return new ReturnType<>("error", e.getMessage());
            }
        }
        return null;
    }

    private void deletePath(Path path) throws IOException{
        if(path==null) return;
        if(!Files.exists(path)){
            return;
        }
        Files.walk(path).forEach(file->{
            if(file.toFile().isDirectory()){
                try {
                    deletePath(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Files.deleteIfExists(path);
    }

    private void addBlank(Problem problem){
        problem.setDescription(addBlank(Objects.requireNonNull(problem.getDescription())));
        problem.setInput_description(addBlank(Objects.requireNonNull(problem.getInput_description())));
        problem.setOutput_description(addBlank(Objects.requireNonNull(problem.getOutput_description())));
        problem.setHint(addBlank(Objects.requireNonNull(problem.getHint())));
    }

    private String addBlank(String str){
        return str.replaceAll("<"," <").replaceAll(">","> ");
    }


    private void moveFolder(Path src, Path dest) throws IOException {
        Files.walk(src).forEach(source -> {
            try {
                if (!Files.isDirectory(source))
                    Files.move(source, dest.resolve(src.relativize(source)), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        Files.deleteIfExists(src);
        deletePath(src);
    }

}
