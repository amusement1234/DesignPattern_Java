// 76 | 开源实战一（上）：通过剖析Java JDK源码学习灵活应用设计模式

// package java.util.Calendar;

// 工厂模式在 Calendar 类中的应用

// getInstance() 方法可以根据不同 TimeZone 和 Locale，创建不同的 Calendar 子类对象，比如 BuddhistCalendar、JapaneseImperialCalendar、GregorianCalendar，这些细节完全封装在工厂方法中，使用者只需要传递当前的时区和地址，就能够获得一个 Calendar 类对象来使用，而获得的对象具体是哪个 Calendar 子类的对象，使用者在使用的时候并不关心。
public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {
    //...
    public static Calendar getInstance(TimeZone zone, Locale aLocale) {
        return createCalendar(zone, aLocale);
    }

    private static Calendar createCalendar(TimeZone zone, Locale aLocale) {
        CalendarProvider provider = LocaleProviderAdapter.getAdapter(CalendarProvider.class, aLocale)
                .getCalendarProvider();
        if (provider != null) {
            try {
                return provider.getInstance(zone, aLocale);
            } catch (IllegalArgumentException iae) {
                // fall back to the default instantiation
            }
        }

        Calendar cal = null;
        if (aLocale.hasExtensions()) {
            String caltype = aLocale.getUnicodeLocaleType("ca");
            if (caltype != null) {
                switch (caltype) {
                    case "buddhist":
                        cal = new BuddhistCalendar(zone, aLocale);
                        break;
                    case "japanese":
                        cal = new JapaneseImperialCalendar(zone, aLocale);
                        break;
                    case "gregory":
                        cal = new GregorianCalendar(zone, aLocale);
                        break;
                }
            }
        }
        if (cal == null) {
            if (aLocale.getLanguage() == "th" && aLocale.getCountry() == "TH") {
                cal = new BuddhistCalendar(zone, aLocale);
            } else if (aLocale.getVariant() == "JP" && aLocale.getLanguage() == "ja" && aLocale.getCountry() == "JP") {
                cal = new JapaneseImperialCalendar(zone, aLocale);
            } else {
                cal = new GregorianCalendar(zone, aLocale);
            }
        }
        return cal;
    }
    //...
}