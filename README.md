# Automation project
**Tools supporting test automation frameworks:**
Java 17, Selenium 4.4.0, TestNG, REST Assured, Kubernetes and Jenkins (Soon), 
Allure Reports, Slack API (Soon), SeleniumGrid, WebDriverManager, 
Saucelabs (Soon), Owner framework, Salesforce API Rest.


## RUN TESTS

In terminal type `mvn test` -> This will run all tests from tests package.`(src/test/java/testCases)`  
You can pass some environment values e.g:
   - `-Dbrowser=chrome` <sub>_**This will run your test cases using a specific browser, the available browser are firefox and chrome.**_</sub>
   - `-Ddriver.remote.server=local` <sub>_**This will specify in which environment you want to run your test cases, the available environments are `local` and `saucelab`.**_</sub>
   - `-Dsauce.platform.name=Windows11` <sub>_**Platform on which test cases will be running on Saucelab.**_</sub>
   - `-Dsauce.browser.version=latest` <sub>_**Browser version in Saucelab.**_</sub>
   - `-Dsauce.user=none` <sub>_**Saucelab username.**_</sub>
   - `-Dsauce.key=none` <sub>_**Saucelab access key.**_</sub>
   - `-Dheadless=false` <sub>_**Specify if you want to run your browser in headless mode.**_</sub>
   - `-Dtestng.parallel=methods` <sub>_**You can choose how to run your test cases in parallel, the available options are between `methods`, `tests`, `classes` or `instances`.**_</sub>
   - `-Dtestng.threads=1` <sub>_**The size of the thread pool for running in parallel.**_</sub>
   - `-Dtestng.groups=sanity` <sub>_**The list of groups you want to be excluded from this run.**_</sub>
   - `-Dselenium.grid.url=http://local-testing.com` <sub>_* Selenium endpoint to run test cases on kubernetes containers. *_</sub>

## API 
Currently, only Rest-Assured is supported. Some materials that would be useful for expanding the API tests:
- [JSONPath Online Evaluator](https://jsonpath.com/)
- [Expressions in JSONPath](https://toolsqa.com/rest-assured/expressions-in-jsonpath/)
- [Read JSON Response Body using Rest Assured](https://toolsqa.com/rest-assured/read-json-response-body-using-rest-assured/)
- [Rest Assured Tutorial](https://www.toolsqa.com/rest-assured-tutorial/)

We also support Salesforce API request, please take a look:
- [Force.com REST API Connector](https://github.com/jesperfj/force-rest-api)


## RESULTS AND LOGS
### CI/CD
- **Jenkins:** Soon...

### Allure
- **Allure:** In terminal type `allure open` to open the allure report.  

## SELENIUM GRID 
Here are the steps to deploy the Grid to a Kubernetes cluster on development:
- Initialize kubernetes locally: `$ minikube start`.
- Install the NGINX ingress controller: `$ minikube addons enable ingress` [NGINX Installation Guide](https://kubernetes.github.io/ingress-nginx/deploy/).
- Configure your localhost to point hostname to kubernetes cluster: The private URL to access on the grid is defined as **local-testing.com** into k8s/ingress-nginx.yaml file:
  - **Windows**: Edit this file `C:\Windows\System32\drivers\etc\hosts` and place the result of `$ minikube ip` alongside of **local-testing.com** URL, e.g.: `192.168.99.100 local-testing.com`. 
  - **Unix**: Sames as windows but within this file `/etc/hosts`.
- Deploy all the grid components to kubernetes: `$ kubectl apply -f k8s`.

Run your test cases on Selenium grid:
1. Make sure that SeleniumGrid is running properly, check http://local-testing.com.
2. In terminal type `mvn test -Dselenium.grid.urlr=http://local-testing.com`

## AUTHOR
- [Ivan Andraschko](https://www.linkedin.com/in/ivan-andraschko/)
## LICENSE
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
