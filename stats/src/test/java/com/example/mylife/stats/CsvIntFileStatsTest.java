package com.example.mylife.stats;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.Test;

public class CsvIntFileStatsTest {

  private static final String PATH_TO_FILES = "target/test-classes/";

  /**
   * Attempting to process a non-existent csv file will mean that no new object is created.
   */
  @Test(expected = IOException.class)
  public void fileDoesNotExist() throws Exception {
    CsvIntFileStats csvIntFileStats = new CsvIntFileStats(PATH_TO_FILES + "missing-file.csv");
    assertTrue(csvIntFileStats == null);
  }

  /**
   * Processing a properly formatted csv file should create an object with correct stats values.
   */
  @Test
  public void nonEmptyFileExists() throws Exception {
    CsvIntFileStats csvIntFileStats = new CsvIntFileStats(PATH_TO_FILES + "normal.csv");
    assertTrue(csvIntFileStats != null);
    assertTrue(csvIntFileStats.getTotalNumberOfIntegers() == 26);
    assertTrue(csvIntFileStats.getMeanValueOfAllIntegers()
        .equals(new BigDecimal("81.962").setScale(3, RoundingMode.CEILING)));
    assertTrue(csvIntFileStats.getHighestNumberOfIntegersInASingleLine() == 12);
    assertTrue(csvIntFileStats.getMostCommonInteger().equals(new HashSet<>(Arrays.asList(8, 123))));
  }

  /**
   * Double quotes wrapping integers should be ignored so the stats should be identical to a csv
   * file with the same integers but without double quotes.
   */
  @Test
  public void nonEmptyFileWithDoubleQuotesExists() throws Exception {
    CsvIntFileStats csvIntFileStats = new CsvIntFileStats(
        PATH_TO_FILES + "normal-double-quotes.csv");
    assertTrue(csvIntFileStats != null);
    assertTrue(csvIntFileStats.getTotalNumberOfIntegers() == 26);
    assertTrue(csvIntFileStats.getMeanValueOfAllIntegers()
        .equals(new BigDecimal("81.962").setScale(3, RoundingMode.CEILING)));
    assertTrue(csvIntFileStats.getHighestNumberOfIntegersInASingleLine() == 12);
    assertTrue(csvIntFileStats.getMostCommonInteger().equals(new HashSet<>(Arrays.asList(8, 123))));
  }

  /**
   * Processing an empty file will result in a new object with default values and should avoid
   * attempting to 'divide by zero' when calculating the mean.
   */
  @Test
  public void emptyFileExists() throws Exception {
    CsvIntFileStats csvIntFileStats = new CsvIntFileStats(PATH_TO_FILES + "empty.csv");
    assertTrue(csvIntFileStats != null);
    assertTrue(csvIntFileStats.getTotalNumberOfIntegers() == 0);
    assertTrue(csvIntFileStats.getMeanValueOfAllIntegers().equals(BigDecimal.ZERO));
    assertTrue(csvIntFileStats.getHighestNumberOfIntegersInASingleLine() == 0);
    assertTrue(csvIntFileStats.getMostCommonInteger().equals(new HashSet<Integer>()));
  }

  /**
   * Processing a file with non-integer elements or otherwise corrupted should be detected and an
   * exception thrown. It is possible that some stats values will have been set so these will be
   * unreliable as indicated by the exception.
   */
  @Test(expected = IOException.class)
  public void corruptedFileExists() throws Exception {
    CsvIntFileStats csvIntFileStats = new CsvIntFileStats(PATH_TO_FILES + "corrupted.csv");
    assertTrue(csvIntFileStats != null);
  }
}