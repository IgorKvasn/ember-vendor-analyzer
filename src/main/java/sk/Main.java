package sk;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {

  List<ModuleInfo> moduleNames = new LinkedList<>();

  public void doIt(String buildFilePath) throws Exception {
    BigDecimal bytes = new BigDecimal(0);
    try (BufferedReader br = Files.newBufferedReader(Paths.get(buildFilePath), StandardCharsets.UTF_8)) {
      for (String line = null; (line = br.readLine()) != null; ) {
        if (line.startsWith("define('")) {
          String module = substringBetween(line, "define('", "/");
          if (module == null) {
            module = substringBetween(line, "define('", "'");
          }
          ModuleInfo moduleInfo = new ModuleInfo(module, bytes);
          if (!moduleNames.contains(moduleInfo)) {
            moduleNames.add(moduleInfo);
          }
        }
        bytes = bytes.add(new BigDecimal(line.length()));
      }
    }


    for (int i = 0; i < moduleNames.size() - 1; i++) {
      ModuleInfo currentModule = moduleNames.get(i);
      ModuleInfo nextModule = moduleNames.get(i + 1);

      currentModule.size = nextModule.lineNumberStart.subtract(currentModule.lineNumberStart);
    }

    ModuleInfo lastModule = moduleNames.get(moduleNames.size() - 1);
    lastModule.size = bytes.subtract(lastModule.lineNumberStart);


    Collections.sort(moduleNames, new Comparator<ModuleInfo>() {
      @Override
      public int compare(ModuleInfo moduleInfo, ModuleInfo t1) {
        return moduleInfo.size.compareTo(t1.size) * -1;
      }
    });

    for (ModuleInfo moduleName : moduleNames) {
      System.out.println(moduleName);
    }

  }

  private String substringBetween(final String str, final String open, final String close) {
    if (str == null || open == null || close == null) {
      return null;
    }
    final int start = str.indexOf(open);
    if (start != -1) {
      final int end = str.indexOf(close, start + open.length());
      if (end != -1) {
        return str.substring(start + open.length(), end);
      }
    }
    return null;
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.printf("Usage: 1 argument with path to vendor.js file");
      return;
    }
    new Main().doIt(args[0]);
  }


  class ModuleInfo {
    String name;
    BigDecimal lineNumberStart;
    BigDecimal size = BigDecimal.ZERO;

    public ModuleInfo(String name, BigDecimal lineNumberStart) {
      this.name = name;
      this.lineNumberStart = lineNumberStart;
    }


    @Override
    public int hashCode() {
      return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof ModuleInfo)) {
        return false;
      }
      return ((ModuleInfo) o).name.equals(this.name);
    }

    @Override
    public String toString() {
      return name + " - size= " + size + " B / " + (size.divide(new BigDecimal(1024), BigDecimal.ROUND_HALF_UP)) + " kB";
    }
  }

}
