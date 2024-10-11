package ch.admin.bar.siard2.api.ext;

public class FileDownloadPathHolder {

    private final String sourceFilePath;
    private final String targetFilePath;

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    private FileDownloadPathHolder(String sourceFile, String targetFile) {
        this.sourceFilePath = sourceFile.replace("\\", "/");
        this.targetFilePath = targetFile.replace("\\", "/");
    }

    public static FileDownloadPathHolder createInstance(String sourceFilePath, String targetFilePath) {
        return new FileDownloadPathHolder(sourceFilePath, targetFilePath);
    }

    public String getTargetPathToMakeDirectory () {
        return targetFilePath.substring(0, targetFilePath.lastIndexOf("/") + 1);
    }

}
