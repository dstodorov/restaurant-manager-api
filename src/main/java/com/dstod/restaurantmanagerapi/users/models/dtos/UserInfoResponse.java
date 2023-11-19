
package com.dstod.restaurantmanagerapi.users.models.dtos;

import java.util.List;

public record UserInfoResponse(
        String firstName,
        String middleName,
        String lastName,
        String username,
        List<String> roles,
        String email,
        String phoneNumber
) {

}

