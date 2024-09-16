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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums;

public enum TimeZone {
	USER(""),
	PACIFIC_MIDWAY("Pacific/Midway"),
	HST("HST"),
	US_ALASKA("US/Alaska"),
	AMERICA_LOS_ANGELES("America/Los_Angeles"),
	AMERICA_DENVER("America/Denver"),
	AMERICA_CHICAGO("America/Chicago"),
	AMERICA_NEW_YORK("America/New_York"),
	AMERICA_PUERTO_RICO("America/Puerto_Rico"),
	US_EAST_INDIANA("US/East-Indiana"),
	AMERICA_SAO_PAULO("America/Sao_Paulo"),
	AMERICA_ARGENTINA_BUENOS_AIRES("America/Argentina/Buenos_Aires"),
	CANADA_ATLANTIC("Canada/Atlantic"),
	CANADA_NEWFOUNDLAND("Canada/Newfoundland"),
	ATLANTIC_CAPE_VERDE("Atlantic/Cape_Verde"),
	UTC("UTC"),
	EUROPE_LONDON("Europe/London"),
	EUROPE_BERLIN("Europe/Berlin"),
	EUROPE_MOSCOW("Europe/Moscow"),
	ASIA_RIYADH("Asia/Riyadh"),
	AFRICA_CAIRO("Africa/Cairo"),
	ASIA_ISTANBUL("Asia/Istanbul"),
	ASIA_TEHRAN("Asia/Tehran"),
	ASIA_DUBAI("Asia/Dubai"),
	ASIA_KARACHI("Asia/Karachi"),
	ASIA_CALCUTTA("Asia/Calcutta"),
	ASIA_DACCA("Asia/Dacca"),
	ASIA_BANGKOK("Asia/Bangkok"),
	ASIA_SHANGHAI("Asia/Shanghai"),
	AUSTRALIA_PERTH("Australia/Perth"),
	ASIA_TOKYO("Asia/Tokyo"),
	ASIA_SEOUL("Asia/Seoul"),
	AUSTRALIA_DARWIN("Australia/Darwin"),
	AUSTRALIA_SYDNEY("Australia/Sydney"),
	SST("SST"),
	NST("NST");

	private final String value;

	TimeZone(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
