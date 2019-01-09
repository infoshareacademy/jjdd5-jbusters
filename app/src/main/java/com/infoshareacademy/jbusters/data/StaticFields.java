package com.infoshareacademy.jbusters.data;


import com.infoshareacademy.jbusters.dao.AppPropertyDao;
import com.infoshareacademy.jbusters.model.AppProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@ApplicationScoped
public class StaticFields {

    private Map<String,String> featuresMap;
    private static final StaticFields staticFields = new StaticFields();
    private static final DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
    private static final DecimalFormat LONG_DF = new DecimalFormat("###,###.##");
    private static final DecimalFormat SHORT_DF = new DecimalFormat("##.##");
    private static final String LONG_DF1 = "longDFPattern";
    private static final URL APP_PROPERTIES_FILE = Thread.currentThread().getContextClassLoader().getResource("app.properties");
    private static final URL DISTR_PROPERTIES_FILE = Thread.currentThread().getContextClassLoader().getResource("districts.properties");
    private static final Path STATISTICS_FILE_PATH = Paths.get(System.getProperty("jboss.home.dir"), "data", "statistics.txt");
    private static final String REPORT_PATH_STRING = Paths.get(System.getProperty("jboss.server.temp.dir"), "report.pdf").toString();
    private static final URL BG_IMG_PATH = Thread.currentThread().getContextClassLoader().getResource(Paths.get("img", "JBusters_logo.png").toString());
    private static final Path LANG_PROPERTIES_FILE = Paths.get(System.getProperty("jboss.home.dir"), "data", "language.properties");

    @Inject
    private AppPropertyDao appPropertyDao;

    public StaticFields() {
        appPropertyDao.findAll().forEach(appProperty -> featuresMap.put(appProperty.getApName(),appProperty.getApValue()));
    }

    public String getTestConf(String name) {
        return featuresMap.get(name);
//        //DecimalFormat longDF = LONG_DF;
//        DecimalFormat longDF = new DecimalFormat(appPropertyDao.findByName(2).getApValue());
//        longDF.setDecimalFormatSymbols(getCustomizedSymbols());
//        longDF.setRoundingMode(RoundingMode.CEILING);
//        return longDF;
    }


    public StaticFields getStaticFields() {
        return staticFields;
    }

    public static URL getAppPropertiesURL() {
        return APP_PROPERTIES_FILE;
    }

    public static URL getDistrPropertiesURL() {
        return DISTR_PROPERTIES_FILE;
    }

    public static Path getStatisticsFilePath() {
        return STATISTICS_FILE_PATH;
    }

    public static String getReportPathString() {
        return REPORT_PATH_STRING;
    }

    public static URL getBgImgPath() {
        return BG_IMG_PATH;
    }

    public static Path getLangPropertiesPath() {
        return LANG_PROPERTIES_FILE;
    }

    public static DecimalFormat getLongDF() {
        DecimalFormat longDF = LONG_DF;
        DecimalFormat longDF = new DecimalFormat(get);
        longDF.setDecimalFormatSymbols(getCustomizedSymbols());
        longDF.setRoundingMode(RoundingMode.CEILING);
        return longDF;
    }

    public static String formatWithLongDF(Number num) {
        return getLongDF().format(num);
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
