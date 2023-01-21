package ir.pod.podiotmockdevice;


import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ali Mojahed on 6/3/2021
 * @project spring-template
 **/

@Log4j2
public class Util {

    public static boolean isNullOrZero(Number num) {
        return num == null || num.intValue() == 0;
    }

    public static void tryMultipleTimes(int n, Supplier function) throws Exception {
        boolean succeed = false;
        Exception e = null;
        for (int i = 0; i < n; i++) {
            try {
                function.get();
                succeed = true;
                break;
            } catch (Exception exception) {
                e = exception;
            }
        }

        if (!succeed) {
            throw e;
        }
    }

    @SafeVarargs
    public static <T> List<T> combineMyLists(List<? extends T>... lists) {

        return Stream
                .of(lists)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty() || input.trim().isEmpty();
    }

    public static <T> boolean isNullOrEmpty(Collection<T> input) {
        return input == null || input.isEmpty();
    }

    public static <K, T> boolean isNullOrEmpty(Map<K, T> input) {
        return input == null || input.isEmpty();
    }

    public static <T> boolean isNullOrEmpty(T[] input) {
        return input == null || input.length <= 0;
    }


    public static boolean isPositive(int number) {
        return number >= 0;
    }

    public static boolean isPositive(long number) {
        return number >= 0;
    }


    public static int getOffset(int page, int size) {
        return (page - 1) * size;
    }

    public static void checkPagination(long size, long offset) throws ProjectException {
        if (!Util.isPositive(offset) || !Util.isPositive(size)) {
            throw new ProjectException(ExceptionStatus.INVALID_PAGINATION_PARAM);
        }
    }

    public static <E extends Enum<E>> boolean isValidParam(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Annotation, E> List<String> getAnnotatedFields(Class<E> t, Class<T> annotation) {
        return Arrays.stream(t.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotation))
                .map(Field::getName).collect(Collectors.toList());
    }

    public static boolean phoneIsValid(String mobileNumber) {
        String regex1 = "^(\\+98|0|0098|98)?9\\d{9}$";

        return mobileNumber.matches(regex1);
    }
//    public static UserVO getUserFromContext() {
//        Object userVO =
//        log.info(userVO);
//        return (UserVO) userVO;
//    }

//    public static long getDashboardTimeFormat(LocalDate localDate) {
//
//        JalaliCalendar.YearMonthDate yearMonthDay = new JalaliCalendar.YearMonthDate(localDate.getYear(),
//                localDate.getMonthValue(),
//                localDate.getDayOfMonth());
//
//        JalaliCalendar.YearMonthDate jalaliDate = JalaliCalendar.gregorianToJalali(yearMonthDay);
//        String year = String.valueOf(jalaliDate.getYear());
//        String month = jalaliDate.getMonth() < 10 ? "0" + jalaliDate.getMonth() : String.valueOf(jalaliDate.getMonth());
//        String day = jalaliDate.getDate() < 10 ? "0" + jalaliDate.getDate() : String.valueOf(jalaliDate.getDate());
//
//        return Long.parseLong(year + month + day);
//    }

    public static String getJalaliDateString(LocalDateTime localDateTime) {
        DateConverter dateConverter = new DateConverter();
        JalaliDate jalaliDate = dateConverter.gregorianToJalali(localDateTime.getYear(),
                localDateTime.getMonth(),
                localDateTime.getDayOfMonth());

        String year = String.valueOf(jalaliDate.getYear());

        String month = jalaliDate.getMonthPersian().getValue() < 10 ?
                "0" + jalaliDate.getMonthPersian().getValue() : String.valueOf(jalaliDate.getMonthPersian().getValue());

        String day = jalaliDate.getDay() < 10 ?
                "0" + jalaliDate.getDay() : String.valueOf(jalaliDate.getDay());

        return year + "/" + month + "/" + day;
    }

//    public static long getTimeStampFromJalaliDateTime(String jalaliDate, String timeStr) {
//
//        DateConverter dateConverter = new DateConverter();
//
//        String[] dateTokens = jalaliDate.split("/");
//        String[] timeTokens = timeStr.split(":");
//
//        int[] dateParts = new int[]{
//                Integer.parseInt(dateTokens[0]),
//                Integer.parseInt(dateTokens[1]),
//                Integer.parseInt(dateTokens[2])
//        };
//
//        int[] timeParts = new int[]{
//                Integer.parseInt(timeTokens[0]),
//                Integer.parseInt(timeTokens[1]),
//                Integer.parseInt(timeTokens[2])
//        };
//
//        LocalDate date = dateConverter.jalaliToGregorian(dateParts[0], dateParts[1], dateParts[2]);
//        LocalTime time = LocalTime.of(timeParts[0], timeParts[1], timeParts[2]);
//        LocalDateTime dateTime = LocalDateTime.of(date, time);
//
//        return SimpleDateUtil.getTimestamp(dateTime);
//    }


//    public static <T> Stream<T> sortStream(Stream<T> stream, String sortCriteria) {
//        stream.sorted(c)
//    }
//
//
//    public static <T>Comparator<T> getDynamicComparator(String field){
//        return new Comparator<T>() {
//            @SneakyThrows
//            @Override
//            public int compare(T o1, T o2) {
//                try {
//                    o1.getClass().getMethod("get" + StringUtils.capitalize(field));
//
//                } catch (NoSuchMethodException e) {
//                    throw new ProjectException(ExceptionStatus.INVALID_INPUT);
//                }
//            }
//        }
//    }

}
