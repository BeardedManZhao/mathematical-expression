package utils;

import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

import java.util.Arrays;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        CalculationNumberResults[] calculationBooleanResults = new CalculationNumberResults[]{
                new CalculationNumberResults(null, 1024, "a"),
                new CalculationNumberResults(null, 1024, "a"),
                new CalculationNumberResults(null, 10240, "a"),
                new CalculationNumberResults(null, 2048, "a"),
                new CalculationNumberResults(null, 1024, "a")
        };
        Arrays.sort(calculationBooleanResults);
        System.out.println(Arrays.toString(calculationBooleanResults));
    }
}