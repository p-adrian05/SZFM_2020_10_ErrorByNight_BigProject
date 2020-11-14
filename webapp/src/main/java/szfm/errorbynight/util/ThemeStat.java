package szfm.errorbynight.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemeStat {

    private String title;
    private int topicCount;
    private String founderUsername;
    private String founderUserProfileImageName;
    private String lastActive;
    private String timestamp;

}