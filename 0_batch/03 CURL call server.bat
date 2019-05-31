@echo on
@set SITE=http://localhost:8080/ws
@set CURL=c:\tools\curl-7.58.0\bin\curl.exe
@set CURL=%CURL% -H "content-type: text/xml"

@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@powershell -Command Write-Host "Service Contract - WSDL file" -foreground "Green"
%CURL% "%SITE%/company.wsdl"
@echo.&pause

@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@powershell -Command Write-Host "Web Service Request - company" -foreground "Green"
%CURL% -d @requests/company.xml "%SITE%"
@echo.&pause

@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@powershell -Command Write-Host "Web Service Request - departments" -foreground "Green"
%CURL% -d @requests/departments.xml "%SITE%"
@echo.&pause

@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@powershell -Command Write-Host "Web Service Request - employees" -foreground "Green"
%CURL% -d @requests/employees.xml "%SITE%"
@echo.&pause

@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause