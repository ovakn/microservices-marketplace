package org.example.userService.mappers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.userService.DTOs.AddressRequest;
import org.example.userService.DTOs.AddressResponse;
import org.example.userService.entities.Address;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class AddressMapper {
    public AddressResponse toDTO(Address address) {
        return new AddressResponse(
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getPostCode()
        );
    }

    public Address toAddress(AddressRequest addressRequest) {
        return new Address(
                addressRequest.getCountry(),
                addressRequest.getCity(),
                addressRequest.getStreet(),
                addressRequest.getPostCode()
        );
    }
}