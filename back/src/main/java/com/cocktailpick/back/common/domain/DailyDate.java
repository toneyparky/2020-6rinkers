package com.cocktailpick.back.common.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyDate {
	private final Date date;

	public static DailyDate of(Date date) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String format = simpleDateFormat.format(date);

			return new DailyDate(simpleDateFormat.parse(format));
		} catch (ParseException e) {
			throw new IllegalArgumentException("날짜를 파싱할 수 없습니다.");
		}
	}

	public Date getDate() {
		return date;
	}

	public byte[] getBytes() {
		String time = String.valueOf(date.getTime());
		return time.getBytes();
	}

}

