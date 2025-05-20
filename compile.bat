@echo off
setlocal enabledelayedexpansion

REM Create escape character (ASCII 27) with a special trick
for /F %%a in ('echo prompt $E ^| cmd') do set "ESC=%%a"

REM Set up colors with proper escape character
set "RED=%ESC%[91m"
set "GREEN=%ESC%[92m"
set "YELLOW=%ESC%[93m"
set "BLUE=%ESC%[94m"
set "MAGENTA=%ESC%[95m"
set "CYAN=%ESC%[96m"
set "WHITE=%ESC%[97m"
set "RESET=%ESC%[0m"

REM Capture start time for timing statistics
set "START_TIME=%time%"

REM Try to enable VT100 sequences using PowerShell if available
powershell -Command "& {if (Get-Command -Name 'Set-ItemProperty' -ErrorAction SilentlyContinue) { Set-ItemProperty -Path 'HKCU:\Console' -Name 'VirtualTerminalLevel' -Value 1 -Type DWORD -Force }}" >nul 2>&1

REM Use Windows native color as fallback in case ANSI colors don't work
color 0A

REM Cache directory and files
set "CACHE_DIR=.cache"
set "CACHE_FILE=%CACHE_DIR%\compile_cache.json"
set "ERROR_FILE=%CACHE_DIR%\errors.txt"
set "LOG_FILE=%CACHE_DIR%\compile_log.txt"
set "TIMESTAMP_FILE=%CACHE_DIR%\current.timestamp"

echo %CYAN%Java Compilation Tool%RESET%
echo %CYAN%======================%RESET%

REM Create cache directory if it doesn't exist
if not exist "%CACHE_DIR%" (
    mkdir "%CACHE_DIR%" 2>nul
    echo %YELLOW%Created cache directory%RESET%
)

REM Create bin directory if it doesn't exist
if not exist "bin" (
    mkdir "bin" 2>nul
    echo %GREEN%Created bin directory%RESET%
)

REM Save current timestamp
for /f "tokens=1-4 delims=/ " %%a in ('date /t') do set "CURRENT_DATE=%%a %%b/%%c/%%d"
for /f "tokens=1-3 delims=: " %%a in ('time /t') do set "CURRENT_TIME=%%a:%%b:%%c"
set "BUILD_TIMESTAMP=%CURRENT_DATE% %CURRENT_TIME%"
echo %BUILD_TIMESTAMP% > "%TIMESTAMP_FILE%"

REM Log compilation start
echo Compilation started > "%LOG_FILE%"
echo > "%ERROR_FILE%"

REM Count total number of Java files for progress indicator
set "TOTAL_FILES=0"
set "COMPILED_FILES=0"
if "%~1"=="" (
    for /r %%F in (*.java) do set /a "TOTAL_FILES+=1"
) else (
    set "TOTAL_FILES=1"
)

echo %CYAN%Found %TOTAL_FILES% Java files to process%RESET%

REM Set the maximum number of parallel compilation tasks
set "MAX_THREADS=4"

