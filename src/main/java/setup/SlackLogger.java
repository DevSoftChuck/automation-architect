package setup;

/**
 * @author Ivan Andraschko
 **/

public class SlackLogger extends TestEnvironment {

//    private static String color;
//    private static String testAttachmentImage;
//    private static final String URL_LINKEDIN = "https://www.linkedin.com/in/kamil-nowocin";
//    //https://hooks.slack.com/services/YOUR_SLACK_TOKEN <- CORRECT SLACK WEB HOOK
//    private static final String URL_SLACK_WEB_HOOK = "https://hooks.slack.com/services/";
//    private static final String URL_REPOSITORY = "https://github.com/kamil-nowocin/Test_Automation-automationpractice";
//    private static final String URL_REPOSITORY_IMAGES = "https://raw.githubusercontent.com/kamil-nowocin/Test_Automation-automationpractice/master/src/test/resources/files/images";
//
//    private String slackResultDetailsBuilder() {
//        List<String> testCaseNames = new ArrayList<>();
//        int i = 1;
//        if (TestNGListener.failedTests.size() == 0) {
//            testCaseNames.add("\n*Congratulations, all tests passed successfully*:trophy:");
//        } else {
//            for (String failedTest : TestNGListener.failedTests) {
//                testCaseNames.add(String.format("*%d) FAILED TEST CASE NAME:*\n", i));
//                testCaseNames.add("`");
//                testCaseNames.add(failedTest);
//                testCaseNames.add("`");
//                testCaseNames.add("\n\n");
//                i++;
//            }
//        }
//        return (StringUtils.join(testCaseNames, ""));
//    }

//    private void whichColor() {
//        if (ContextInjection.failedTestsAmount == 0) {
//            color = "#32CD32"; //GREEN
//            testAttachmentImage = (String.format("%s/green_icon.png", URL_REPOSITORY_IMAGES));
//        } else {
//            color = "#FF4500"; //RED
//            testAttachmentImage = (String.format("%s/red_icon.png", URL_REPOSITORY_IMAGES));
//        }
//        color = "#32CD32"; //GREEN
//        testAttachmentImage = (String.format("%s/green_icon.png", URL_REPOSITORY_IMAGES));
//    }

//    public void sendTestExecutionStatusToSlack(ITestContext iTestCkontext) {
////        int TOTAL_TEST_CASES = ContextInjection.failedTestsAmount + ContextInjection.passedTestsAmount;
//        int TOTAL_TEST_CASES = 10;
//
//        whichColor();
//        try {
//            Payload slackLoggerPayload = Payload.builder()
//                    .blocks(Arrays.asList(
//                            ContextBlock.builder()
//                                    .elements(Arrays.asList(
//                                            MarkdownTextObject.builder()
//                                                    .text("Author: _*Kamil Nowocin*_")
//                                                    .build(),
//                                            ImageElement.builder()
//                                                    .imageUrl((String.format("%s/linkedin_icon.png", URL_REPOSITORY_IMAGES)))
//                                                    .altText("test")
//                                                    .build(),
//                                            MarkdownTextObject.builder()
//                                                    .text(String.format("<%s| LinkedIn>", URL_LINKEDIN))
//                                                    .build()))
//                                    .build(),
//                            DividerBlock.builder().build(),
//                            SectionBlock.builder().text
//                                    (MarkdownTextObject.builder()
//                                            .text((String.format("<!here>\nNumber of tests executed: *%d*\nRepository:" +
//                                                    " *<%s| Test_Automation-automationpractice>* ", TOTAL_TEST_CASES, URL_REPOSITORY)))
//                                            .build())
//                                    .build(),
//                            SectionBlock.builder().fields(Arrays.asList(
//                                    MarkdownTextObject.builder()
//                                            .text(String.format(":large_green_square:*PASSED TESTS*\n(%d/%d)",
//                                                    10))//ContextInjection.passedTestsAmount, TOTAL_TEST_CASES))
//                                            .build(),
//                                    MarkdownTextObject.builder()
//                                            .text(String.format(":large_red_square:*FAILED TESTS*\n(%d/%d)",
//                                                    10))//ContextInjection.failedTestsAmount, TOTAL_TEST_CASES))
//                                            .build()))
//                                    .build(),
//                            DividerBlock.builder().build()))
//                    .attachments(Collections.singletonList(
//                            Attachment.builder()
//                                    .color(color)
//                                    .blocks(Arrays.asList(
//                                            SectionBlock.builder().text
//                                                    (MarkdownTextObject.builder()
//                                                            .text((String.format("Execution date: <!date^%d^{date}, {time} CET|sa>\n\n" +
//                                                                            "*XML SUITE NAME:* %s\n*XML TESTS:* %s\n" +
//                                                                            "*====================FAILURE DETAILS====================*\n\n%s",
//                                                                    getUnixTime(), MessageBuilder.getXmlSuiteName(iTestContext),
//                                                                    MessageBuilder.getXmlTestName(iTestContext), slackResultDetailsBuilder())))
//                                                            .build())
//                                                    .accessory
//                                                            (ImageElement.builder()
//                                                                    .imageUrl(testAttachmentImage)
//                                                                    .altText("TEST FAILED")
//                                                                    .build())
//                                                    .build(),
//                                            SectionBlock.builder().text
//                                                    (MarkdownTextObject.builder()
//                                                            .text("Click for more information from Travis-CI")
//                                                            .build())
//                                                    .accessory
//                                                            (ButtonElement.builder()
//                                                                    .text(PlainTextObject.builder()
//                                                                            .text("Travis-CI")
//                                                                            .emoji(true)
//                                                                            .build())
//                                                                    .url(TRAVIS_BUILD_WEB_URL)
//                                                                    .style("danger")
//                                                                    .build())
//                                                    .build(),
//                                            ActionsBlock.builder()
//                                                    .elements(Collections.singletonList(
//                                                            ButtonElement.builder()
//                                                                    .text(PlainTextObject.builder()
//                                                                            .text("Another button")
//                                                                            .emoji(true)
//                                                                            .build())
//                                                                    .url("https://www.fakeurl.pl")
//                                                                    .style("danger")
//                                                                    .build()))
//                                                    .build(),
//                                            DividerBlock.builder().build(),
//                                            SectionBlock.builder().text
//                                                    (MarkdownTextObject.builder()
//                                                            .text((String.format("End of test execution for: *%s* :ghost:",
//                                                                    MessageBuilder.getXmlTestName(iTestContext))))
//                                                            .build())
//                                                    .build()
//                                    )).build()
//                    ))
//                    .build();
//            WebhookResponse webhookResponse = Slack.getInstance().send(String.format("%s/%s", URL_SLACK_WEB_HOOK, SLACK_TOKEN), slackLoggerPayload);
//            logger.info(slackLoggerResponse(webhookResponse));
//        } catch (IOException e) {
//            logger.error("Unexpected Error! WebHook: " + URL_SLACK_WEB_HOOK);
//        }
//    }

//    private String slackLoggerResponse(WebhookResponse webhookResponse) {
//        if (webhookResponse.getCode() != 200) {
//            return String.format("Slack response: %d, Additional information: %s", webhookResponse.getCode(), webhookResponse.getBody());
//        }
//        return String.format("Slack response: %d", webhookResponse.getCode());
//    }
}