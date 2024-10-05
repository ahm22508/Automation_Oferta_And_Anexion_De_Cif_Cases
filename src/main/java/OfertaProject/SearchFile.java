package OfertaProject;

import java.io.File;

public class SearchFile {
    public static File searchFile(File directory, String fileNameToSearch) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File foundFile = searchFile(file, fileNameToSearch);
                    if (foundFile != null) {
                        return foundFile;
                    }
                } else {
                  String FileNameWithPath = directory+"\\"+fileNameToSearch+".xlsm";
                  String FileNameWithPath1 = directory+"\\"+fileNameToSearch+".xlsx";
                  String FileNameWithPath2 = directory+"\\"+fileNameToSearch+".xls";
                        if (file.toString().equals(FileNameWithPath)||file.toString().equals(FileNameWithPath1)||file.toString().equals(FileNameWithPath2)){
                            return file;
                        }
                    }
                }
           }
            return null;
        }
    }
