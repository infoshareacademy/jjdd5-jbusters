package com.infoshareacademy.jbusters.data;


import com.infoshareacademy.jbusters.dao.AppPropertyDao;
import com.infoshareacademy.jbusters.model.AppProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

@ApplicationScoped
public class StaticFields {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticFields.class);
    private Map<String,String> featuresMap = new HashMap<>();
    private  final DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
    private  final DecimalFormat LONG_DF = new DecimalFormat("###,###.##");
    private final DecimalFormat SHORT_DF = new DecimalFormat("##.##");
    private  final String LONG_DF1 = "longDFPattern";
    private  final URL APP_PROPERTIES_FILE = Thread.currentThread().getContextClassLoader().getResource("app.properties");
    private  final URL DISTR_PROPERTIES_FILE = Thread.currentThread().getContextClassLoader().getResource("districts.properties");
    private  final Path STATISTICS_FILE_PATH = Paths.get(System.getProperty("jboss.home.dir"), "data", "statistics.txt");
    private  final String REPORT_PATH_STRING = Paths.get(System.getProperty("jboss.server.temp.dir"), "report.pdf").toString();
    private  final URL BG_IMG_PATH = Thread.currentThread().getContextClassLoader().getResource(Paths.get("img", "JBusters_logo.png").toString());
    private  final Path LANG_PROPERTIES_FILE = Paths.get(System.getProperty("jboss.home.dir"), "data", "language.properties");

    @Inject
    private AppPropertyDao appPropertyDao;

    public StaticFields() {
        LOGGER.info("StaticFields constructor initialized");
    }

    @PostConstruct
    public void init() {
        List<AppProperty> appPropertyList = appPropertyDao.findAll();;
        LOGGER.info("AppPropertyList created");

        LOGGER.info("AppPropertyList filled with {} values",appPropertyList.size());
        appPropertyDao.findAll().forEach(appProperty -> featuresMap.put(appProperty.getApName(),appProperty.getApValue()));

        LOGGER.info("number of properties: "+featuresMap.size());

    }

    public String getTestConf(String name) {
        return featuresMap.get(name);
//        //DecimalFormat longDF = LONG_DF;
//        DecimalFormat longDF = new DecimalFormat(appPropertyDao.findByName(2).getApValue());
//        longDF.setDecimalFormatSymbols(getCustomizedSymbols());
//        longDF.setRoundingMode(RoundingMode.CEILING);
//        return longDF;
    }


    //public StaticFields getStaticFields() {
    //    return staticFields;
    //}

    public  URL getAppPropertiesURL() {
        return APP_PROPERTIES_FILE;
    }

    public  URL getDistrPropertiesURL() {
        return DISTR_PROPERTIES_FILE;
    }

    public  Path getStatisticsFilePath() {
        return STATISTICS_FILE_PATH;
    }

    public  String getReportPathString() {
        return REPORT_PATH_STRING;
    }

    public  URL getBgImgPath() {
        return BG_IMG_PATH;
    }

    public  Path getLangPropertiesPath() {
        return LANG_PROPERTIES_FILE;
    }

    public DecimalFormat getLongDF() {
        //DecimalFormat longDF = LONG_DF;
        DecimalFormat longDF = new DecimalFormat(getTestConf(LONG_DF1));
        longDF.setDecimalFormatSymbols(getCustomizedSymbols());
        longDF.setRoundingMode(RoundingMode.CEILING);
        return longDF;
    }

    public String formatWithLongDF(Number num) {
        return getLongDF().format(num);
    }

    public  DecimalFormat getShortDF() {
        SHORT_DF.setDecimalFormatSymbols(getCustomizedSymbols());
        SHORT_DF.setRoundingMode(RoundingMode.CEILING);
        return SHORT_DF;
    }

    public  String formatWithShortDF(Number num) {
        SHORT_DF.setDecimalFormatSymbols(getCustomizedSymbols());
        SHORT_DF.setRoundingMode(RoundingMode.CEILING);
        return SHORT_DF.format(num);
    }

    private DecimalFormatSymbols getCustomizedSymbols() {
        decimalSymbols.setDecimalSeparator('.');
        decimalSymbols.setGroupingSeparator(' ');
        return decimalSymbols;
    }
}
