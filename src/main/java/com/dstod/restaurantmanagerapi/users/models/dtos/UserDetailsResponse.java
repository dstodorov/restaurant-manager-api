
package com.dstod.restaurantmanagerapi.users.models.dtos;

import java.util.List;

public record UserDetailsResponse(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String username,
        List<String> roles,
        String email,
        String phoneNumber
) {

}

