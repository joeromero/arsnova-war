package de.thm.arsnova.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.thm.arsnova.exceptions.BadRequestException;
import de.thm.arsnova.exceptions.ForbiddenException;
import de.thm.arsnova.exceptions.NoContentException;
import de.thm.arsnova.exceptions.NotFoundException;
import de.thm.arsnova.exceptions.PreconditionFailedException;
import de.thm.arsnova.exceptions.UnauthorizedException;

public class AbstractController {
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public void handleNotFoundException(final Exception e, final HttpServletRequest request) {
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenException.class)
	public void handleForbiddenException(final Exception e, final HttpServletRequest request) {
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public void handleUnauthorizedException(final Exception e, final HttpServletRequest request) {
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(NoContentException.class)
	public void handleNoContentException(final Exception e, final HttpServletRequest request) {
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public void handleBadRequestException(final Exception e, final HttpServletRequest request) {
	}

	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	@ExceptionHandler(PreconditionFailedException.class)
	public void handlePreconditionFailedException(final Exception e, final HttpServletRequest request) {
	}
}
