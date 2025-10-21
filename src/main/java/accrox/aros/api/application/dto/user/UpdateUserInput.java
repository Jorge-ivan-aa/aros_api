package accrox.aros.api.application.dto.user;

import accrox.aros.api.application.dto.area.GetAreaInput;

import java.util.Collection;

public record UpdateUserInput (

        String document,

        String name,

        String email,

        String password,

        String address,

        String phone

){
}
