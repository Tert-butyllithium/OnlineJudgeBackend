# Our OnlineJudge Backend
>  An onlinejudge system based on Java and Vue, demo: https://ac.lanran.club (needs IPv6)

## Overview
- An OJ as our ***Object-oriented analysis and design*** project
- The frontend is based on [qduoj](https://github.com/QingdaoU/OnlineJudge), and the database is based on [HUSTOJ](https://github.com/zhblue/hustoj)
- The first version is an adapter! :)

## Progress

- [x] Problem, Contest, Submit, Rank 
- [x] Support Kotlin and Python 
- [x] Support Special Judge
- [x] Support freeze score board before the end of contest
- [x] Show everyone accepted problems in contest to send balloons to contestants 
- [ ] Create Problem and view/edit/upload/download the test data
- [ ] ~~Code plagiarism detection based on abstract syntax tree~~ 

## User guide
1. update your jdk to version 11
2. open `IDEA` and `File-> New -> New Project from Version Control -> Git`
3. edit the file `src/main/resources/application.properties` to set up your port (default 1235) and your database settings
4. We will announce the one-click deployment script after the project is completed.
5. judge server: https://github.com/Isaac-Graham/CS309_OOAD_online_judge
