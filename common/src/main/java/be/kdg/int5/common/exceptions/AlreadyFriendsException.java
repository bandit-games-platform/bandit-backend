package be.kdg.int5.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Players are already friends." )
public class AlreadyFriendsException extends RuntimeException{
}


