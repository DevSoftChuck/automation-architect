
def ENV = "STAGE"

def setupPropertiesFiles(env){
  String propertiesFileName = env + "_context"
  script{
    sh 'chmod 777 $WORKSPACE'
    withCredentials([file(credentialsId: "$propertiesFileName", variable: "PROPERTIES_FILE")]){
      sh """
        rm -rf $WORKSPACE/src/test/resources/$PROPERTIES_FILE
        cp $PROPERTIES_FILE $WORKSPACE/src/test/resources
      """
    }
  }
}

def sendAllureReport(String groupOfTestCases){
  allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
  emailext mimeType: 'text/html',
            body: '${FILE, path="target/surefire-reports/emailable-testng-report.html"} <br> For detailed test execution results: <a href="&BUILD_URL/allure">Click Here</a>',
            subject: "PCAutomation '${groupOfTestCases}' Test execution Summary '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
            to: "some-email@test.com"
}

def runTestCases(String env, String groups, String headless, String browser){
  sh """
    mvn clean test -Dremote.platform.name=linux \
                   -Dwebdriver.remote.server=saucelas \
                   -Denv=${env} \
                   -Dbrowser=${browser} \
                   -Dheadless=${headless} \
                   -Dgroups=${groups}
  """
}

def runTestCasesUsingSecretFile(String env, String groups, String headless, String browser){
  withCredentials([file(credentialsId: "SomeSecretFileName", variable: "SOME_SECRET_FILE_NAME")],
                   string(credentialsId: "SomeSecretFileName2", variable: "SOME_SECRET_FILE_NAME2")){
    sh """
    mvn clean test -Dremote.platform.name=linux \
                   -Dwebdriver.remote.server=saucelas \
                   -Denv=${env} \
                   -Dbrowser=${browser} \
                   -Dheadless=${headless} \
                   -Dgroups=${groups} \
                   -DconsumerKey=${SOME_SECRET_FILE_NAME} \
                   -DanotherConsumerKey=${SOME_SECRET_FILE_NAME2}
    """
  }
}

pipeline{
  agent any

  tools {
    maven 'Apache_Maven_3.5.3'
    jdk 'OpenJDK 17'
  }

  options {
    timeout(time: 90, unit: "MINUTES")
    disableConcurrentBuilds()
  }

  environment {
    DEFAULT_BRANCH='develop'
  }

  parameters {
    string(
        name: 'ENV',
        defaultValue: '',
        description: 'Available env: STAGE1, STAGE2, or STAGE3.'
    )
    choice(
        name: 'HEADLESS',
        choices: ['false', 'true'],
        description: 'Run your test cases in headless mode.'
    )
    choice(
        name: 'GROUPS_TO_RUN',
        choices: ['Sanity', 'Regression', 'Smoke'],
        description: 'By default sanity test cases would run.'
    )
  }

  stages {
    stage("Run Test Cases") {
      when {
        expression { return params.GROUPS_TO_RUN != '' && params.ENV != ''}
      }
      steps {
        echo 'Starting sanity test cases'
        script {
          setupPropertiesFiles("${params.ENV}")
          runTestCases("${params.ENV}", "${params.GROUPS_TO_RUN}", "false", "chrome")
          sendAllureReport("${params.GROUPS_TO_RUN}")
        }
      }
    }
  }
}