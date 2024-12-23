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
            this.filePath = null;
        }

        // 새로운 filePath 설정
        this.filePath = filePath;
        System.out.flush();
    }


    // 다른데서 사용할 땐  GlobalState.getInstance().clearFilePath(); 호출
    public void clearFilePath() {
        this.filePath = null;
        System.out.flush();
    }


}
