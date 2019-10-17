package nl.schiphol.jaxb.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Search directories recursively for file in Java */
public class FileSearch {
  private static final Logger log = LoggerFactory.getLogger(FileSearch.class);
  private String fileNameToSearch;
  private List<String> result = new ArrayList<>();

  /**
   * @param directory
   * @param fileNameToSearch case insensitive
   */
  public void searchDirectory(File directory, String fileNameToSearch) {
    setFileNameToSearch(fileNameToSearch);
    if (directory.isDirectory()) {
      search(directory);
    } else {
      log.debug(directory.getAbsoluteFile() + " is not a directory!");
    }
  }

  public String getFileNameToSearch() {
    return fileNameToSearch;
  }

  private void setFileNameToSearch(String fileNameToSearch) {
    this.fileNameToSearch = fileNameToSearch.toLowerCase();
  }

  public List<String> getResult() {
    return result;
  }

  private void search(File file) {
    if (file.isDirectory()) {
      log.debug("Searching directory ... " + file.getAbsoluteFile());
      // do you have permission to read this directory?
      if (file.canRead()) {
        for (File temp : file.listFiles()) {
          if (temp.isDirectory()) {
            search(temp);
          } else {
            if (getFileNameToSearch().equals(temp.getName().toLowerCase())) {
              result.add(temp.getAbsoluteFile().toString());
            }
          }
        }
      } else {
        log.debug(file.getAbsoluteFile() + "Permission Denied");
      }
    }
  }

  /** example */
  public static void main(String[] args) {

    FileSearch fileSearch = new FileSearch();

    // try different directory and filename :)
    fileSearch.searchDirectory(new File("/Users/test/websites"), "post.php");

    int count = fileSearch.getResult().size();
    if (count == 0) {
      System.out.println("\nNo result found!");
    } else {
      System.out.println("\nFound " + count + " result!\n");
      for (String matched : fileSearch.getResult()) {
        System.out.println("Found : " + matched);
      }
    }
  }
}
