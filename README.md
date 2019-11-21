# Our OnlineJudge Backend
>  An onlinejudge system based on Java and Vue, demo: https://lanran.club

## Overview
- An OJ as our OOAD project
- the frontend is based on [qduoj](https://github.com/QingdaoU/OnlineJudge), and the database is based on [HUSTOJ](https://github.com/zhblue/hustoj),  

## Progress

- [x] Problem, Contest, Submit, Rank 
- [x] Support Kotlin and Python 
- [ ] Support Special Judge
- [ ] Create Problem and view the test data
- [ ] ~~Code plagiarism detection based on abstract syntax tree~~ 

## User guide
1. update your jdk to 11
2. open `IDEA` and `File-> New -> New Project from Version Control -> Git`
3. modify the file `src/main/resources/application.properties` to set up your port (default 1235) and your database
4. We will announce the one-click deployment script after the project is completed.
5. judge server: https://github.com/Isaac-Graham/CS309_OOAD_online_judge
