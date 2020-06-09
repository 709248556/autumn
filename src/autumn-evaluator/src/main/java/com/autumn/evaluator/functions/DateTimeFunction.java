package com.autumn.evaluator.functions;

import com.autumn.evaluator.*;
import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;
import com.autumn.evaluator.exception.FunctionParamException;
import com.autumn.util.DateUtils;
import com.autumn.util.TypeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期与时间函数
 */
public class DateTimeFunction {

    /**
     * 返回一个日期时间在另外一个日期时间的基础添加指定的年\月\日\时\分\秒
     */
    @FunctionRegister(name = "DateAddYMDHMS", category = "日期与时间", caption = "返回一个日期时间在另外一个日期时间的基础添加指定的年、月、日、时、分、秒 。", minParamCount = 7)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    @ParamRegister(order = 2, name = "year", caption = "增加或减少的年数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 3, name = "month", caption = "增加或减少的月数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 4, name = "day", caption = "增加或减少的天数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 5, name = "hour", caption = "增加或减少的小时数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 6, name = "minute", caption = "增加或减少的分钟数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 7, name = "second", caption = "增加或减少的秒数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    public static class DateAddYMDHMS extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            Variant p3 = paramContext.getParam(3).getValue();
            Variant p4 = paramContext.getParam(4).getValue();
            Variant p5 = paramContext.getParam(5).getValue();
            Variant p6 = paramContext.getParam(6).getValue();
            Variant p7 = paramContext.getParam(7).getValue();
            java.util.Date date1 = p1.toDate();
            java.util.Date date2 = DateUtils.addYear(date1, p2.intValue());
            java.util.Date date3 = DateUtils.addMonth(date2, p3.intValue());
            java.util.Date date4 = DateUtils.addDay(date3, p4.intValue());
            java.util.Date date5 = DateUtils.addHour(date4, p5.intValue());
            java.util.Date date6 = DateUtils.addMinute(date5, p6.intValue());
            java.util.Date date7 = DateUtils.addSecond(date6, p7.intValue());
            return new Variant(date7);
        }
    }


    /**
     * 返回一个日期在另外一个日期的基础添加指定的年\月\日
     */
    @FunctionRegister(name = "DateAddYMD", category = "日期与时间", caption = "返回一个日期在另外一个日期的基础添加指定的年、月、日 。", minParamCount = 4)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    @ParamRegister(order = 2, name = "year", caption = "增加或减少的年数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 3, name = "month", caption = "增加或减少的月数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 4, name = "day", caption = "增加或减少的天数,可以为正数与负数，如-5。", paramType = VariantType.NUMBER)
    public static class DateAddYMD extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            Variant p3 = paramContext.getParam(3).getValue();
            Variant p4 = paramContext.getParam(4).getValue();
            java.util.Date date1 = p1.toDate();
            java.util.Date date2 = DateUtils.addYear(date1, p2.intValue());
            java.util.Date date3 = DateUtils.addMonth(date2, p3.intValue());
            java.util.Date date4 = DateUtils.addDay(date3, p4.intValue());
            return new Variant(date4);
        }
    }


    /**
     * 返回一个日期中的月低最后一天
     */
    @FunctionRegister(name = "MonthLastDay", category = "日期与时间", caption = "返回一个日期中的月低最后一天 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class MonthLastDay extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(getListDay(p1.toDate()));
        }
    }

    private static int getListDay(java.util.Date theDate) {
        if (DateUtils.getMonth(theDate) == 2) {
            int year = DateUtils.getYear(theDate);
            // 是否是闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        } else {
            if (DateUtils.getMonth(theDate) == 1 || DateUtils.getMonth(theDate) == 3 || DateUtils.getMonth(theDate) == 5 || DateUtils.getMonth(theDate) == 7 || DateUtils.getMonth(theDate) == 8 || DateUtils.getMonth(theDate) == 10 || DateUtils.getMonth(theDate) == 12) {
                return 31;
            } else {
                return 30;
            }
        }

    }


    /**
     * 返回一个日期中的年份
     */
    @FunctionRegister(name = "Year", category = "日期与时间", caption = "返回一个日期中的年份 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class Year extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(DateUtils.getYear(p1.toDate()));
        }
    }


    /**
     * 返回一个日期中的星期
     */
    @FunctionRegister(name = "WeekDay", category = "日期与时间", caption = "返回一个日期中的星期 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    @ParamRegister(order = 2, name = "type", caption = "返回类型，0表示返回1-7，1表示返回星期一到星期日，其他表示返回英文Monday到Sunday 。", paramType = VariantType.NUMBER)
    public static class WeekDay extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            int runType = p2.intValue();
            java.util.Date date1 = p1.toDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            Object runValue = weekType(c.get(Calendar.DAY_OF_WEEK) - 1, runType);
            if (runType == 0) {
                return new Variant(runValue);
            } else {
                return new Variant(String.valueOf(runValue));
            }
        }
    }

    /**
     * 返回星期类型
     *
     * @param week  星期对象
     * @param wtype 0表示返回整数,1表示返回中文,其他表示英文
     * @return
     */
    private static Object weekType(int week, int wtype) {
        Object runobj;
        switch (week) {
            case 5:
                if (wtype == 0) {
                    runobj = 5;
                } else if (wtype == 1) {
                    runobj = "星期五";
                } else {
                    runobj = "5";
                }
                break;
            case 1:
                if (wtype == 0) {
                    runobj = 1;
                } else if (wtype == 1) {
                    runobj = "星期一";
                } else {
                    runobj = "1";
                }
                break;
            case 6:
                if (wtype == 0) {
                    runobj = 6;
                } else if (wtype == 1) {
                    runobj = "星期六";
                } else {
                    runobj = "6";
                }
                break;
            case 0:
                if (wtype == 0) {
                    runobj = 7;
                } else if (wtype == 1) {
                    runobj = "星期日";
                } else {
                    runobj = "0";
                }
                break;
            case 4:
                if (wtype == 0) {
                    runobj = 4;
                } else if (wtype == 1) {
                    runobj = "星期四";
                } else {
                    runobj = "4";
                }
                break;
            case 2:
                if (wtype == 0) {
                    runobj = 2;
                } else if (wtype == 1) {
                    runobj = "星期二";
                } else {
                    runobj = "2";
                }
                break;
            case 3:
                if (wtype == 0) {
                    runobj = 3;
                } else if (wtype == 1) {
                    runobj = "星期三";
                } else {
                    runobj = "3";
                }
                break;
            default:
                runobj = "";
                break;
        }
        return runobj;
    }


    /**
     * 返回一当前机器中的日期
     */
    @FunctionRegister(name = "ToDay", category = "日期与时间", caption = "返回当前机器的日期 。")
    public static class ToDay extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(new java.util.Date());
        }
    }


    /**
     * 日期转换
     */
    @FunctionRegister(name = "DateConvert", category = "日期与时间", caption = "将代表日期字符窜转换成日期 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "具有日期格式的字符窜。", paramType = VariantType.STRING)
    public static class DateConvert extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(DateUtils.parseDate(p1.getValue().toString()));
        }
    }


    /**
     * 返回一个日期时间中的秒钟
     */
    @FunctionRegister(name = "Second", category = "日期与时间", caption = "返回一个日期时间中的秒钟 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class Second extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            java.util.Date date1 = p1.toDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            return new Variant(c.get(Calendar.SECOND));
        }
    }


    /**
     * 返回当前机器中的日期与时间
     */
    @FunctionRegister(name = "Now", category = "日期与时间", caption = "返回当前机器中的日期与时间 。")
    public static class Now extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(new java.util.Date());
        }
    }


    /**
     * 返回一个日期中的月份
     */
    @FunctionRegister(name = "Month", category = "日期与时间", caption = " 返回一个日期中的月份。", minParamCount = 1)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class Month extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(DateUtils.getMonth( p1.toDate()));
        }
    }


    /**
     * 返回一个日期时间中的分钟数
     */
    @FunctionRegister(name = "Minute", category = "日期与时间", caption = "返回一个日期时间中的分钟数 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class Minute extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Calendar c = Calendar.getInstance();
            c.setTime(p1.toDate());
            return new Variant(c.get(Calendar.MINUTE));
        }
    }


    /**
     * 返回一个日期时间中的小时数
     */
    @FunctionRegister(name = "Hour", category = "日期与时间", caption = "返回一个日期时间中的小时数 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class Hour extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Calendar c = Calendar.getInstance();
            c.setTime(p1.toDate());
            return new Variant(c.get(Calendar.HOUR_OF_DAY));
        }
    }


    /**
     * 返回两个日期相差的天数
     */
    @FunctionRegister(name = "DateDiff", category = "日期与时间", caption = "返回两个日期相差的天数 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "Start_date", caption = "开始日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    @ParamRegister(order = 2, name = "End_date", caption = "结束日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class DateDiff extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            java.util.Date date1 = p1.toDate();
            java.util.Date date2 = p2.toDate();
            return new Variant(DateUtils.subtract(date2, date1).getDays());
        }
    }


    /**
     * 返回一个日期中的天数
     */
    @FunctionRegister(name = "Day", category = "日期与时间", caption = "返回一个日期中的天数 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class Day extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(DateUtils.getDay(p1.toDate()));
        }
    }


    /**
     * 获取日期时间戳的毫秒数
     */
    @FunctionRegister(name = "timestamp", category = "日期与时间", caption = "获取日期时间戳的毫秒数(1970-1-1以来) 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "date", caption = "指定的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    public static class timestamp extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(p1.toDate().getTime());
        }
    }


    /**
     * 返回指定的日期
     */
    @FunctionRegister(name = "Date", category = "日期与时间", caption = "输入年月日，返回指定的日期。", minParamCount = 3)
    @ParamRegister(order = 1, name = "year", caption = "指定的年度。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 2, name = "month", caption = "指定的月份(1-12)。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 3, name = "day", caption = "指定的日(1-31)。", paramType = VariantType.INTEGER)
    public static class Date extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            Variant p3 = paramContext.getParam(3).getValue();
            java.util.Date date = DateUtils.parseDate(p1.getValue().toString() + "-" + p2.getValue().toString() + "-" + p3.getValue().toString());
            return new Variant(date);
        }
    }


    /**
     * 根据参数类型返回一个日期格式
     */
    @FunctionRegister(name = "DateFormat", category = "日期与时间", caption = "根据参数类型返回一个日期格式(Param参数值 0=自定义,1=yyyy-MM-dd,2=yyyy-MM-dd hh:mm:ss,3=hh:mm:ss,4=yyyy年MM月dd日,5=yyyy年MM月dd日 6=hh时mm分ss秒,7=MM-dd-yyyy,8=MM/dd/yyyy,9=MM-dd-yy,10=dd/MM/yy。", minParamCount = 2)
    @ParamRegister(order = 1, name = "date", caption = "需要转换的日期。", paramType = VariantType.DATETIME | VariantType.STRING)
    @ParamRegister(order = 2, name = "Param", caption = "输入0-10；0(自定义)、1(yyyy-MM-dd)、2(yyyy-MM-dd hh:mm:ss)、3(hh:mm:ss)、4(yyyy年MM月dd日)、5(yyyy年MM月dd日 hh时mm分ss秒)、6(hh时mm分ss秒)、7(MM-dd-yyyy)、8(MM/dd/yyyy)、9(MM-dd-yy)、10(MM/dd/yy)。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 3, name = "format", caption = "自定义时设置的格式：yyyy表示年、MM表示月、dd表示月、hh表示小时、mm表示分钟、ss表示秒钟。", paramType = VariantType.STRING, defaultValue = "")
    public static class DateFormat extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            Variant p3 = paramContext.getParam(3).getValue();
            if ("".equals(String.valueOf(p1).trim())) {
                return new Variant("");
            }
            java.util.Date date = p1.toDate();
            String strFormat;
            switch (p2.intValue()) {
                case 0:
                    if (p3.isNull()) {
                        throw new FunctionParamException(name, paramContext.getParam(2).getName(), "自定义时必须指定格式。");
                    }
                    strFormat = String.valueOf(p3);
                    break;
                case 2:
                    strFormat = "yyyy-MM-dd hh:mm:ss";
                    break;
                case 3:
                    strFormat = "hh:mm:ss";
                    break;
                case 4:
                    strFormat = "yyyy年MM月dd日";
                    break;
                case 5:
                    strFormat = "yyyy年MM月dd日 hh时mm分ss秒";
                    break;
                case 6:
                    strFormat = "hh时mm分ss秒";
                    break;
                case 7:
                    strFormat = "MM-dd-yyyy";
                    break;
                case 8:
                    strFormat = "MM/dd/yyyy";
                    break;
                case 9:
                    strFormat = "MM-dd-yy";
                    break;
                case 10:
                    strFormat = "dd/MM/yy";
                    break;
                default:
                    strFormat = "yyyy-MM-dd";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
            return new Variant(sdf.format(date));
        }
    }
}