package me.syncwrld.nerdflixapi;

import me.syncwrld.nerdflixapi.database.setup.DatabaseSetup;
import me.syncwrld.nerdflixapi.holder.LoaderHolder;
import me.syncwrld.nerdflixapi.management.DefaultFileManager;
import me.syncwrld.nerdflixapi.memory.loader.DatabaseToMemoryLoader;
import me.syncwrld.nerdflixapi.memory.loader.MemoryToDatabaseLoader;
import me.syncwrld.nerdflixapi.server.ServerCore;

public class NerdflixServerBootstrap extends ServerCore {

    private final DefaultFileManager defaultFileManager = new DefaultFileManager(this);

    @Override
    public void enable() {
        info("Starting Nerdflix Server...");
        info("Detected Java Version: " + getJavaVersion());
        info("More information: " + getOS() + " | " + getOsVersion());

        if (isWindows()) {
            warn("Nerdflix Server was made to run 24/7, and usually Windows users don't leave their computer online for long periods of time.", "For better experience, run the server on Linux or Docker. Please don't host it in desktop operational systems.");
        }

        info("Saving defaults...");
        defaultFileManager.saveDefaults();

        info("Loading API Keys...");
        LoaderHolder.API_KEY_LOADER.load();

        info("Loading Database...");
        DatabaseSetup.loadAll(this);

        info("Loading Memory...");
        DatabaseToMemoryLoader.INSTANCE.load();

        info("Running on address: " + getBindAddress());
        info("Nerdflix Server started successfully!");
    }

    @Override
    public void disable() {
        System.out.println("Shutting down Nerdflix Server...");
        MemoryToDatabaseLoader.INSTANCE.load();
    }

}
