package com.vroomerp.common.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import org.apache.log4j.Logger;



public class StringUtils {

	static Logger logger = Logger.getLogger(StringUtils.class);

	private static SimpleDateFormat sdfNoHour = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat sdfDateHour = new SimpleDateFormat("yyyyMMddHH:mm");
	private static SimpleDateFormat sdfDateHourMin = new SimpleDateFormat("yyyyMMddHHmm");
	private static SimpleDateFormat sdfMongoDate = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat sdfReadableStringDateHour = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static SimpleDateFormat sdfReadableStringDate = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdfOnlyDayOfWeek = new SimpleDateFormat("EE");

	public static SimpleDateFormat sdfDateWithMinusNoHour = new SimpleDateFormat("dd-MM-yyyy");

	private static NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("it", "IT"));
	private static DecimalFormat df = new DecimalFormat("###.##");

	public static int getMinutesOfDayFromDate(Date recursiveTime) {

		Integer currentDayMinute = null;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(recursiveTime);
			int hour = now.get(Calendar.HOUR_OF_DAY);
			int minute = now.get(Calendar.MINUTE);

			currentDayMinute = ((hour * 60) + minute);
		}

		catch (Exception exception) {

			logger.error(exception.getMessage(), exception);
		}

		return currentDayMinute;
	}
	
	public static String dateWithoutHourStringByMillis(long millis) {

		return sdfReadableStringDate.format(new Date(millis));

	}
	
	public static String dateWithHourStringByMillis(long millis) {

		return sdfReadableStringDateHour.format(new Date(millis));

	}
	
	public static String removeCent(int number) {
		return (number / 100) + "";
	}

	public static int getMinutesFromRecursiveTimeString(String recursiveTime) {

		Integer totalMinutes = null;

		try {

			Integer minutesFromHourString = Integer.parseInt(recursiveTime.substring(0, 2));
			Integer remainingMinute = Integer.parseInt(recursiveTime.substring(2, 4));

			if (minutesFromHourString != null && minutesFromHourString > 0) {

				totalMinutes = minutesFromHourString * 60;

			}

			if (remainingMinute != null && remainingMinute > 0) {

				totalMinutes += remainingMinute;

			}
		}

		catch (Exception exception) {

			logger.error(exception.getMessage(), exception);
		}
		return totalMinutes;
	}

	public static String getExpirationActivitiesDate(Long expTimeMillis) {

		String dateString = null;

		try {

			Date dataAttuale = new Date();

			dateString = sdfReadableStringDate.format(new Date(dataAttuale.getTime() - expTimeMillis));

		}

		catch (Exception exception) {

			logger.error(exception.getMessage(), exception);
		}

		return dateString;
	}

	public static int getWeekFromDate(Date date) {

		Integer week = null;

		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			week = c.get(Calendar.WEEK_OF_YEAR);

		}

		catch (Exception exception) {

			logger.error(exception.getMessage(), exception);

		}

		return week;
	}

	public static int getDayOfYear(Date date) {

		Integer dayOfYear = null;

		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			dayOfYear = c.get(Calendar.DAY_OF_YEAR);

		}

		catch (Exception exception) {

			logger.error(exception.getMessage(), exception);

		}

		return dayOfYear;
	}

	public static int getDayOfWeekFromDate(Date date) {

		Integer dayOfWeek = null;

		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		}

		catch (Exception exception) {

			logger.error(exception.getMessage(), exception);

		}

		return dayOfWeek;
	}

	public static String getRecursiveTime(String recursiveTime) {

		String readibleTime;

		readibleTime = recursiveTime.substring(0, 2) + ":" + recursiveTime.substring(2, 4);

		return readibleTime;
	}

	public static String getRecursiveDay(Integer recursive) {

		String recursiveDay = null;
		try {
			switch (recursive) {

			case 1:

				recursiveDay = "Domenica";

				break;

			case 2:

				recursiveDay = "Luned�";

				break;

			case 3:

				recursiveDay = "Marted�";

				break;

			case 4:

				recursiveDay = "Mercoled�";

				break;

			case 5:

				recursiveDay = "Gioved�";

				break;

			case 6:

				recursiveDay = "Venerd�";

				break;

			case 7:

				recursiveDay = "Sabato";

				break;

			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return recursiveDay;

	}

	public static Date dateFromString(String stringDate) throws ParseException {

		return sdfNoHour.parse(stringDate);

	}

	public static String stringFromDateForCalendar(Date date) {

		String dateString = stringFromDate(date);

		String monthString = dateString.substring(4, 6);
		Integer monthMinusOne = Integer.parseInt(monthString) - 1;
		String monthStringMinusOne = String.format("%02d", monthMinusOne);
		String fullStringMinusOne = dateString.substring(0, 4) + monthStringMinusOne
				+ dateString.substring(6, dateString.length());

		return fullStringMinusOne;
	}

	public static String stringWithHourFromDateForCalendar(Date date) {

		String dateString = stringWithHourFromDate(date);

		String monthString = dateString.substring(4, 6);
		Integer monthMinusOne = Integer.parseInt(monthString) - 1;
		String monthStringMinusOne = String.format("%02d", monthMinusOne);
		String fullStringMinusOne = dateString.substring(0, 4) + monthStringMinusOne
				+ dateString.substring(6, dateString.length());

		return fullStringMinusOne;

	}

	public static String stringFromDate(Date date) {

		try {
			return sdfNoHour.format(date);
		} catch (Exception ex) {
			return sdfNoHour.format(new Date());
		}

	}

	public static String mongoStringDateFromDate(Date date) {

		try {
			return sdfMongoDate.format(date);
		} catch (Exception ex) {
			return sdfMongoDate.format(new Date());
		}

	}

	public static String stringWithHourFromDate(Date date) {

		try {
			return sdfDateHour.format(date);
		} catch (Exception ex) {
			return sdfDateHour.format(new Date());
		}

	}

	public static String stringOnlyHourFromDate(Date date) {

		try {
			return sdfHour.format(date);
		} catch (Exception ex) {
			return sdfNoHour.format(new Date());
		}

	}

	public static String readableStringFromDateNoHourAndMin(Date date) {

		try {
			return sdfReadableStringDate.format(date);
		} catch (Exception ex) {
			return sdfNoHour.format(new Date());
		}

	}

	public static String readableStringFromDate(Date date) {

		try {
			return sdfReadableStringDateHour.format(date);
		} catch (Exception ex) {
			return sdfNoHour.format(new Date());
		}

	}

	public static Date dateFromStringWithHourAndMinutes(String stringDate) throws ParseException {

		return sdfDateHourMin.parse(stringDate);

	}

	public static Date dateHourFromString(String stringDate) throws ParseException {

		return sdfDateHour.parse(stringDate);

	}

	public static String loadResource(String path) throws Exception {

		String outputText = "";
		try (InputStream inputStream = StringUtils.class.getResourceAsStream(path);
				Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
			outputText = scanner.useDelimiter("\\A").next();
		}
		return outputText;
	}

	public static String base64MimeType(String base64StringWithoutMimeType, String mimeType) {

		String base64MimeType = ("data:MIME_TYPE;base64," + base64StringWithoutMimeType).replaceAll("MIME_TYPE",
				mimeType);
		return base64MimeType;

	}

	public static String formatCentsToEuro(int cents) {
		return nf.format(((float) cents) / 100.0f);

	}

	public static String formatFloatToDecimal(float f) {
		return df.format(f);

	}

	public static String date7BeforeString(Date dataCompletamento) {

		Calendar calendar = Calendar.getInstance(Locale.ITALIAN);
		calendar.setTime(dataCompletamento);
		calendar.get(Calendar.DAY_OF_WEEK);

		Date date7before = calendar.getTime();

		return sdfOnlyDayOfWeek.format(date7before).toUpperCase();

	}

}
