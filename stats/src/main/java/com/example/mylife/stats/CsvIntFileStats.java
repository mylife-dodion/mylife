
package com.example.mylife.stats;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Immutable object to hold statistical information about the integers in a csv format text file.
 *
 * <p>The following information is available after successful file processing: <ul> <li>Total number
 * of integers</li> <li>Mean value of all integers</li> <li>Highest number of integers in a single
 * line</li> <li>Most common integer</li> </ul>
 *
 * <p>It is possible for several integers to be 'most common' i.e. if they all have the same highest
 * frequency.
 *
 * <p>If the file is empty i.e. has no integers, then it is not possible to calculate a mean because
 * this would involve division by zero. In such cases the value of zero is returned for the mean and
 * the collection of most common integers would be empty too.
 */
public class CsvIntFileStats {

  private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger
      .getLogger(CsvIntFileStats.class);

  private int totalNumberOfIntegers;
  private BigDecimal meanValueOfAllIntegers;
  private int highestNumberOfIntegersInASingleLine;
  private final Set<Integer> mostCommonInteger;

  /**
   * Sole constructor to create object that contains statistical information about integers in a csv
   * text file.
   *
   * @param inputFile (required) path to csv text file.
   * @throws IOException if the file cannot be found/read etc.
   */
  public CsvIntFileStats(final String inputFile) throws IOException {

    LOGGER.trace(inputFile + " Entering constructor...");

    this.totalNumberOfIntegers = 0;
    this.meanValueOfAllIntegers = BigDecimal.ZERO;
    this.highestNumberOfIntegersInASingleLine = 0;
    this.mostCommonInteger = new HashSet<>();

    final Map<Integer, Integer> integerMap = new HashMap<>();
    int sumOfIntegers = 0;
    final Scanner fileScanner = new Scanner(Paths.get(inputFile));
    Scanner lineScanner;

    while (fileScanner.hasNextLine()) {
      lineScanner = new Scanner(fileScanner.nextLine().replace("\"", ""));
      lineScanner.useDelimiter(",");
      // Reset counter
      int itemsOnLine = 0;
      while (lineScanner.hasNextInt()) {
        Integer currentInteger = lineScanner.nextInt();
        // Update counters
        itemsOnLine = itemsOnLine + 1;
        sumOfIntegers = sumOfIntegers + currentInteger;
        this.totalNumberOfIntegers = this.totalNumberOfIntegers + 1;
        // Update map to track frequency of integer
        integerMap.merge(currentInteger, 1, (a, b) -> a + b);
      }
      // Sanity check because if all went well there should be no items left to process. However,
      // if items are left then they must be other than 'int' and this suggests a corrupted file.
      if (lineScanner.hasNext()) {
        LOGGER.error(
            inputFile + " Something appears to be wrong with the input file. Throwing IOException");
        throw new IOException();
      }
      // Update the highest number of integers on a line if required
      if (itemsOnLine > this.highestNumberOfIntegersInASingleLine) {
        this.highestNumberOfIntegersInASingleLine = itemsOnLine;
      }
      lineScanner.close();
    }
    fileScanner.close();

    // calculate mean only if there was at least one integer
    if (this.totalNumberOfIntegers > 0) {
      this.meanValueOfAllIntegers = new BigDecimal(
          (double) sumOfIntegers / this.totalNumberOfIntegers).setScale(3, RoundingMode.CEILING);
    }

    // Iterate over map to confirm highest frequency used to calculate most common integer(s)
    LOGGER.trace(inputFile + " Integer frequency map =" + " " + integerMap);
    Integer maxFrequency = 0;
    for (Map.Entry<Integer, Integer> entry : integerMap.entrySet()) {
      if (entry.getValue() > maxFrequency) {
        maxFrequency = entry.getValue();
      }
    }
    // Iterate over map again to extract entries that have the newly confirmed highest frequency
    for (Map.Entry<Integer, Integer> entry : integerMap.entrySet()) {
      if (entry.getValue().equals(maxFrequency)) {
        this.mostCommonInteger.add(entry.getKey());
      }
    }

    LOGGER.debug(inputFile + " " + this);

  }

  /**
   * Identifies how many integers were present in the file.
   *
   * @return A count of the integers in the file.
   */
  public int getTotalNumberOfIntegers() {
    return totalNumberOfIntegers;
  }

  /**
   * Identifies the mean (sum divided by the count of all integers) to three decimal places.
   *
   * @return Calculated mean. Will be zero if there are no integers in the file.
   */
  public BigDecimal getMeanValueOfAllIntegers() {
    return meanValueOfAllIntegers;
  }

  /**
   * Identifies the highest number of integers found in any one of the lines in the file.
   *
   * @return Greatest number of integers present on a single line.
   */
  public int getHighestNumberOfIntegersInASingleLine() {
    return highestNumberOfIntegersInASingleLine;
  }

  /**
   * Identifies those integers that appeared most frequently in the file.
   *
   * @return Collection containing most common integer(s). Could be empty if file was empty.
   */
  public Set<Integer> getMostCommonInteger() {
    return mostCommonInteger;
  }

  /**
   * Concise human readable representation of the object.
   *
   * @return Statistical information in the format <code>CsvIntFileStats{totalNumberOfIntegers=26,
   * meanValueOfAllIntegers=81.962, highestNumberOfIntegersInASingleLine=12, mostCommonInteger=[8,
   * 123]}</code>
   */
  @Override
  public String toString() {
    return "CsvIntFileStats{"
        + "totalNumberOfIntegers=" + totalNumberOfIntegers
        + ", meanValueOfAllIntegers=" + meanValueOfAllIntegers
        + ", highestNumberOfIntegersInASingleLine=" + highestNumberOfIntegersInASingleLine
        + ", mostCommonInteger=" + mostCommonInteger
        + '}';
  }

}
