package utils;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommandExecutor {

    public static Process executeCommand(String commands){
        try {
            List<ProcessBuilder> builders = new ArrayList<>();

            if (commands.contains("|"))
                for (String command : commands.split("\\|"))
                    builders.add(getInheritProcessBuilder(command.trim()));
            else
                builders.add(getInheritProcessBuilder(commands.trim()));

            //Replaces the last element in this list with the same but modified element.
            builders.set(builders.size() - 1,
                    builders.get(builders.size() - 1)
                            .redirectErrorStream(true)
            );

            List<Process> processes = ProcessBuilder.startPipeline(builders);
            return processes.get(processes.size() - 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ProcessBuilder getInheritProcessBuilder(String command){
        ProcessBuilder processBuilder;
        if (System.getProperty("os.name").toLowerCase().contains("win")){
            processBuilder = new ProcessBuilder("cmd.exe", "/S/D/c", command);
        } else {
            processBuilder = new ProcessBuilder("/bin/sh", "-c", command);
        }
        processBuilder.directory(new File(System.getProperty("user.dir")));
        processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
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
        String command = "allure generate allure-results --clean";

        CompletableFuture<Process> processFuture =
                CompletableFuture.supplyAsync(() -> CommandExecutor.executeCommand(command));
        processFuture.thenApplyAsync(process -> CommandExecutor.searchFor(process, "generated"))
                .completeOnTimeout(false, 60, TimeUnit.SECONDS)
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
