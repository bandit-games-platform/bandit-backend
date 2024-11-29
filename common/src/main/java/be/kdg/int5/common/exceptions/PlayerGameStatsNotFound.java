package be.kdg.int5.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such player and game stats" )
public class PlayerGameStatsNotFound extends RuntimeException{
}