REM Process specific file or all files
if "%~1"=="" (
    echo %YELLOW%Smart compilation mode with threading - using up to %MAX_THREADS% threads...%RESET%
    
    REM Load cache if exists
    set "CACHE_EXISTS=false"
    if exist "%CACHE_FILE%" set "CACHE_EXISTS=true"
    
    REM Create a temporary file to store file entries
    set "TEMP_FILES=%CACHE_DIR%\temp_files.txt"
    if exist "!TEMP_FILES!" del "!TEMP_FILES!"
    
    REM Create worker directories
    set "WORKER_DIR=%CACHE_DIR%\workers"
    if not exist "!WORKER_DIR!" mkdir "!WORKER_DIR!" 2>nul
    
    REM Clean any previous worker files
    if exist "!WORKER_DIR!\*.worker" del "!WORKER_DIR!\*.worker" >nul 2>&1
    if exist "!WORKER_DIR!\*.done" del "!WORKER_DIR!\*.done" >nul 2>&1
    if exist "!WORKER_DIR!\*.result" del "!WORKER_DIR!\*.result" >nul 2>&1
    
    REM Create a list of all Java files for processing
    set "ALL_FILES=!WORKER_DIR!\allfiles.list"
    if exist "!ALL_FILES!" del "!ALL_FILES!"
    echo Creating file list at: !ALL_FILES!
    
    REM First pass - check which files need compilation and create the list
    echo %YELLOW%Checking which files need compilation...%RESET%
    set "FILES_TO_COMPILE=0"
    
    for /r %%F in (*.java) do (
        set "FILE=%%F"
        set "REL_PATH=%%~dpnxF"
        set "CLASSNAME=%%~nF"
        set "NEED_COMPILE=true"
        set "CLASS_PATH=bin\!CLASSNAME!.class"
        
        REM Check if .class file exists and is newer than .java file
        if exist "!CLASS_PATH!" (
            for %%A in ("!FILE!") do set "JAVA_TIME=%%~tA"
            for %%B in ("!CLASS_PATH!") do set "CLASS_TIME=%%~tB"
            
            REM Compare timestamps (crude comparison, but works in most cases)
            if "!CLASS_TIME!" GTR "!JAVA_TIME!" (
                set "NEED_COMPILE=false"
                
                REM Still add to cache even if not compiled (do this in a separate temp file)
                for %%T in ("!FILE!") do set "FILE_TIME=%%~tT"
                echo         "!REL_PATH!": { "status": "cached", "timestamp": "!FILE_TIME!" } >> "!TEMP_FILES!"
            )
        )
        
        if "!NEED_COMPILE!"=="true" (
            echo !FILE!>>!ALL_FILES!
            set /a "FILES_TO_COMPILE+=1"
        ) else (
            echo %GREEN%Skipping !CLASSNAME! - already up to date%RESET%
        )
    )
    
    echo %CYAN%Found !FILES_TO_COMPILE! files that need compilation%RESET%
    
    if !FILES_TO_COMPILE! GTR 0 (
        REM Check if the all files list was created successfully
        if not exist "!ALL_FILES!" (
            echo %RED%Error: Could not create file list at !ALL_FILES!%RESET%
            echo %YELLOW%Falling back to single-threaded compilation%RESET%
            
            REM Single-threaded fallback
            for /r %%F in (*.java) do (
                set "FILE=%%F"
                set "REL_PATH=%%~dpnxF"
                set "CLASSNAME=%%~nF"
                set "NEED_COMPILE=true"
                set "CLASS_PATH=bin\!CLASSNAME!.class"
                
                REM Check if .class file exists and is newer than .java file
                if exist "!CLASS_PATH!" (
                    for %%A in ("!FILE!") do set "JAVA_TIME=%%~tA"
                    for %%B in ("!CLASS_PATH!") do set "CLASS_TIME=%%~tB"
                    
                    REM Compare timestamps
                    if "!CLASS_TIME!" GTR "!JAVA_TIME!" (
                        set "NEED_COMPILE=false"
                    )
                )
                
                if "!NEED_COMPILE!"=="true" (
                    echo %YELLOW%Compiling !REL_PATH!%RESET%
                    
                    REM Compile the file and capture errors
                    javac -d "%~dp0bin" "!FILE!" 2> temp_errors.txt
                    set "ERROR_CODE=!ERRORLEVEL!"
                    
                    if !ERROR_CODE! NEQ 0 (
                        echo %RED%Compilation failed for !CLASSNAME!%RESET%
                        type temp_errors.txt >> "%ERROR_FILE%"
                        echo Failed to compile !REL_PATH! >> "%LOG_FILE%"
                        type temp_errors.txt
                    ) else (
                        echo %GREEN%Successfully compiled !CLASSNAME!%RESET%
                        set /a "COMPILED_FILES+=1"
                        
                        REM Get the current timestamp of the file
                        for %%T in ("!FILE!") do set "FILE_TIME=%%~tT"
                        
                        REM Add to the files object in cache
                        echo         "!REL_PATH!": { "status": "success", "timestamp": "!FILE_TIME!" } >> "!TEMP_FILES!"
                    )
                )
            )
            
            if exist temp_errors.txt del temp_errors.txt
        ) else (
            REM Determine batch size based on file count and thread count
            set /a "BATCH_SIZE=FILES_TO_COMPILE/MAX_THREADS + 1"
            if !BATCH_SIZE! LSS 1 set "BATCH_SIZE=1"
            
            REM Split files into batches for workers
            set "WORKER_COUNT=-1"
            set "CURRENT_BATCH=0"
            set "CURRENT_WORKER=0"
            set "FILE_COUNT=0"
            
            REM Clean worker task lists first
            for /l %%W in (0,1,!MAX_THREADS!) do (
                if exist "!WORKER_DIR!\worker_%%W.list" del "!WORKER_DIR!\worker_%%W.list"
            )
            
            echo %YELLOW%Splitting into batches of !BATCH_SIZE! files per worker%RESET%
            
            REM Create batch files for workers
            for /f "usebackq delims=" %%F in ("!ALL_FILES!") do (
                set /a "FILE_COUNT+=1"
                set /a "CURRENT_WORKER=FILE_COUNT %% MAX_THREADS"
                
                REM Append to the worker's task list
                echo %%F>>!WORKER_DIR!\worker_!CURRENT_WORKER!.list
                
                REM Update worker count
                if !CURRENT_WORKER! GTR !WORKER_COUNT! set "WORKER_COUNT=!CURRENT_WORKER!"
            )
            
            set /a "WORKER_COUNT+=1"
            if !WORKER_COUNT! GTR !MAX_THREADS! set "WORKER_COUNT=!MAX_THREADS!"
            echo %YELLOW%Starting !WORKER_COUNT! compilation threads...%RESET%
            
            REM Start worker processes
            for /l %%W in (0,1,!WORKER_COUNT!) do (
                if exist "!WORKER_DIR!\worker_%%W.list" (
                    REM Count how many files in this worker's list for validation
                    set "FILE_COUNT_IN_LIST=0"
                    for /f "usebackq" %%L in ("!WORKER_DIR!\worker_%%W.list") do set /a "FILE_COUNT_IN_LIST+=1"
                    
                    echo %CYAN%Worker %%W will compile !FILE_COUNT_IN_LIST! files%RESET%
                    
                    REM Launch compiler worker with the worker.bat file
                    start /b cmd /c "%~dp0worker.bat %%W "!WORKER_DIR!\worker_%%W.list" "!WORKER_DIR!" "%~dp0bin""
                    echo %CYAN%Started worker %%W%RESET%
                    timeout /t 1 /nobreak >nul
                )
            )
            
            REM Wait for all workers to complete
            echo %YELLOW%Waiting for all compilation threads to finish...%RESET%
            :wait_loop
            set "ALL_DONE=true"
            set "ACTIVE_WORKERS=0"
            for /l %%W in (0,1,!WORKER_COUNT!) do (
                if exist "!WORKER_DIR!\worker_%%W.list" (
                    if not exist "!WORKER_DIR!\worker_%%W.done" (
                        set "ALL_DONE=false"
                        set /a "ACTIVE_WORKERS+=1"
                    )
                )
            )
            
            if "!ALL_DONE!"=="false" (
                echo %CYAN%Waiting for !ACTIVE_WORKERS! active workers...%RESET%
                timeout /t 1 /nobreak >nul
                goto wait_loop
            )
            
            REM Collect results from workers
            echo %GREEN%All compilation threads completed%RESET%
            set "COMPILED_FILES=0"
            for /l %%W in (0,1,!WORKER_COUNT!) do (
                if exist "!WORKER_DIR!\worker_%%W.result" (
                    for /f "tokens=* usebackq" %%C in ("!WORKER_DIR!\worker_%%W.result") do (
                        set /a "COMPILED_FILES+=%%C"
                    )
                    
                    REM Append to the main temp file
                    if exist "!WORKER_DIR!\worker_%%W.cache" (
                        type "!WORKER_DIR!\worker_%%W.cache" >> "!TEMP_FILES!"
                    )
                    
                    REM Collect errors
                    if exist "!WORKER_DIR!\worker_%%W.errors" (
                        type "!WORKER_DIR!\worker_%%W.errors" >> "%ERROR_FILE%"
                    )
                )
            )
        )
    )
    
    echo %CYAN%Compilation complete. Compiled %COMPILED_FILES% out of %TOTAL_FILES% files.%RESET%
    
    REM Update the cache with all file information
    echo { > "%CACHE_FILE%"
    echo     "files": { >> "%CACHE_FILE%"
    
    REM Process the temp file to make valid JSON
    set "has_entries=false"
    if exist "!TEMP_FILES!" (
        set "temp_json=%CACHE_DIR%\temp_json.txt"
        if exist "!temp_json!" del "!temp_json!"
        
        REM First, convert paths to use forward slashes and make them relative to project root
        for /f "usebackq delims=" %%L in ("!TEMP_FILES!") do (
            set "line=%%L"
            
            REM Extract the path from the line (between first quotation marks)
            for /f "tokens=1,* delims=:" %%A in ("!line!") do (
                set "path=%%A"
                set "rest=%%B"
                
                REM Remove quotation marks
                set "path=!path:~1!"
                
                REM Make path relative by removing the project root path
                set "rel_path=!path:%CD%\=!"
                
                REM Replace backslashes with forward slashes
                set "rel_path=!rel_path:\=/!"
                
                REM Rebuild the line with the relative path
                echo         "!rel_path!"!rest! >> "!temp_json!"
            )
        )
        
        REM Now process the cleaned up temp file
        set "entries_count=0"
        
        REM Count entries manually
        for /f "usebackq delims=" %%L in ("!temp_json!") do set /a "entries_count+=1"
        
        REM Write all entries to the JSON file with proper formatting
        if !entries_count! GTR 0 (
            set "has_entries=true"
            set "current_entry=0"
            
            for /f "usebackq delims=" %%L in ("!temp_json!") do (
                set /a "current_entry+=1"
                set "line=%%L"
                
                if !current_entry! LSS !entries_count! (
                    echo !line!, >> "%CACHE_FILE%"
                ) else (
                    REM Last entry - write without trailing comma
                    echo !line! >> "%CACHE_FILE%"
                )
            )
        )
        
        if exist "!temp_json!" del "!temp_json!"
    )
    
    REM If no entries were added, ensure valid JSON
    if "!has_entries!"=="false" (
        echo              >> "%CACHE_FILE%"
    )
    
    echo     }, >> "%CACHE_FILE%"
    echo     "lastBuild": "%BUILD_TIMESTAMP%", >> "%CACHE_FILE%"
    echo     "errors": { >> "%CACHE_FILE%"
    
    REM Add error information if any - with proper JSON escaping
    set "has_errors=false"
    set "error_count=0"
    
    if exist "%ERROR_FILE%" (
        set "temp_errors=%CACHE_DIR%\temp_errors.txt"
        if exist "!temp_errors!" del "!temp_errors!"
        
        REM First collect all errors with IDs
        set "error_count=0"
        for /f "tokens=* usebackq" %%E in ("%ERROR_FILE%") do (
            if not "%%E"=="" (
                set /a "error_id=!RANDOM!"
                echo error_!error_id!: %%E>> "!temp_errors!"
                set "has_errors=true"
                set /a "error_count+=1"
            )
        )
        
        REM Now format them as JSON
        if exist "!temp_errors!" (
            if !error_count! GTR 0 (
                set "current_error=0"
                for /f "tokens=1,* delims=:" %%A in ('type "!temp_errors!"') do (
                    set /a "current_error+=1"
                    set "error_id=%%A"
                    set "error_text=%%B"
                    
                    REM Escape double quotes
                    set "error_text=!error_text:"=\"!"
                    
                    if !current_error! LSS !error_count! (
                        echo         "!error_id!": "!error_text!", >> "%CACHE_FILE%"
                    ) else (
                        REM Last error - write without trailing comma
                        echo         "!error_id!": "!error_text!" >> "%CACHE_FILE%"
                    )
                )
            )
            
            if exist "!temp_errors!" del "!temp_errors!"
        )
    )

    REM If no errors, ensure valid JSON
    if "!has_errors!"=="false" (
        echo                >> "%CACHE_FILE%"
    )
    
    echo     } >> "%CACHE_FILE%"
    echo } >> "%CACHE_FILE%"
    
    REM Clean up temp files
    if exist "!TEMP_FILES!" del "!TEMP_FILES!"
    if exist "!ALL_FILES!" del "!ALL_FILES!"
    if exist "!WORKER_DIR!\*.worker" del "!WORKER_DIR!\*.worker" >nul 2>&1
    if exist "!WORKER_DIR!\*.list" del "!WORKER_DIR!\*.list" >nul 2>&1
    if exist "!WORKER_DIR!\*.done" del "!WORKER_DIR!\*.done" >nul 2>&1
    if exist "!WORKER_DIR!\*.result" del "!WORKER_DIR!\*.result" >nul 2>&1
    if exist "!WORKER_DIR!\*.cache" del "!WORKER_DIR!\*.cache" >nul 2>&1
    if exist "!WORKER_DIR!\*.errors" del "!WORKER_DIR!\*.errors" >nul 2>&1
) else (
    echo %YELLOW%Compiling specific file: %~1%RESET%
    
    REM Check if the file exists with the provided path
    if exist "%~1" (
        echo %YELLOW%Compiling %~1...%RESET%
        javac -d "%~dp0bin" "%~1" 2> temp_errors.txt
        set "ERROR_CODE=!ERRORLEVEL!"
        
        if !ERROR_CODE! NEQ 0 (
            echo %RED%Compilation failed for %~1%RESET%
            type temp_errors.txt >> "%ERROR_FILE%"
            echo Failed to compile %~1 >> "%LOG_FILE%"
            type temp_errors.txt
        ) else (
            echo %GREEN%Successfully compiled %~1%RESET%
            set /a "COMPILED_FILES+=1"
        )
    ) else if exist "%~1.java" (
        REM Check if adding .java extension helps
        echo %YELLOW%Found %~1.java, compiling...%RESET%
        javac -d "%~dp0bin" "%~1.java" 2> temp_errors.txt
        set "ERROR_CODE=!ERRORLEVEL!"
        
        if !ERROR_CODE! NEQ 0 (
            echo %RED%Compilation failed for %~1.java%RESET%
            type temp_errors.txt >> "%ERROR_FILE%"
            echo Failed to compile %~1.java >> "%LOG_FILE%"
            type temp_errors.txt
        ) else (
            echo %GREEN%Successfully compiled %~1.java%RESET%
            set /a "COMPILED_FILES+=1"
        )
    ) else (
        REM Try to find the file in the project with multiple search strategies
        set "found=false"
        
        REM First try exact filename match
        for /r %%F in (*%~nx1.java) do (
            echo %YELLOW%Found matching file: %%F%RESET%
            javac -d "%~dp0bin" "%%F" 2> temp_errors.txt
            set "ERROR_CODE=!ERRORLEVEL!"
            
            if !ERROR_CODE! NEQ 0 (
                echo %RED%Compilation failed for %%F%RESET%
                type temp_errors.txt >> "%ERROR_FILE%"
                echo Failed to compile %%F >> "%LOG_FILE%"
                type temp_errors.txt
            ) else (
                echo %GREEN%Successfully compiled %%F%RESET%
                set /a "COMPILED_FILES+=1"
            )
            set "found=true"
            goto :found_file
        )
        
        REM Try with just the base filename (no path) 
        for /r %%F in ("%~1.java") do (
            if exist "%%F" (
                echo %YELLOW%Found matching file: %%F%RESET%
                javac -d "%~dp0bin" "%%F" 2> temp_errors.txt
                set "ERROR_CODE=!ERRORLEVEL!"
                
                if !ERROR_CODE! NEQ 0 (
                    echo %RED%Compilation failed for %%F%RESET%
                    type temp_errors.txt >> "%ERROR_FILE%"
                    echo Failed to compile %%F >> "%LOG_FILE%"
                    type temp_errors.txt
                ) else (
                    echo %GREEN%Successfully compiled %%F%RESET%
                    set /a "COMPILED_FILES+=1"
                )
                set "found=true"
                goto :found_file
            )
        )
        
        REM Try partial name match as last resort
        for /r %%F in (*.java) do (
            set "filename=%%~nF"
            if /i "!filename!"=="%~1" (
                echo %YELLOW%Found matching file by name: %%F%RESET%
                javac -d "%~dp0bin" "%%F" 2> temp_errors.txt
                set "ERROR_CODE=!ERRORLEVEL!"
                
                if !ERROR_CODE! NEQ 0 (
                    echo %RED%Compilation failed for %%F%RESET%
                    type temp_errors.txt >> "%ERROR_FILE%"
                    echo Failed to compile %%F >> "%LOG_FILE%"
                    type temp_errors.txt
                ) else (
                    echo %GREEN%Successfully compiled %%F%RESET%
                    set /a "COMPILED_FILES+=1"
                )
                set "found=true"
                goto :found_file
            )
        )
        
:found_file
        if "!found!"=="false" (
            echo %RED%Error: File %1 not found.%RESET%
            echo %YELLOW%Try using the full path or make sure the file exists.%RESET%
            echo File %1 not found >> "%ERROR_FILE%"
            exit /b 1
        )
    )
    
    if exist temp_errors.txt del temp_errors.txt
)

