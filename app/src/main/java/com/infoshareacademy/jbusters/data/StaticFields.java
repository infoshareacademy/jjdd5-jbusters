package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.dao.ConfigurationDao;
import com.infoshareacademy.jbusters.model.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
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
    private static final String JBOSS_HOME_DIR = "jboss.home.dir";
    private static final String JBOSS_TEMP_DIR = "jboss.server.temp.dir";
    private final Path STATISTICS_FILE_PATH = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "statistics.txt");
    private final String REPORT_PATH_STRING = Paths.get(System.getProperty(JBOSS_TEMP_DIR), "report.pdf").toString();
    private final URL BG_IMG_PATH = Thread.currentThread().getContextClassLoader().getResource(Paths.get("img", "JBusters_logo.png").toString());
    private Configuration config;
    private static final Path LANG_PROPERTIES_FILE = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "language.properties");
    private static final Path SCHEDULER_PROPERTIES_FILE = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "scheduler.properties");
    private static final Path EXCHANGE_RATES_PROPERTIES_FILE = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "exchange_rates.properties");
    private static final Path EXCHANGE_RATES_SELECTION_FILE = Paths.get(System.getProperty(JBOSS_HOME_DIR), "data", "exchange_rates_selection.properties");
    private static final String EXCHANGE_RATES_URL = "http://bossa.pl/pub/waluty/omega/nbp/ndohlcv.txt";

    @Inject
    private ConfigurationDao configurationDao;

    public StaticFields() {
        LOGGER.info("StaticFields constructor initialized");
    }

    @PostConstruct
    public void init() {
        int id = 1;
        config = configurationDao.findById(id);
    }

    public Path getStatisticsFilePath() {
        return STATISTICS_FILE_PATH;
    }

    public String getReportPathString() {
        return REPORT_PATH_STRING;
    }

    public URL getBgImgPath() {
        return BG_IMG_PATH;
    }

    public Path getLangPropertiesPath() {
        return LANG_PROPERTIES_FILE;
    }

    public DecimalFormat getLongDF() {
        DecimalFormat longDF = new DecimalFormat(config.getLongDFPattern());
        longDF.setDecimalFormatSymbols(getCustomizedSymbols());
        longDF.setRoundingMode(RoundingMode.CEILING);
        return longDF;
    }

    public static Path getSchedulerPropertiesFile() {
        return SCHEDULER_PROPERTIES_FILE;
    }

    public static Path getExchangeRatesPropertiesFile() {
        return EXCHANGE_RATES_PROPERTIES_FILE;
    }

    public static Path getExchangeRatesSelectionFile() {
        return EXCHANGE_RATES_SELECTION_FILE;
    }

    public static String getExchangeRatesUrl() {
        return EXCHANGE_RATES_URL;
    }


    public String formatWithLongDF(Number num) {
        return getLongDF().format(num);
    }

    public DecimalFormat getShortDF() {
        DecimalFormat shortDF = new DecimalFormat(config.getShortDFPattern());
        shortDF.setDecimalFormatSymbols(getCustomizedSymbols());
        shortDF.setRoundingMode(RoundingMode.CEILING);
        return shortDF;
    }

    public String formatWithShortDF(Number num) {
        return getShortDF().format(num);
    }

    private DecimalFormatSymbols getCustomizedSymbols() {
        DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        decimalSymbols.setDecimalSeparator(config.getDecimalSep().charAt(0));
        decimalSymbols.setGroupingSeparator(config.getGroupingSep().charAt(0));
        return decimalSymbols;
    }

    public int getMinResultsReq() {
        return config.getMinResultsReq();
    }

    public BigDecimal getExchangeRate() {
        return config.getExchangeRate();
    }

    public String getCurrency() {
        return config.getCurrency();
    }

    public BigDecimal getAreaDiff() {
        return config.getAreaDiff();
    }

    public BigDecimal getAreaDiffExpanded() {
        return config.getAreaDiffExpanded();
    }

    public int getDecimalPlaces() {
        return config.getDecimalPlaces();
    }

    public BigDecimal getPriceDiff() {
        return config.getPriceDiff();
    }
}
