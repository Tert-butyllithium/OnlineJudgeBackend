package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsiteController {

    @RequestMapping("/api/website")
    public String getWebsite() {
        return "{\n" +
                "    \"error\": null,\n" +
                "    \"data\": {\n" +
                "        \"website_base_url\": \"https:/lanran.club\",\n" +
                "        \"website_name\": \"LanranForces Online Judge\",\n" +
                "        \"website_name_shortcut\": \"Lanranforces\",\n" +
                "        \"website_footer\": \"<a href=\\\"#\\\">粤ICP备19124180号-1</a>\",\n" +
                "        \"allow_register\": false,\n" +
                "        \"submission_list_show_all\": true\n" +
                "    }\n" +
                "}";
    }

    @RequestMapping("/api/announcement")
    public String getAnnouncement(){
        return "    {\n" +
                "    \"error\": null,\n" +
                "    \"data\": {\n" +
                "        \"results\": [],\n" +
                "        \"total\": 0\n" +
                "    }\n" +
                "    }";
    }

    @RequestMapping("/api/languages")
    public String getConfig(){
        return "{\n" +
                "    \"error\": null,\n" +
                "    \"data\": {\n" +
                "        \"languages\": [\n" +
                "            {\n" +
                "                \"spj\": {\n" +
                "                    \"config\": {\n" +
                "                        \"command\": \"{exe_path} {in_file_path} {user_out_file_path}\",\n" +
                "                        \"exe_name\": \"spj-{spj_version}\",\n" +
                "                        \"seccomp_rule\": \"c_cpp\"\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"spj-{spj_version}\",\n" +
                "                        \"src_name\": \"spj-{spj_version}.c\",\n" +
                "                        \"max_memory\": 1073741824,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 10000,\n" +
                "                        \"compile_command\": \"/usr/bin/gcc -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c11 {src_path} -lm -o {exe_path}\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"name\": \"C\",\n" +
                "                \"config\": {\n" +
                "                    \"run\": {\n" +
                "                        \"env\": [\n" +
                "                            \"LANG=en_US.UTF-8\",\n" +
                "                            \"LANGUAGE=en_US:en\",\n" +
                "                            \"LC_ALL=en_US.UTF-8\"\n" +
                "                        ],\n" +
                "                        \"command\": \"{exe_path}\",\n" +
                "                        \"seccomp_rule\": {\n" +
                "                            \"File IO\": \"c_cpp_file_io\",\n" +
                "                            \"Standard IO\": \"c_cpp\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"main\",\n" +
                "                        \"src_name\": \"main.c\",\n" +
                "                        \"max_memory\": 268435456,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 10000,\n" +
                "                        \"compile_command\": \"gcc -g -O2 -std=gnu11 -static ${files} -lm\"\n" +
                "                    },\n" +
                "                    \"template\": \"//PREPEND BEGIN\\n#include <stdio.h>\\n//PREPEND END\\n\\n//TEMPLATE BEGIN\\nint add(int a, int b) {\\n  // Please fill this blank\\n  return ___________;\\n}\\n//TEMPLATE END\\n\\n//APPEND BEGIN\\nint main() {\\n  printf(\\\"%d\\\", add(1, 2));\\n  return 0;\\n}\\n//APPEND END\"\n" +
                "                },\n" +
                "                \"description\": \"GCC 5.4\",\n" +
                "                \"content_type\": \"text/x-csrc\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"spj\": {\n" +
                "                    \"config\": {\n" +
                "                        \"command\": \"{exe_path} {in_file_path} {user_out_file_path}\",\n" +
                "                        \"exe_name\": \"spj-{spj_version}\",\n" +
                "                        \"seccomp_rule\": \"c_cpp\"\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"spj-{spj_version}\",\n" +
                "                        \"src_name\": \"spj-{spj_version}.cpp\",\n" +
                "                        \"max_memory\": 1073741824,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 5000,\n" +
                "                        \"compile_command\": \"g++ -g -O2 -std=gnu++17 -static ${files}\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"name\": \"C++\",\n" +
                "                \"config\": {\n" +
                "                    \"run\": {\n" +
                "                        \"env\": [\n" +
                "                            \"LANG=en_US.UTF-8\",\n" +
                "                            \"LANGUAGE=en_US:en\",\n" +
                "                            \"LC_ALL=en_US.UTF-8\"\n" +
                "                        ],\n" +
                "                        \"command\": \"{exe_path}\",\n" +
                "                        \"seccomp_rule\": \"c_cpp\"\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"main\",\n" +
                "                        \"src_name\": \"main.cpp\",\n" +
                "                        \"max_memory\": 536870912,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 10000,\n" +
                "                        \"compile_command\": \"g++ -g -O2 -std=gnu++17 -static ${files}\"\n" +
                "                    },\n" +
                "                    \"template\": \"//PREPEND BEGIN\\n#include <iostream>\\n//PREPEND END\\n\\n//TEMPLATE BEGIN\\nint add(int a, int b) {\\n  // Please fill this blank\\n  return ___________;\\n}\\n//TEMPLATE END\\n\\n//APPEND BEGIN\\nint main() {\\n  std::cout << add(1, 2);\\n  return 0;\\n}\\n//APPEND END\"\n" +
                "                },\n" +
                "                \"description\": \"G++ 5.4\",\n" +
                "                \"content_type\": \"text/x-c++src\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Java\",\n" +
                "                \"config\": {\n" +
                "                    \"run\": {\n" +
                "                        \"env\": [\n" +
                "                            \"LANG=en_US.UTF-8\",\n" +
                "                            \"LANGUAGE=en_US:en\",\n" +
                "                            \"LC_ALL=en_US.UTF-8\"\n" +
                "                        ],\n" +
                "                        \"command\": \"/usr/bin/java -cp {exe_dir} -XX:MaxRAM={max_memory}k -Djava.security.manager -Dfile.encoding=UTF-8 -Djava.security.policy==/etc/java_policy -Djava.awt.headless=true Main\",\n" +
                "                        \"seccomp_rule\": null,\n" +
                "                        \"memory_limit_check_only\": 1\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"Main\",\n" +
                "                        \"src_name\": \"Main.java\",\n" +
                "                        \"max_memory\": -1,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 5000,\n" +
                "                        \"compile_command\": \"javac -encoding UTF-8 -sourcepath . -d . ${files}\"\n" +
                "                    },\n" +
                "                    \"template\": \"//PREPEND BEGIN\\n//PREPEND END\\n\\n//TEMPLATE BEGIN\\n//TEMPLATE END\\n\\n//APPEND BEGIN\\n//APPEND END\"\n" +
                "                },\n" +
                "                \"description\": \"OpenJDK 11\",\n" +
                "                \"content_type\": \"text/x-java\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Python3\",\n" +
                "                \"config\": {\n" +
                "                    \"run\": {\n" +
                "                        \"env\": [\n" +
                "                            \"LANG=en_US.UTF-8\",\n" +
                "                            \"LANGUAGE=en_US:en\",\n" +
                "                            \"LC_ALL=en_US.UTF-8\"\n" +
                "                        ],\n" +
                "                        \"command\": \"/usr/bin/python3 {exe_path}\",\n" +
                "                        \"seccomp_rule\": \"general\"\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"__pycache__/solution.cpython-35.pyc\",\n" +
                "                        \"src_name\": \"solution.py\",\n" +
                "                        \"max_memory\": 134217728,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 10000,\n" +
                "                        \"compile_command\": \"python3 -m py_compile ${files}\"\n" +
                "                    },\n" +
                "                    \"template\": \"//PREPEND BEGIN\\n//PREPEND END\\n\\n//TEMPLATE BEGIN\\n//TEMPLATE END\\n\\n//APPEND BEGIN\\n//APPEND END\"\n" +
                "                },\n" +
                "                \"description\": \"Python 3.6.8\",\n" +
                "                \"content_type\": \"text/x-python\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Kotlin\",\n" +
                "                \"config\": {\n" +
                "                    \"run\": {\n" +
                "                        \"env\": [\n" +
                "                            \"LANG=en_US.UTF-8\",\n" +
                "                            \"LANGUAGE=en_US:en\",\n" +
                "                            \"LC_ALL=en_US.UTF-8\"\n" +
                "                        ],\n" +
                "                        \"command\": \"kotlin -Dfile.encoding=UTF-8 -J-XX:+UseSerialGC -J-Xss64m -J-Xms1920m -J-Xmx1920m\",\n" +
                "                        \"seccomp_rule\": \"general\"\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"Main\",\n" +
                "                        \"src_name\": \"Main.kt\",\n" +
                "                        \"max_memory\": 134217728,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 10000,\n" +
                "                        \"compile_command\": \"kotlinc -d . ${files}\"\n" +
                "                    },\n" +
                "                    \"template\": \"//PREPEND BEGIN\\n//PREPEND END\\n\\n//TEMPLATE BEGIN\\n//TEMPLATE END\\n\\n//APPEND BEGIN\\n//APPEND END\"\n" +
                "                },\n" +
                "                \"description\": \"Kotlin 1.3.50\",\n" +
                "                \"content_type\": \"text/x-kotlin\"\n" +
                "            }" +
                "        ],\n" +
                "        \"spj_languages\": [\n" +
                "            {\n" +
                "                \"spj\": {\n" +
                "                    \"config\": {\n" +
                "                        \"command\": \"{exe_path} {in_file_path} {user_out_file_path}\",\n" +
                "                        \"exe_name\": \"spj-{spj_version}\",\n" +
                "                        \"seccomp_rule\": \"c_cpp\"\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"spj-{spj_version}\",\n" +
                "                        \"src_name\": \"spj-{spj_version}.c\",\n" +
                "                        \"max_memory\": 1073741824,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 10000,\n" +
                "                        \"compile_command\": \"/usr/bin/gcc -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c11 {src_path} -lm -o {exe_path}\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"name\": \"C\",\n" +
                "                \"config\": {\n" +
                "                    \"run\": {\n" +
                "                        \"env\": [\n" +
                "                            \"LANG=en_US.UTF-8\",\n" +
                "                            \"LANGUAGE=en_US:en\",\n" +
                "                            \"LC_ALL=en_US.UTF-8\"\n" +
                "                        ],\n" +
                "                        \"command\": \"{exe_path}\",\n" +
                "                        \"seccomp_rule\": {\n" +
                "                            \"File IO\": \"c_cpp_file_io\",\n" +
                "                            \"Standard IO\": \"c_cpp\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"main\",\n" +
                "                        \"src_name\": \"main.c\",\n" +
                "                        \"max_memory\": 268435456,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 10000,\n" +
                "                        \"compile_command\": \"/usr/bin/gcc -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c11 {src_path} -lm -o {exe_path}\"\n" +
                "                    },\n" +
                "                    \"template\": \"//PREPEND BEGIN\\n#include <stdio.h>\\n//PREPEND END\\n\\n//TEMPLATE BEGIN\\nint add(int a, int b) {\\n  // Please fill this blank\\n  return ___________;\\n}\\n//TEMPLATE END\\n\\n//APPEND BEGIN\\nint main() {\\n  printf(\\\"%d\\\", add(1, 2));\\n  return 0;\\n}\\n//APPEND END\"\n" +
                "                },\n" +
                "                \"description\": \"GCC 5.4\",\n" +
                "                \"content_type\": \"text/x-csrc\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"spj\": {\n" +
                "                    \"config\": {\n" +
                "                        \"command\": \"{exe_path} {in_file_path} {user_out_file_path}\",\n" +
                "                        \"exe_name\": \"spj-{spj_version}\",\n" +
                "                        \"seccomp_rule\": \"c_cpp\"\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"spj-{spj_version}\",\n" +
                "                        \"src_name\": \"spj-{spj_version}.cpp\",\n" +
                "                        \"max_memory\": 1073741824,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 5000,\n" +
                "                        \"compile_command\": \"/usr/bin/g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++14 {src_path} -lm -o {exe_path}\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"name\": \"C++\",\n" +
                "                \"config\": {\n" +
                "                    \"run\": {\n" +
                "                        \"env\": [\n" +
                "                            \"LANG=en_US.UTF-8\",\n" +
                "                            \"LANGUAGE=en_US:en\",\n" +
                "                            \"LC_ALL=en_US.UTF-8\"\n" +
                "                        ],\n" +
                "                        \"command\": \"{exe_path}\",\n" +
                "                        \"seccomp_rule\": \"c_cpp\"\n" +
                "                    },\n" +
                "                    \"compile\": {\n" +
                "                        \"exe_name\": \"main\",\n" +
                "                        \"src_name\": \"main.cpp\",\n" +
                "                        \"max_memory\": 536870912,\n" +
                "                        \"max_cpu_time\": 3000,\n" +
                "                        \"max_real_time\": 10000,\n" +
                "                        \"compile_command\": \"/usr/bin/g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++14 {src_path} -lm -o {exe_path}\"\n" +
                "                    },\n" +
                "                    \"template\": \"//PREPEND BEGIN\\n#include <iostream>\\n//PREPEND END\\n\\n//TEMPLATE BEGIN\\nint add(int a, int b) {\\n  // Please fill this blank\\n  return ___________;\\n}\\n//TEMPLATE END\\n\\n//APPEND BEGIN\\nint main() {\\n  std::cout << add(1, 2);\\n  return 0;\\n}\\n//APPEND END\"\n" +
                "                },\n" +
                "                \"description\": \"G++ 5.4\",\n" +
                "                \"content_type\": \"text/x-c++src\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
    }
}
