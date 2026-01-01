package com.automation.utils;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Test Data Generator - Generates random test data using JavaFaker
 */
public class DataGenerator {

    private static final Logger log = LoggerFactory.getLogger(DataGenerator.class);

    private static final Faker faker = new Faker(new Locale("en-US"));

    private DataGenerator() {
        // Private constructor
    }

    // Name generators
    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getFullName() {
        return faker.name().fullName();
    }

    public static String getUsername() {
        return faker.name().username();
    }

    // Contact generators
    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getEmail(String domain) {
        return faker.name().username() + "@" + domain;
    }

    public static String getPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    // Address generators
    public static String getStreetAddress() {
        return faker.address().streetAddress();
    }

    public static String getCity() {
        return faker.address().city();
    }

    public static String getState() {
        return faker.address().state();
    }

    public static String getZipCode() {
        return faker.address().zipCode();
    }

    public static String getCountry() {
        return faker.address().country();
    }

    public static String getFullAddress() {
        return faker.address().fullAddress();
    }

    // Text generators
    public static String getParagraph() {
        return faker.lorem().paragraph();
    }

    public static String getSentence() {
        return faker.lorem().sentence();
    }

    public static String getWord() {
        return faker.lorem().word();
    }

    public static String getText(int wordCount) {
        return faker.lorem().words(wordCount).toString();
    }

    // Number generators
    public static int getRandomNumber(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static long getRandomLong(long min, long max) {
        return faker.number().numberBetween(min, max);
    }

    public static double getRandomDouble(int maxNumberOfDecimals, int min, int max) {
        return faker.number().randomDouble(maxNumberOfDecimals, min, max);
    }

    // Password generator
    public static String getPassword() {
        return faker.internet().password(8, 16, true, true, true);
    }

    public static String getPassword(int minLength, int maxLength) {
        return faker.internet().password(minLength, maxLength, true, true, true);
    }

    // Company generators
    public static String getCompanyName() {
        return faker.company().name();
    }

    public static String getJobTitle() {
        return faker.job().title();
    }

    // Date generators
    public static Date getPastDate(int daysBack) {
        return faker.date().past(daysBack, TimeUnit.DAYS);
    }

    public static Date getFutureDate(int daysAhead) {
        return faker.date().future(daysAhead, TimeUnit.DAYS);
    }

    public static String getFormattedDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(faker.date().birthday());
    }

    public static String getBirthDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(faker.date().birthday(18, 65));
    }

    // UUID generator
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    // Credit card generators
    public static String getCreditCardNumber() {
        return faker.finance().creditCard();
    }

    public static String getCreditCardExpiry() {
        return String.format("%02d/%02d",
                getRandomNumber(1, 12),
                getRandomNumber(24, 30));
    }

    public static String getCVV() {
        return String.valueOf(getRandomNumber(100, 999));
    }

    // URL generators
    public static String getUrl() {
        return faker.internet().url();
    }

    public static String getImageUrl() {
        return faker.internet().image();
    }

    // Color generator
    public static String getColorHex() {
        return faker.color().hex();
    }

    // File generators
    public static String getFileName() {
        return faker.file().fileName();
    }

    public static String getFileExtension() {
        return faker.file().extension();
    }

    // Custom data
    public static String getCustom(String pattern) {
        return faker.regexify(pattern);
    }
}
