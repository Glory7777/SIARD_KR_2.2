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

    // 추가된 메서드
    public String getRemainingSourcePath() {
        // 슬래시로 경로 나누기
        String[] sourceParts = sourceFilePath.split("/");
        String[] targetParts = targetFilePath.split("/");
        // 공통 부분 찾기
        int i = 0;
        while (i < sourceParts.length && i < targetParts.length && sourceParts[i].equals(targetParts[i])) {
            i++;
        }
        // 공통 요소 출력
        String[] commonParts = Arrays.copyOfRange(sourceParts, 0, i);
        System.out.println("Before removing common elements: " + String.join("/", sourceParts));
        System.out.println("Common elements: " + String.join("/", commonParts));
        // 공통 부분 이후의 경로 생성
        String[] remainingParts = Arrays.copyOfRange(sourceParts, i, sourceParts.length);

        // 마지막 요소(파일명) 제거
        if (remainingParts.length > 1) {
            remainingParts = Arrays.copyOf(remainingParts, remainingParts.length - 1);
            System.out.println("Remaining path after removing common elements: [Empty]");
        } else {
            return ""; // 파일명만 남은 경우 빈 문자열 반환
        }

        // 결과 출력
        String remainingPath = String.join("/", remainingParts);
        System.out.println("Remaining path after removing common elements: " + remainingPath);

        return remainingPath;
    }

}
