package dev.jae.users.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private String id;

    private String username;

    public static UserDto from (User user){
        return builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