if %COMPILED_FILES% GTR 0 (
    echo %GREEN%Compilation complete. %COMPILED_FILES% files compiled successfully.%RESET%
) else (
    echo %YELLOW%No files needed compilation.%RESET%
)

echo %CYAN%Class files in bin directory.%RESET%

REM Calculate and display elapsed time
set "END_TIME=%time%"

REM Extract hours, minutes, seconds, and centiseconds
for /f "tokens=1-4 delims=:,. " %%a in ("%START_TIME%") do (
    set "START_H=%%a"
    set "START_M=%%b"
    set "START_S=%%c"
    set "START_CS=%%d"
)

for /f "tokens=1-4 delims=:,. " %%a in ("%END_TIME%") do (
    set "END_H=%%a"
    set "END_M=%%b"
    set "END_S=%%c"
    set "END_CS=%%d"
)

REM Remove leading spaces
set "START_H=%START_H: =%"
set "END_H=%END_H: =%"

REM Calculate elapsed time in centiseconds
set /a "ELAPSED_CS=(END_H*360000 + END_M*6000 + END_S*100 + END_CS) - (START_H*360000 + START_M*6000 + START_S*100 + START_CS)"

REM Handle timer passing midnight
if %ELAPSED_CS% lss 0 set /a "ELAPSED_CS+=8640000"

