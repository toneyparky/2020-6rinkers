package com.cocktailpick.back.common.util;

import java.security.SecureRandom;
import java.util.Random;

import com.cocktailpick.back.common.domain.DailyDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberOfDaily {
	public static long generateBy(DailyDate dailyDate) {
		int randomNumber = new SecureRandom(dailyDate.getBytes()).nextInt();
		return Math.abs(randomNumber);
	}
}