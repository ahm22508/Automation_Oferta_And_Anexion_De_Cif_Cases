package OfertaProject;

import java.io.File;

public class FileAnalysis {

    public static boolean isFile(String filePath){
        File file = new File(filePath);
        return file.canRead() && file.isFile() && file.exists();
    }
}