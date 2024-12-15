package ch.admin.bar.siard2.api.primary;

import lombok.Getter;

public class GlobalState {

    @Getter
    private String filePath;

    @Getter
    private static final GlobalState instance = new GlobalState();

    private GlobalState() { }

    public synchronized void setFilePath(String filePath) {
        this.filePath = filePath;
        System.out.println("File path set to: " + filePath);
        System.out.flush();
    }
}
