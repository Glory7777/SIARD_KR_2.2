package ch.admin.bar.siard2.api.ext.util;

import ch.admin.bar.siard2.api.ext.FileDownloadPathHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {

    private final FileDownloadPathHolder fileDownloadPathHolder;

    private FileUtil(FileDownloadPathHolder fileDownloadPathHolder) {
        this.fileDownloadPathHolder = fileDownloadPathHolder;
    }

    public static FileUtil create(FileDownloadPathHolder fileDownloadPathHolder) {
        return new FileUtil(fileDownloadPathHolder);
    }

    public void copy() {

        String sourceFilePath = fileDownloadPathHolder.getSourceFilePath();
        String targetFilePath = fileDownloadPathHolder.getTargetFilePath();

        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            System.out.println("File does not exist :: " + sourceFilePath);
            return;
        }

        makeDirectoryIfNotExists();

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(targetFilePath + File.separator + sourceFile.getName());
             FileChannel fcin = fis.getChannel();
             FileChannel fcout = fos.getChannel();
        ) {
            long size = fcin.size();
            fcout.transferFrom(fcin, 0, size);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    private void makeDirectoryIfNotExists() {
        String targetPathToMakeDirectory = fileDownloadPathHolder.getTargetPathToMakeDirectory();
        File file = new File(targetPathToMakeDirectory);
        if (file.isDirectory() && !file.exists()) {
            file.mkdirs();
        }
    }

}
