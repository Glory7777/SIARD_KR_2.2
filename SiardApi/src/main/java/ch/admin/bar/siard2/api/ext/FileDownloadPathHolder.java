package ch.admin.bar.siard2.api.ext;

import java.util.Arrays;

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

    // 공통 경로를 제외한 상대 경로 계산
    public String getRemainingSourcePath() {
        String[] sourceParts = sourceFilePath.split("/");
        String[] targetParts = targetFilePath.split("/");
        // 공통 부분 찾기
        int commonLength = 0;
        while (commonLength < sourceParts.length && commonLength < targetParts.length && sourceParts[commonLength].equals(targetParts[commonLength])) {
            commonLength++;
        }
        // 공통 부분 이후의 경로 생성
        String[] remainingParts = Arrays.copyOfRange(sourceParts, commonLength, sourceParts.length - 1);

        return String.join("/", remainingParts);
    }

}
