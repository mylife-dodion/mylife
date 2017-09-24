package com.example.mylife.demo;

import com.example.mylife.stats.CsvIntFileStats;
import java.io.IOException;
import org.apache.log4j.Logger;

public class Demo {

  private static final Logger LOGGER = Logger.getLogger(Demo.class);

  /**
   * Main entry point for program to process csv file to demonstrate the CsvIntFileStats class.
   *
   * @param args path to the selected csv file to be processed
   */
  public static void main(String[] args) {
    System.out.println("Demo program is processing csv file " + args[0] + " ...");
    try {
      CsvIntFileStats stats = new CsvIntFileStats(args[0]);
      LOGGER.info("Stats for file " + args[0] + " are " + stats);
      System.out.println("Total number of integers = " + stats.getTotalNumberOfIntegers());
      System.out.println("Mean value of all integers (to three decimal places) = " + stats
          .getMeanValueOfAllIntegers());
      System.out.println("Highest number of integers in a single line = " + stats
          .getHighestNumberOfIntegersInASingleLine());
      System.out.println("Most common integer = " + stats.getMostCommonInteger());
    } catch (IOException e) {
      System.out.println("There was a problem processing the selected file [ " + args[0]
          + " ]. Full details are available in the log file.");
      LOGGER.error(e);
    }
  }

}
