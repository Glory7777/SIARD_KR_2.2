package ch.admin.bar.siard2.api.ext;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class SftpConnection {

    @Builder.Default
    private String host = "";
    private int port;
    @Builder.Default
    private String user = "";
    @Builder.Default
    private String password = "";
    private boolean ready;

}
