package me.syncwrld.nerdflixapi.server.tool;

import com.google.common.base.Strings;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.List;

public class MachineLookup {

  public MachineLookup() {}

  public String getProcessorModel() {
    return System.getProperty("os.arch") == null ? "?" : System.getProperty("os.arch");
  }

  public int getRuntimeAvailableCores() {
    return Runtime.getRuntime().availableProcessors();
  }

  public List<String> getProcessors() {
    try {
      Process process = Runtime.getRuntime().exec("lscpu");
      BufferedReader bfReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line, cpuModel = "";

      while ((line = bfReader.readLine()) != null) {
        if (line.contains("Model name:")) {
          cpuModel =
              line.replace("Model name:", "")
                  .replace("                      ", "")
                  .replace("  ", "");
        }
      }

      return Strings.isNullOrEmpty(cpuModel)
          ? Collections.singletonList("Unknown")
          : Collections.singletonList(cpuModel);
    } catch (Exception e) {
      try {
        List<String> model = Collections.emptyList();
        Components components = JSensors.get.components();

        components.cpus.forEach(cpu -> model.add(cpu.name));
      } catch (Exception exception) {
        return Collections.singletonList("Unknown");
      }
    }

    return Collections.singletonList("Unknown");
  }

  public String getFormattedCpus() {
    return String.join(", ", getProcessors()).replace("  ", "");
  }

  public String getOS() {
    return ManagementFactory.getOperatingSystemMXBean().getName();
  }

  public String getOsVersion() {
    return ManagementFactory.getOperatingSystemMXBean().getVersion();
  }

  public double getLoadAverage() {
    return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
  }

  public long getMaxMemory() {
    return Runtime.getRuntime().maxMemory();
  }

  public long getFreeMemory() {
    return Runtime.getRuntime().freeMemory();
  }

  public long getTotalRam() {
    return Runtime.getRuntime().totalMemory();
  }

  public long getUsedMemory() {
    return getTotalRam() - getFreeMemory();
  }
}
