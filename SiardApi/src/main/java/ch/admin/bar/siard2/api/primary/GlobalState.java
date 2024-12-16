package ch.admin.bar.siard2.api.primary;

import lombok.Getter;

public class GlobalState {

    @Getter
    private String filePath;

    @Getter
    private static final GlobalState instance = new GlobalState();

    private GlobalState() { }

    public void setFilePath(String filePath) {
        // 기존 filePath가 존재하면 초기화
        if (this.filePath != null) {
            System.out.println("Clearing existing file path: " + this.filePath);
            this.filePath = null;
        }

        // 새로운 filePath 설정
        this.filePath = filePath;
        System.out.println("File path set to: " + filePath);
        System.out.flush();
    }


    public void clearFilePath() {
        this.filePath = null;
        System.out.println("File path cleared.");
        System.out.flush();
    }


//    public synchronized void setFilePath(String filePath) {
//        this.filePath = filePath;
//        System.out.println("File path set to: " + filePath);
//        System.out.flush();
//    }
}
