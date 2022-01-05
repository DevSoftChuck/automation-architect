# Salesforce Automation project

**Several tools for supporting test automation frameworks:**
Java 1.8, Selenium 4.1.1, TestNG 7.4.0, Cucumber 6.11.0, REST Assured (Soon), Maven, Travis CI (Jenkins Soon), Allure Reports, Cucumber Reports, Slack API (Soon), SeleniumGrid 4.1.1, WebDriverManager, Saucelabs (Soon), GitHub Pages.

## RUN TESTS

- In terminal type `mvn test` -> This will run all tests from tests package.`(src/test/java/Features)`  
You can pass some environment values e.g:
   - `-Dtests.executor=chrome` <sub>_**This will run your test cases using the selected executor, the available executors are firefox, grid, saucelabs, and chrome.**_</sub>
   - `-Dcucumber.filter.tags=tag` <sub>_**This will run all `.feature` scenarios with the provided tag.**_</sub>

## RESULTS AND LOGS
### CI/CD
- **Travis:** After each CI/CD cycle run, tests results will be automatically uploaded to [Github pages](https://ivan-andraschko.github.io/salesforce-automation/)

- **Jenkins:** Soon...

### Localhost
After each local cycle run logs and results are saved inside project, on allure-reports, allure-results, cucumber-reports, and logs folders.
### Allure & Cucumber Reports
- **Allure:** In terminal type `allure generate allure-results --clean` to generate Allure tests results, and then `allure open` to open the allure report.  
- **Cucumber:** Report is generated automatically, you will find those reports under cucumber-reports folder.
## SELENIUM GRID 
Here are the steps to deploy the Grid 4 to a Kubernetes cluster on development:
- Initialize kubernetes locally: `$ minikube start`.
- Install the NGINX ingress controller: `$ minikube addons enable ingress` [NGINX Installation Guide](https://kubernetes.github.io/ingress-nginx/deploy/).
- Configure your localhost to point hostname to kubernetes cluster: The private URL to access on the grid is defined as **salesforce-qa-testing.com** into k8s/ingress-nginx.yaml file:
  - **Windows**: Edit this file `C:\Windows\System32\drivers\etc\hosts` and place the result of `$ minikube ip` alongside of **salesforce-qa-testing.com** URL, e.g.: `192.168.99.100 salesforce-qa-testing.com`. 
  - **Unix**: Sames as windows but within this file `/etc/hosts`.
- Deploy all the grid components to kubernetes: `$ kubectl apply -f k8s`.

Run your test cases on Selenium grid:
1. Make sure that SeleniumGrid is running properly, check http://salesforce-qa-testing.com.
2. In terminal type `mvn test -Dtests.executor=grid -Dremote.browser=chrome`
## AUTHOR
- **Ivan Andraschko** - [Ivan Andraschko](https://www.linkedin.com/in/ivan-andraschko/)
## LICENSE
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
