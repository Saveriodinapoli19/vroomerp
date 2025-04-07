package com.vroomerp.common.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

public class DateUtils {

	static Logger logger = Logger.getLogger(DateUtils.class);

	private static ZoneId idz = ZoneId.of("Europe/Rome");

	public static Date atStartOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	public static Date atEndOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return localDateTimeToDate(endOfDay);
	}

	public static boolean valorizzaLaGiornataCheck(Date date, String timeToSend) {

		String[] timeCheck = timeToSend.split(":");

		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date); // assigns calendar to given date
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minutes = calendar.get(Calendar.MINUTE);// gets hour in 24h format
		Integer hourTimeCheck = Integer.parseInt(timeCheck[0].trim());
		Integer minuteTimeCheck = Integer.parseInt(timeCheck[1].trim());

		// logger.info("dayminute ["+((hour * 60) + minutes)+"] dayminutecheck
		// ["+((hourTimeCheck * 60) + minuteTimeCheck)+"]");

// PEER: Sostituzione if con return bool
		// anche un errore logico hour >= hourTimeCheck && minutes >= minuteTimeCheck

		return ((hour * 60) + minutes) >= ((hourTimeCheck * 60) + minuteTimeCheck);
	}

	private static LocalDateTime dateToLocalDateTime(Date date) {

		return LocalDateTime.ofInstant(date.toInstant(), idz);
	}

	private static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(idz).toInstant());
	}

	public static boolean dailyCheckExpiration(Date date, long expirationTimeMillis) {

		boolean result = false;

		Long differenceFromNowInMillis = new Date().getTime() - date.getTime();

		if (differenceFromNowInMillis > expirationTimeMillis)
			result = true;

		return result;
	}

	public static boolean checkRecursiveMessageFromRange(String dateFrom, String dateTo, Integer recursiveDatyOfWeek) {

		boolean result = false;

		Integer firstDayOfWeek = 0;
		Integer lastDayOfWeek = 0;
		Integer firstValue = 0;
		Integer lastValue = 0;
		try {
			firstDayOfWeek = StringUtils.getDayOfWeekFromDate(StringUtils.dateFromString(dateFrom));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (dateTo != null) {

			try {
				lastDayOfWeek = StringUtils.getDayOfWeekFromDate(StringUtils.dateFromString(dateTo));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			lastDayOfWeek = StringUtils.getDayOfWeekFromDate(new Date());
		}

		if (lastDayOfWeek > firstDayOfWeek) {
			firstValue = firstDayOfWeek;
			lastValue = lastDayOfWeek;
		}

		else {
			firstValue = lastDayOfWeek;
			lastValue = firstDayOfWeek;
		}

		if (IntStream.rangeClosed(firstValue, lastValue).boxed().collect(Collectors.toList())
				.contains(recursiveDatyOfWeek)) {
			result = true;

		}

		return result;
	}

	public static boolean checkDailyAppointment(Date dataAppuntamento, long expirationTimeMillis, Date dataAttuale) {

		boolean result = true;

		Date dataMezzanotteDomani = atEndOfDay(new Date(dataAttuale.getTime() + expirationTimeMillis));

		if (dataAppuntamento.getTime() > dataMezzanotteDomani.getTime()) {
			result = false;

		}

		return result;
	}

}
