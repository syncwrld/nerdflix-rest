package me.syncwrld.nerdflixapi.server;

import com.google.common.base.Stopwatch;
import me.syncwrld.booter.CommonPurposeClass;
import me.syncwrld.nerdflixapi.NerdflixServerEngine;
import me.syncwrld.nerdflixapi.server.interfaces.BootableServer;
import me.syncwrld.nerdflixapi.server.other.ConsoleColors;
import me.syncwrld.nerdflixapi.server.tool.MachineLookup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import java.util.stream.Stream;

public abstract class ServerCore extends CommonPurposeClass implements BootableServer {

    public static final Logger LOGGER = Logger.getLogger(NerdflixServerEngine.class.getName());
    private static final ScheduledExecutorService executorService =
            Executors.newSingleThreadScheduledExecutor();
    public static Stopwatch STOPWATCH;
    public static MachineLookup LOOKUP = new MachineLookup();
    public static boolean TOTALLY_LOADED;
    public Stopwatch ENABLE_STOPWATCH;

    public static void log(Level level, String... messages) {
        String color =
                level == Level.INFO
                        ? ConsoleColors.BLUE_BRIGHT
                        : level == Level.WARNING
                        ? ConsoleColors.YELLOW_BOLD
                        : level == Level.SEVERE ? ConsoleColors.RED_BRIGHT : ConsoleColors.WHITE;
        for (String message : messages) {
            LOGGER.log(level, color + message + ConsoleColors.RESET);
        }
    }

    public static String getUptime() {
        if (STOPWATCH.isRunning()) {
            return STOPWATCH.toString();
        }
        return "Server is not running yet, stopwatch is not initialized.";
    }

    public static void info(String... messages) {
        log(Level.INFO, messages);
    }

    public static void warn(String... messages) {
        log(Level.WARNING, messages);
    }

    public static void error(String... messages) {
        log(Level.SEVERE, messages);
    }

    public static void info(String message) {
        log(Level.INFO, message);
    }

    public static void warn(String message) {
        log(Level.WARNING, message);
    }

    public static void error(String message) {
        log(Level.SEVERE, message);
    }

    public void startRunnable(Runnable runnable, long time, TimeUnit timeUnit) {
        executorService.scheduleAtFixedRate(runnable, 0L, time, timeUnit);
        info(
                runnable.getClass().getName()
                        + " created with period of "
                        + time
                        + " "
                        + timeUnit.name().toLowerCase());
    }

    public void clearConsole() {
        for (int i = 0; i <= 100; i++) {
            System.out.println(" ");
        }
    }

    public void startCounting() {
        STOPWATCH = Stopwatch.createStarted();
    }

    public String getBaseDirectory() {
        return System.getProperty("user.dir");
    }

    public void startServer() {
        ENABLE_STOPWATCH = Stopwatch.createStarted();
        this.clearConsole();
        this.setupLogger(LOGGER);
        this.startCounting();

        this.enable();

        ENABLE_STOPWATCH.stop();
        info("Nerdflix Server enabled in " + ENABLE_STOPWATCH);
        TOTALLY_LOADED = true;
    }

    public void setLoaded(boolean loaded) {
        TOTALLY_LOADED = loaded;
    }

    public boolean isLoaded() {
        return TOTALLY_LOADED;
    }

    public void setupLogger(Logger logger) {
        int removed = 0;
        for (Handler iHandler : logger.getParent().getHandlers()) {
            logger.getParent().removeHandler(iHandler);
            removed++;
        }

        System.out.println("Removed " + removed + " handlers from parent loggers");

        Formatter formatter =
                new Formatter() {
                    @Override
                    public String format(LogRecord record) {
                        String format =
                                String.format(
                                        "[Nerdflix Server] %s: %s",
                                        record.getLevel().getName(), record.getMessage() + "\n");
                        record.setMessage(format);
                        return format;
                    }
                };
        Handler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        logger.getParent().addHandler(handler);
    }

    public void saveResource(String file) {
        File fileObj = new File(getBaseDirectory(), file);

        if (!fileObj.exists()) {
            info("Saving resource: " + file);
            try (InputStream in = getResource(file)) {
                if (in == null) {
                    warn("Resource not found: " + file);
                    return;
                }

                if (!fileObj.getParentFile().exists() && !(fileObj.getParentFile().isFile())) {
                    if (fileObj.getParentFile().mkdirs()) {
                        info("Created directory: " + fileObj.getParentFile().getAbsolutePath());
                    } else {
                        warn("Failed to create directory: " + fileObj.getParentFile().getAbsolutePath());
                    }
                }
                Files.copy(in, fileObj.toPath());
            } catch (IOException e) {
                warn("Error while saving resource: " + e.getMessage());
            }
        }
    }

    public InputStream getResource(String filename) {
        try {
            URL url = this.getClass().getClassLoader().getResource(filename);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (Exception ignored) {
            return null;
        }
    }

    public boolean isWindows() {
        return getOS().toLowerCase().contains("win");
    }

    public boolean isLinux() {
        return getOS().toLowerCase().contains("linux");
    }

    public String getOS() {
        return System.getProperty("os.name");
    }

    public String getOsVersion() {
        return System.getProperty("os.version");
    }

    public String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public String getProcessorModel() {
        return LOOKUP.getFormattedCpus();
    }

    public boolean isDocker() {
        try (Stream<String> stream = Files.lines(Paths.get("/proc/1/cgroup"))) {
            return stream.anyMatch(line -> line.contains("/docker"));
        } catch (IOException e) {
            return false;
        }
    }

    public String getCpuCores() {
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }

    public String getRam() {
        return Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB";
    }

    public String getBindAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "0.0.0.0 (localhost)";
        }
    }
}
