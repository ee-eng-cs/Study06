@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set M2_HOME=c:\tools\apache-maven-3.6.0
pushd %cd%
cd ..
call %M2_HOME%\bin\mvn --projects soap-ws-client clean install spring-boot:run
pause
popd