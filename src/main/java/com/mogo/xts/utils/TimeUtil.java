package com.mogo.xts.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final Timestamp MAX_EXPIRE_DATE = getMaxExpireTime();

	private static Timestamp getMaxExpireTime() {
		Timestamp rtn = Timestamp.valueOf("2100-01-01 00:00:00");
		return rtn;
	}

	public static Date addOrMinusYear(long ti, int i) throws Exception {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(1, i);
		rtn = cal.getTime();
		return rtn;
	}

	public static Date addOrMinusMonth(long ti, int i) throws Exception {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(2, i);
		rtn = cal.getTime();
		return rtn;
	}

	public static Date addOrMinusWeek(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(3, i);
		rtn = cal.getTime();
		return rtn;
	}

	public static Date addOrMinusDays(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(5, i);
		rtn = cal.getTime();
		return rtn;
	}

	public static Date addOrMinusHours(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(10, i);
		rtn = cal.getTime();
		return rtn;
	}

	public static Date addOrMinusMinutes(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(12, i);
		rtn = cal.getTime();
		return rtn;
	}

	public static Date addOrMinusSecond(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(13, i);
		rtn = cal.getTime();
		return rtn;
	}

	public static int yearsBetween(Date start, Date end) {
		return Years.yearsBetween(LocalDate.fromDateFields(start),
				LocalDate.fromDateFields(end)).getYears();
	}

	public static int monthsBetween(Date start, Date end) {
		return Months.monthsBetween(LocalDate.fromDateFields(start),
				LocalDate.fromDateFields(end)).getMonths();
	}

	public static int weeksBetween(Date start, Date end) {
		return Weeks.weeksBetween(LocalDate.fromDateFields(start),
				LocalDate.fromDateFields(end)).getWeeks();
	}

	public static int daysBetween(Date start, Date end) {
		return Days.daysBetween(LocalDate.fromDateFields(start),
				LocalDate.fromDateFields(end)).getDays();
	}

	public static int hoursBetween(Date start, Date end) {
		return Hours.hoursBetween(LocalDate.fromDateFields(start),
				LocalDate.fromDateFields(end)).getHours();
	}

	public static int minutesBetween(Date start, Date end) {
		return Minutes.minutesBetween(LocalDate.fromDateFields(start),
				LocalDate.fromDateFields(end)).getMinutes();
	}

	public static int secondsBetween(Date start, Date end) {
		return Seconds.secondsBetween(LocalDate.fromDateFields(start),
				LocalDate.fromDateFields(end)).getSeconds();
	}

	public static Timestamp getNextMonthStartDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(5, 1);
		rightNow.set(11, 0);
		rightNow.set(14, 0);
		rightNow.set(13, 0);
		rightNow.set(12, 0);
		rightNow.set(2, rightNow.get(2) + 1);
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static Timestamp getBeforeMonthStartDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(5, 1);
		rightNow.set(11, 0);
		rightNow.set(14, 0);
		rightNow.set(13, 0);
		rightNow.set(12, 0);
		rightNow.set(2, rightNow.get(2) - 1);
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static Timestamp getCurrentMonthEndDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(5, rightNow.getActualMaximum(5));
		rightNow.set(11, 23);
		rightNow.set(14, 59);
		rightNow.set(13, 59);
		rightNow.set(12, 59);
		rightNow.set(2, rightNow.get(2));
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static Timestamp getCurrentMonthFirstDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(5, 1);
		rightNow.set(11, 0);
		rightNow.set(14, 0);
		rightNow.set(13, 0);
		rightNow.set(12, 0);
		rightNow.set(2, rightNow.get(2));
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static Timestamp getCurrentDayEndDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(11, 23);
		rightNow.set(14, 59);
		rightNow.set(13, 59);
		rightNow.set(12, 59);
		rightNow.set(2, rightNow.get(2));
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static Timestamp getCurrentDayStartDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(11, 0);
		rightNow.set(14, 0);
		rightNow.set(13, 0);
		rightNow.set(12, 0);
		rightNow.set(2, rightNow.get(2));
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static Timestamp getBeforeDayEndDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(5, rightNow.get(5) - 1);
		rightNow.set(11, 23);
		rightNow.set(14, 59);
		rightNow.set(13, 59);
		rightNow.set(12, 59);
		rightNow.set(2, rightNow.get(2));
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static Timestamp getNextDayStartDay(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(5, rightNow.get(5) + 1);
		rightNow.set(11, 0);
		rightNow.set(14, 0);
		rightNow.set(13, 0);
		rightNow.set(12, 0);
		rightNow.set(2, rightNow.get(2));
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static String getYYYY_MM_DD(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}

	public static String getYYYYMMDDHHMMSS(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateformat.format(date);
	}

	public static String getYYYY_MM_DD_HH_MM_SS(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dateformat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		return dateformat.format(date);
	}

	public static Date getYYYY_MM_DD_HH_MM_SS(String dateString) {
		Date date = null;
		try {
			if (StringUtils.isBlank(dateString)) {
				return null;
			}
			DateFormat dateformat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			date = dateformat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;

	}
	
	public static Date getYYYY_MM_DD(String dateString) {
		Date date = null;
		try {
			if (dateString == null) {
				return null;
			}
			DateFormat dateformat = new SimpleDateFormat(YYYY_MM_DD);
			date = dateformat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;

	}

	public static String getYYYYMMDD(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		return dateformat.format(date);
	}

	public static String getYYYYMM(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dateformat = new SimpleDateFormat("yyyyMM");
		return dateformat.format(date);
	}

	public static String getMM(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dateformat = new SimpleDateFormat("MM");
		return dateformat.format(date);
	}

	public static Timestamp getSysDate() {
		Timestamp rtn = new Timestamp(System.currentTimeMillis());
		return rtn;
	}

	public static Timestamp getMaxExpire() {
		return MAX_EXPIRE_DATE;
	}

	public static Timestamp getTimstampByString(String strDate, String mask)
			throws Exception {
		if (strDate == null) {
			return null;
		}
		DateFormat dateformat = new SimpleDateFormat(mask);
		return new Timestamp(dateformat.parse(strDate).getTime());
	}

	public static Timestamp getBillMonthDate(Date beginDate, Date endDate) {
		if (null == beginDate) {
			return null;
		}

		Timestamp monthEndDate = new Timestamp(addOrMinusDays(
				getNextMonthStartDate(endDate).getTime(), -1).getTime());
		return new Timestamp(monthEndDate.getTime());
	}

	public static Timestamp getTruncDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(11, 0);
		rightNow.set(14, 0);
		rightNow.set(13, 0);
		rightNow.set(12, 0);
		return new Timestamp(rightNow.getTimeInMillis());
	}

	public static long getDistanceTime(Date date1, Date date2) {
		return date1.getTime() - date2.getTime();
	}

	/**
	 * 获取n天之后（之前）的时间
	 * 
	 * @param date
	 * @param cutDay
	 * @return
	 */
	public static Date addDistanceTime(Date date, String cutDay) {
		if (StringUtils.isBlank(cutDay)) {
			return date;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +Integer.parseInt(cutDay));// +1今天的时间加一天
		date = calendar.getTime();
		return date;
	}

	/**
	 * 比较时间大小 date1=date2: return 0 --- date1>date2: return 1 --- date1<date2:
	 * return -1 ---
	 */
	public static int compare_date(Date date1, Date date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dt1 = df.parse(df.format(date1));
			Date dt2 = df.parse(df.format(date2));
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取指定日期前后几天的开始时间
	 * 
	 */
	public static Date getSpecifiedDayBefore(Date time, int cutDay) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			c.setTime(time);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day + cutDay);
			String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
					.getTime());
			date = df.parse(dayBefore + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取指定日期前后几天的结束时间
	 * 
	 */
	public static Date getSpecifiedDayAfter(Date time, int cutDay) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			c.setTime(time);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day + cutDay);
			String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
					.getTime());
			date = df.parse(dayBefore + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean betweenCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (date.after(begin) && date.before(end)) {
			return true;
		} else if (nowTime.compareTo(beginTime) == 0
				|| nowTime.compareTo(endTime) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 两个日期相差多少天小时分钟等
	 * @param startDate
	 * @param endDate
	 * @param type 1 天 2 小时 3 分钟 4 秒
	 * @return
	 */
	public static long getDatePoor(Date startDate, Date endDate, int type) {

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - startDate.getTime();
		// 计算差多少天
		if (type == 1) {
			return diff / nd;
			// 计算差多少小时
		} else if (type == 2) {
			return diff / nh;
			// 计算差多少分钟
		} else if (type == 3) {
			return diff / nm;
			// 计算差多少秒
		} else if (type == 4){
			return diff / ns;
		}
		return 0;

	}
	
	/**
	 * 获取当天的零点零分零秒和23点59分59秒
	 * flag  1:零点 2：23点
	 * @return
	 * @throws ParseException 
	 */
	public static Timestamp getThisDayStartEndDate(int flag) throws ParseException {
	    SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	     
	    Date start = formater2.parse(formater.format(new Date())+ " 00:00:00");
	    Date end = formater2.parse(formater.format(new Date())+ " 23:59:59");
        if(flag==1){
        	return new Timestamp(start.getTime()); 
        }
    	return new Timestamp(end.getTime());
	}
	
	/**
	   * 比较时间大小
	   * date1=date2: return 2 ---
	   * date1>date2: return 1 ---
	   * date1<date2: return -1 ---
	   */
	  public static int compare_date2(Date date1,Date date2){
		  int i =0;
		  try {
	          if (date1.getTime() > date2.getTime()) {
	              i=1;
	          } else if (date1.getTime() < date2.getTime()) {
	              i=-1;
	          } else {
	              i=2;
	          }
	      } catch (Exception exception) {
	          exception.printStackTrace();
	      }
	      return i;
	  }

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 *            date2 > date1
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}
	
	/**
	 * 将时间戳转换为时间
	 * @param s
	 * @return
	 */
	public static Date stampToDate(String s) {
		return new Date(new Long(s));
	}

    public static String getSmsYMD(Date date){
        String str = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(str)){
            sb.append(str.substring(2,4)+"年");
            sb.append(str.substring(str.indexOf("年")+1,str.indexOf("月"))+"月");
            sb.append(str.substring(str.indexOf("月")+1,str.indexOf("日"))+"日");
        }
        return  sb.toString();
    }

    public static String getSmsMD(Date date){
        String str = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(str)){

            sb.append(str.substring(str.indexOf("年")+1,str.indexOf("月"))+"月");
            sb.append(str.substring(str.indexOf("月")+1,str.indexOf("日"))+"日");
        }
        return  sb.toString();
    }

    public static String getSmsMDH(Date date){
        String str = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(date);
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(str)){

            sb.append(str.substring(str.indexOf("年")+1,str.indexOf("月"))+"月");
            sb.append(str.substring(str.indexOf("月")+1,str.indexOf("日"))+"日");
            sb.append(str.substring(str.indexOf("日")+1,str.indexOf("时"))+"时");
        }
        return  sb.toString();
    }


    public static String getSmsDay(Date date){
        String str = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(str)){
            sb.append(str.substring(str.indexOf("月")+1,str.indexOf("日"))+"日");
        }
        return  sb.toString();
    }

    //获取月份最后一天
    public static String getMonthLastDay(Date date){
		 String lastday;
		 Calendar cale = null;
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 cale = Calendar.getInstance();
		 cale.setTime(date);
		 cale.add(Calendar.MONTH, 1);
		 cale.set(Calendar.DAY_OF_MONTH, 0);
		 lastday = format.format(cale.getTime());
         return  lastday;
    }

    public static void main(String[] args) {
    	try {
			Date a=addOrMinusMonth(new Date().getTime() , 2);
			System.out.println(getYYYY_MM_DD_HH_MM_SS(a));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