REM Convert to total seconds first (including decimal part)
set /a "ELAPSED_S=%ELAPSED_CS% / 100"
set /a "ELAPSED_CS_REMAINDER=%ELAPSED_CS% %% 100"

REM Calculate minutes and seconds
set /a "ELAPSED_M=%ELAPSED_S% / 60"
set /a "ELAPSED_S=%ELAPSED_S% %% 60"

REM Format the time display
if %ELAPSED_M% GTR 0 (
    echo %CYAN%Total compilation time: %ELAPSED_M% minutes %ELAPSED_S%.%ELAPSED_CS_REMAINDER% seconds.%RESET%
) else (
    echo %CYAN%Total compilation time: %ELAPSED_S%.%ELAPSED_CS_REMAINDER% seconds.%RESET%
)

exit /b 0

:compile_worker
setlocal enabledelayedexpansion
set "WORKER_ID=%~1"
set "TASK_LIST=%~2"
set "WORKER_DIR=%~3"
set "WORKER_CACHE=%WORKER_DIR%\worker_%WORKER_ID%.cache"
set "WORKER_ERRORS=%WORKER_DIR%\worker_%WORKER_ID%.errors"
set "WORKER_RESULT=%WORKER_DIR%\worker_%WORKER_ID%.result"
set "WORKER_LOG=%WORKER_DIR%\worker_%WORKER_ID%.log"

