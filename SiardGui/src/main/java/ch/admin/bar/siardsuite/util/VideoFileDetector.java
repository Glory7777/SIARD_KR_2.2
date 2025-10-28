package ch.admin.bar.siardsuite.util;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 동영상 파일을 감지하는 유틸리티 클래스
 */
public class VideoFileDetector {
    
    private static final Logger log = LoggerFactory.getLogger(VideoFileDetector.class);
    
    // 주요 동영상 파일 확장자들
    private static final Set<String> VIDEO_EXTENSIONS = new HashSet<>(Arrays.asList(
        "mp4", "avi", "mov", "wmv", "flv", "webm", "mkv", "m4v", "3gp", "mpg", "mpeg",
        "asf", "vob", "qt", "ogv", "f4v", "fli", "wmx", "wvx", "movie", "mj2", "jpm",
        "jpgv", "pyv", "uvu", "viv", "dvb", "fvt", "mxu", "ice"
    ));
    
    // 동영상 MIME 타입들
    private static final Set<String> VIDEO_MIME_TYPES = new HashSet<>(Arrays.asList(
        "video/mp4", "video/avi", "video/quicktime", "video/x-ms-wmv", "video/x-flv",
        "video/webm", "video/x-matroska", "video/x-msvideo", "video/mpeg", "video/x-ms-asf",
        "video/x-ms-vob", "video/ogg", "video/3gpp", "video/x-f4v", "video/x-fli",
        "video/x-ms-wmx", "video/x-ms-wvx", "video/x-sgi-movie", "video/mj2", "video/jpm",
        "video/jpeg", "video/vnd.dece.hd", "video/vnd.dece.mobile", "video/vnd.dece.pd",
        "video/vnd.dece.sd", "video/vnd.dece.video", "video/vnd.dvb.file", "video/vnd.fvt",
        "video/vnd.mpegurl", "video/vnd.ms-playready.media.pyv", "video/vnd.uvvu.mp4",
        "video/vnd.vivo", "video/x-m4v", "video/x-mng", "video/x-ms-wm", "video/x-conference/x-cooltalk"
    ));
    
    private final Tika tika;
    
    public VideoFileDetector() {
        this.tika = new Tika();
    }
    
    /**
     * 파일 확장자로 동영상 파일인지 확인
     * @param filename 파일명
     * @return 동영상 파일이면 true
     */
    public boolean isVideoByExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        
        String extension = getFileExtension(filename);
        return VIDEO_EXTENSIONS.contains(extension.toLowerCase());
    }
    
    /**
     * 바이트 배열로 동영상 파일인지 확인
     * @param bytes 파일의 바이트 배열
     * @return 동영상 파일이면 true
     */
    public boolean isVideoByContent(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return false;
        }
        
        try {
            String mimeType = tika.detect(bytes);
            return VIDEO_MIME_TYPES.contains(mimeType.toLowerCase());
        } catch (Exception e) {
            log.warn("Failed to detect MIME type for video content", e);
            return false;
        }
    }
    
    /**
     * InputStream으로 동영상 파일인지 확인
     * @param inputStream 파일의 InputStream
     * @return 동영상 파일이면 true
     */
    public boolean isVideoByContent(InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        
        try {
            String mimeType = tika.detect(inputStream);
            return VIDEO_MIME_TYPES.contains(mimeType.toLowerCase());
        } catch (Exception e) {
            log.warn("Failed to detect MIME type for video content", e);
            return false;
        }
    }
    
    /**
     * 파일명에서 확장자 추출
     * @param filename 파일명
     * @return 확장자 (점 제외)
     */
    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1);
    }
    
    /**
     * 동영상 파일이 감지되었을 때 사용자에게 표시할 메시지 생성
     * @param filename 파일명 (선택사항)
     * @return 사용자 친화적 오류 메시지
     */
    public String getVideoDetectionMessage(String filename) {
        if (filename != null && !filename.isEmpty()) {
            return String.format("동영상 파일 '%s'이 포함되어 있어 처리할 수 없습니다. 동영상 파일은 SIARD 아카이브에 포함할 수 없습니다.", filename);
        } else {
            return "동영상 파일이 포함되어 있어 처리할 수 없습니다. 동영상 파일은 SIARD 아카이브에 포함할 수 없습니다.";
        }
    }
}