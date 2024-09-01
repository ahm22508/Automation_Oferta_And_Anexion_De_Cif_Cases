package OfertaProject;

import java.io.File;

public class SearchFile {
    public static File searchFile (File directory, String fileNameToSearch){
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File foundFile = searchFile(file, fileNameToSearch);
                    if (foundFile != null) {
                        return foundFile;
                    }
                } else {
                    if (file.getName().equalsIgnoreCase(fileNameToSearch)) {
                        return file;
                    }
                }
            }
        }
        return null;
    }
}
