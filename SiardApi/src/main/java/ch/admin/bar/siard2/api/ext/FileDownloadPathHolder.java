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

    private FileDownloadPathHolder(String sourceFile, String targetFile, boolean shouldReplaceSlashes) { // TODO :: shouldReplace는 추후에 로직 변경 필요
        this.sourceFilePath = sourceFile.replace("\\", "/");
        this.targetFilePath = targetFile.replace("\\", "/");
    }

    public static FileDownloadPathHolder createInstance(String sourceFilePath, String targetFilePath, boolean shouldReplaceSlashes) {
        return new FileDownloadPathHolder(sourceFilePath, targetFilePath, shouldReplaceSlashes);
    }

    public String getTargetPathToMakeDirectory () {
        return targetFilePath.substring(0, targetFilePath.lastIndexOf("/") + 1);
    }

}
