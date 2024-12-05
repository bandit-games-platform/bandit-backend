package be.kdg.int5.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Invite has already been sent" )
public class InviteStatusExistsException extends RuntimeException{
}


