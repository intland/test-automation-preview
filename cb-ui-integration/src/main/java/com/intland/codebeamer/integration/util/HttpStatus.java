/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.util;

public enum HttpStatus {

	/** Default value */
	NONE(0),

	/** {@code 204 No Content} (HTTP/1.1 - RFC 7231) */
	NO_CONTENT(204),

	/** {@code 400 Bad Request} (HTTP/1.1 - RFC 2616) */
	BAD_REQUEST(400),

	/** {@code 401 Unauthorized} (HTTP/1.0 - RFC 1945) */
	UNAUTHORIZED(401),

	/** {@code 402 Payment Required} (HTTP/1.1 - RFC 2616) */
	PAYMENT_REQUIRED(402),

	/** {@code 403 Forbidden} (HTTP/1.0 - RFC 1945) */
	FORBIDDEN(403),

	/** {@code 404 Not Found} (HTTP/1.0 - RFC 1945) */
	NOT_FOUND(404),

	/** {@code 405 Method Not Allowed} (HTTP/1.1 - RFC 2616) */
	METHOD_NOT_ALLOWED(405),

	/** {@code 406 Not Acceptable} (HTTP/1.1 - RFC 2616) */
	NOT_ACCEPTABLE(406),

	/** {@code 407 Proxy Authentication Required} (HTTP/1.1 - RFC 2616)*/
	PROXY_AUTHENTICATION_REQUIRED(407),

	/** {@code 408 Request Timeout} (HTTP/1.1 - RFC 2616) */
	REQUEST_TIMEOUT(408),

	/** {@code 409 Conflict} (HTTP/1.1 - RFC 2616) */
	CONFLICT(409),

	/** {@code 410 Gone} (HTTP/1.1 - RFC 2616) */
	GONE(410),

	/** {@code 411 Length Required} (HTTP/1.1 - RFC 2616) */
	LENGTH_REQUIRED(411),

	/** {@code 412 Precondition Failed} (HTTP/1.1 - RFC 2616) */
	PRECONDITION_FAILED(412),

	/** {@code 413 Request Entity Too Large} (HTTP/1.1 - RFC 2616) */
	REQUEST_TOO_LONG(413),

	/** {@code 414 Request-URI Too Long} (HTTP/1.1 - RFC 2616) */
	REQUEST_URI_TOO_LONG(414),

	/** {@code 415 Unsupported Media Type} (HTTP/1.1 - RFC 2616) */
	UNSUPPORTED_MEDIA_TYPE(415),

	/** {@code 416 Requested Range Not Satisfiable} (HTTP/1.1 - RFC 2616) */
	REQUESTED_RANGE_NOT_SATISFIABLE(416),

	/** {@code 417 Expectation Failed} (HTTP/1.1 - RFC 2616) */
	EXPECTATION_FAILED(417),

	/** {@code 429 Too Many Requests} (Additional HTTP Status Codes - RFC 6585) */
	TOO_MANY_REQUESTS(429);

	private int code;

	HttpStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getCodeAsString() {
		return String.valueOf(code);
	}
}