echo Worker %WORKER_ID% started > "%WORKER_LOG%"
echo 0 > "%WORKER_RESULT%"

set "WORKER_COMPILED=0"
if exist "%TASK_LIST%" (
    for /f "usebackq delims=" %%F in ("%TASK_LIST%") do (
        set "FILE=%%F"
        set "REL_PATH=%%~dpnxF"
        set "CLASSNAME=%%~nF"
        
        echo Worker %WORKER_ID%: Processing !CLASSNAME! >> "%WORKER_LOG%"
        
        REM Compile the file and capture errors
        javac -d "%~dp0bin" "!FILE!" 2>"%WORKER_DIR%\temp_errors_%WORKER_ID%.txt"
        set "ERROR_CODE=!ERRORLEVEL!"
        
        if !ERROR_CODE! NEQ 0 (
            echo Worker %WORKER_ID%: Compilation failed for !CLASSNAME! >> "%WORKER_LOG%"
            type "%WORKER_DIR%\temp_errors_%WORKER_ID%.txt" >> "%WORKER_ERRORS%"
            
            REM Add to the errors object in cache
            for %%T in ("!FILE!") do set "FILE_TIME=%%~tT"
            echo         "!REL_PATH!": { "status": "failed", "timestamp": "!FILE_TIME!" } >> "%WORKER_CACHE%"
        ) else (
            echo Worker %WORKER_ID%: Successfully compiled !CLASSNAME! >> "%WORKER_LOG%"
            set /a "WORKER_COMPILED+=1"
            
            REM Get the current timestamp of the file
            for %%T in ("!FILE!") do set "FILE_TIME=%%~tT"
            
            REM Add to the files object in cache
            echo         "!REL_PATH!": { "status": "success", "timestamp": "!FILE_TIME!" } >> "%WORKER_CACHE%"
        )
        
        if exist "%WORKER_DIR%\temp_errors_%WORKER_ID%.txt" del "%WORKER_DIR%\temp_errors_%WORKER_ID%.txt"
    )
) else (
    echo Worker %WORKER_ID%: Task list not found >> "%WORKER_LOG%"
)

echo %WORKER_COMPILED% > "%WORKER_RESULT%"
echo Worker %WORKER_ID% completed with %WORKER_COMPILED% files compiled >> "%WORKER_LOG%"
echo Done > "%WORKER_DIR%\worker_%WORKER_ID%.done"
exit /b 0