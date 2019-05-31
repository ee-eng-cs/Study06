For the 'Study06_soap-ws-server' the directory "...\target\generated-sources\xjc" 
added manually to "Project"/"Properties"/"Java Build Path".
-----------------------------------------------------------------------------------------
The 'soap-ws-server' module has to be started with "01 MVN (server) run.bat"
for building 'soap-ws-client' with Maven using "02 MVN (client) run.bat".
-----------------------------------------------------------------------------------------
The 'soap-ws-server' module has to be started with "01 MVN (server) run.bat"
for building Javadoc with Maven using "04 MVN Javadoc.bat".
-----------------------------------------------------------------------------------------
In Eclipse the 'soap-ws-client' module error
"Execution default of goal org.jvnet.jaxb2.maven2:maven-jaxb2-plugin:0.14.0:generate failed."
is removed with  menu "Project"/"Clean...".

But BEFORE executing that menu "Project"/"Clean..." run server as Maven build
the 'Study06_soap-ws-server' with goals "clean install spring-boot:run"
-----------------------------------------------------------------------------------------