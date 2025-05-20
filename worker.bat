@echo off
setlocal enabledelayedexpansion

REM Worker batch file for Java compilation
set "WORKER_ID=%~1"
set "TASK_LIST=%~2"
set "WORKER_DIR=%~3"
set "BIN_DIR=%~4"

set "WORKER_CACHE=%WORKER_DIR%\worker_%WORKER_ID%.cache"
set "WORKER_ERRORS=%WORKER_DIR%\worker_%WORKER_ID%.errors"
set "WORKER_RESULT=%WORKER_DIR%\worker_%WORKER_ID%.result"
set "WORKER_LOG=%WORKER_DIR%\worker_%WORKER_ID%.log"

echo Worker %WORKER_ID% started > "%WORKER_LOG%"
echo 0 > "%WORKER_RESULT%"

set "WORKER_COMPILED=0"

REM Check that Java compiler is available
where javac >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Worker %WORKER_ID%: javac not found in PATH >> "%WORKER_LOG%"
    echo 'javac' is not recognized as an internal or external command, >> "%WORKER_ERRORS%"
    echo operable program or batch file. >> "%WORKER_ERRORS%"
    echo Done > "%WORKER_DIR%\worker_%WORKER_ID%.done"
    exit /b 1
)

if not exist "%TASK_LIST%" (
    echo Worker %WORKER_ID%: Task list not found at %TASK_LIST% >> "%WORKER_LOG%"
    echo Done > "%WORKER_DIR%\worker_%WORKER_ID%.done"
    exit /b 1
)

echo Worker %WORKER_ID%: Processing task list %TASK_LIST% >> "%WORKER_LOG%"
echo Worker %WORKER_ID%: Will output to %BIN_DIR% >> "%WORKER_LOG%"

for /f "usebackq delims=" %%F in ("%TASK_LIST%") do (
    set "FILE=%%F"
    set "REL_PATH=%%~dpnxF"
    set "CLASSNAME=%%~nF"
    
    echo Worker %WORKER_ID%: Compiling !CLASSNAME! >> "%WORKER_LOG%"
    
    REM Compile the file and capture errors
    javac -d "%BIN_DIR%" "!FILE!" 2>"%WORKER_DIR%\temp_errors_%WORKER_ID%.txt"
    set "ERROR_CODE=!ERRORLEVEL!"
    
    if !ERROR_CODE! NEQ 0 (
        echo Worker %WORKER_ID%: Compilation failed for !CLASSNAME! >> "%WORKER_LOG%"
        type "%WORKER_DIR%\temp_errors_%WORKER_ID%.txt" >> "%WORKER_ERRORS%"
        
        REM Add to the errors object in cache - use simpler path
        set "SIMPLE_PATH=!FILE:%CD%\=!"
        set "SIMPLE_PATH=!SIMPLE_PATH:\=/!"
        for %%T in ("!FILE!") do set "FILE_TIME=%%~tT"
        echo         "!SIMPLE_PATH!": { "status": "failed", "timestamp": "!FILE_TIME!" } >> "%WORKER_CACHE%"
    ) else (
        echo Worker %WORKER_ID%: Successfully compiled !CLASSNAME! >> "%WORKER_LOG%"
        set /a "WORKER_COMPILED+=1"
        
        REM Use simpler path format
        set "SIMPLE_PATH=!FILE:%CD%\=!"
        set "SIMPLE_PATH=!SIMPLE_PATH:\=/!"
        for %%T in ("!FILE!") do set "FILE_TIME=%%~tT"
        
        REM Add to the files object in cache
        echo         "!SIMPLE_PATH!": { "status": "success", "timestamp": "!FILE_TIME!" } >> "%WORKER_CACHE%"
    )
    
    if exist "%WORKER_DIR%\temp_errors_%WORKER_ID%.txt" del "%WORKER_DIR%\temp_errors_%WORKER_ID%.txt"
)

echo %WORKER_COMPILED% > "%WORKER_RESULT%"
echo Worker %WORKER_ID% completed with %WORKER_COMPILED% files compiled >> "%WORKER_LOG%"
echo Done > "%WORKER_DIR%\worker_%WORKER_ID%.done"
exit /b 0
