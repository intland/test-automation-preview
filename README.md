## Prerequisite
* Java 21
* Installed Codebeamer 3.0 - Snapshost
* Oracle / PostgreSQL
* Linux / Windows


## How to run
* Create a configuration.yml file in the cb-ui-integration-test/src/test/resources directory, use the configuration.yml.sample
   - Change the url in the configuration.yml
* Set the following environment variable (https://phoenixnap.com/kb/windows-set-environment-variable):
  - PLAYWRIGHT_CHROME_EXECUTABLE_PATH=C:\path\to\chrome.exe
  - PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1
  - CB_MAC_ADDRESS=00:00:00:00:00:00
  - CB_apiThrottling=false
  - CB_LICENSE=<license>
* ./gradlew clean test

  
