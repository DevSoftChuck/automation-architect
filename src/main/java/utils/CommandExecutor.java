package utils;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommandExecutor {

    private static final int PROCESS_TIME_OUT = 60;

    public static Process executeCommand(String commands){
        try {
            List<ProcessBuilder> builders = new ArrayList<>();
            builders.add(getInitialProcessBuilder());
            if (commands.contains("|"))
                for (String command : commands.split("\\|"))
                    builders.add(new ProcessBuilder(command.trim().split(" ")));
            else
                builders.add(new ProcessBuilder(commands.trim().split(" ")));

            //Replaces the last element in this list with the same but modified element.
            builders.set(builders.size() - 1,
                    builders.get(builders.size() - 1)
                            .redirectErrorStream(true)
//                            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
//                            .redirectOutput(ProcessBuilder.Redirect.appendTo(new File("logs/process_log")))
            );
            List<Process> processes = ProcessBuilder.startPipeline(builders);
            return processes.get(processes.size() - 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ProcessBuilder getInitialProcessBuilder(){
        ProcessBuilder processBuilder;
        Map<String, String> environment = new HashMap<>();
        environment.put("allure", "C:\\Program Files (x86)\\allure-2.18.1\\bin");

        if (System.getProperty("os.name").toLowerCase().contains("win")){
            processBuilder = new ProcessBuilder("cmd.exe", "/c");
        } else {
            processBuilder = new ProcessBuilder("/bin/sh", "-c");
        }
        processBuilder.directory(new File(System.getProperty("user.dir")));
        processBuilder.environment().putAll(environment);
        return processBuilder;
    }

    private static String getFullProcessInput(InputStream stream){
        return new BufferedReader(new InputStreamReader(stream))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static boolean searchFor(Process process, String word){
        String readLine;
        boolean match = false;
        try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((readLine = processOutputReader.readLine()) != null) {
                if (readLine.contains(word)){
                    match = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return match;
    }

    /**
     * https://www.baeldung.com/java-completablefuture
     * https://www.baeldung.com/java-9-completablefuture
     * http://iteratrlearning.com/java9/2016/09/13/java9-timeouts-completablefutures.html
     * */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String command = "ping 127.0.0.1 -n 10 | findstr Approximate";

        CompletableFuture<Process> processFuture =
                CompletableFuture.supplyAsync(() -> CommandExecutor.executeCommand(command));
        processFuture.thenApplyAsync(process -> CommandExecutor.searchFor(process, "Approximate"))
                .completeOnTimeout(false, PROCESS_TIME_OUT, TimeUnit.SECONDS)
                .thenAccept(isFounded ->{
                    try {
                        if (isFounded) {
                            System.out.println("Found it!");
                            processFuture.get().destroyForcibly();
                        }
                        else
                            System.out.println("Sorry, we didn't find anything.");
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }).get();
    }
}
