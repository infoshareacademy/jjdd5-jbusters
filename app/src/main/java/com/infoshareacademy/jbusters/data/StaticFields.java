package com.infoshareacademy.jbusters.data;

import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class StaticFields {

    private static final StaticFields staticFields = new StaticFields();
    private static final DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
    private static final DecimalFormat LONG_DF = new DecimalFormat("###,###.##");
    private static final DecimalFormat SHORT_DF = new DecimalFormat("##.##");
    private static final String JBOSS_HOME_DIR = "jboss.home.dir";
    private static final String JBOSS_TEMP_DIR = "jboss.server.temp.dir";

    private static final URL APP_PROPERTIES_FILE = Thread.currentThread().getContextClassLoader().getResource("app.properties");
    private static final URL DISTR_PROPERTIES_FILE = Thread.currentThread().getContextClassLoader().getResource("districts.properties");
    private static final Path STATISTICS_FILE_PATH = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "statistics.txt");
    private static final String REPORT_PATH_STRING = Paths.get(System.getProperty(JBOSS_TEMP_DIR), "report.pdf").toString();
    private static final URL BG_IMG_PATH = Thread.currentThread().getContextClassLoader().getResource(Paths.get("img", "JBusters_logo.png").toString());
    private static final Path LANG_PROPERTIES_FILE = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "language.properties");
    private static final Path SCHEDULER_PROPERTIES_FILE = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "scheduler.properties");
    private static final Path EXCHANGE_RATES_PROPERTIES_FILE = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "exchange_rates.properties");
    private static final Path EXCHANGE_RATES_SELECTION_FILE = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "exchange_rates_selection.properties");
    private static final String EXCHANGE_RATES_URL = "http://bossa.pl/pub/waluty/omega/nbp/ndohlcv.txt";

    private StaticFields() {
    }

    public StaticFields getStaticFields() {
        return staticFields;
    }

    public static URL getAppPropertiesURL() { return APP_PROPERTIES_FILE; }

    public static URL getDistrPropertiesURL() {
        return DISTR_PROPERTIES_FILE;
    }

    public static Path getStatisticsFilePath() { return STATISTICS_FILE_PATH; }

    public static String getReportPathString() { return REPORT_PATH_STRING; }

    public static URL getBgImgPath() { return BG_IMG_PATH; }

    public static Path getLangPropertiesPath() { return LANG_PROPERTIES_FILE; }

    public static Path getSchedulerPropertiesFile() { return SCHEDULER_PROPERTIES_FILE; }

    public static Path getExchangeRatesPropertiesFile() { return EXCHANGE_RATES_PROPERTIES_FILE; }

    public static Path getExchangeRatesSelectionFile() { return EXCHANGE_RATES_SELECTION_FILE; }

    public static String getExchangeRatesUrl() { return EXCHANGE_RATES_URL; }

    public static DecimalFormat getLongDF() {
        LONG_DF.setDecimalFormatSymbols(getCustomizedSymbols());
        LONG_DF.setRoundingMode(RoundingMode.CEILING);
        return LONG_DF;
    }

    public static String formatWithLongDF(Number num) {
        LONG_DF.setDecimalFormatSymbols(getCustomizedSymbols());
        LONG_DF.setRoundingMode(RoundingMode.CEILING);
        return SHORT_DF.format(num);
    }

    public static DecimalFormat getShortDF() {
        SHORT_DF.setDecimalFormatSymbols(getCustomizedSymbols());
        SHORT_DF.setRoundingMode(RoundingMode.CEILING);
        return SHORT_DF;
    }

    public static String formatWithShortDF(Number num) {
        SHORT_DF.setDecimalFormatSymbols(getCustomizedSymbols());
        SHORT_DF.setRoundingMode(RoundingMode.CEILING);
        return SHORT_DF.format(num);
    }

    private static DecimalFormatSymbols getCustomizedSymbols() {
        decimalSymbols.setDecimalSeparator('.');
        decimalSymbols.setGroupingSeparator(' ');
        return decimalSymbols;
    }


}
