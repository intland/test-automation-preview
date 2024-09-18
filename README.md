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
  - CB_LICENSE=your license
* ./gradlew clean test

## How to run in IDEA
Import the project as a gradle project and make sure you change the following configuration

![image](https://github.com/user-attachments/assets/bb6349de-976d-414e-95d2-74e02fc1f121)

  
