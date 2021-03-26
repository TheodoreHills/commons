package cn.vorbote.commons;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@SuppressWarnings("all")
public class MathUtil {

    /**
     * Calc the factorial of n
     *
     * @param n The number N should greater or equal to 0
     * @return The result of factorial n
     * @throws RuntimeException The n should greater or equal to 0
     */
    public static long Factorial(int n) {
        if (n < 0) throw new RuntimeException("A number lower than 0 has no factorial result");
        if (n <= 1) return 1L;
        else return n * Factorial(n - 1);
    }

    /**
     * Get the sum of the numbers
     *
     * @param numbers The numbers
     * @return The sum value of the numbers
     */
    public static double Sum(Collection<? extends Number> numbers) {
        double sum = 0;
        for (Number number : numbers) {
            sum += number.doubleValue();
        }
        return sum;
    }

    /**
     * Get the average value of the numbers
     *
     * @param numbers The numbers
     * @return The average value of the numbers
     */
    public static double Average(Collection<? extends Number> numbers) {
        double sum = Sum(numbers);
        log.debug("sum = {}, size = {}", sum, numbers.size());
        return sum / numbers.size();
    }

    /**
     * Check the num whether is between the noLess and noGreater number
     *
     * @param num       The number
     * @param noLess    The <code>num</code> should not less than this number.
     * @param noGreater The <code>num</code> should not bigger than this number.
     * @return
     */
    public static boolean IsBetween(Number num, Number noLess, Number noGreater) {
        return num.doubleValue() <= noGreater.doubleValue() &&
                num.doubleValue() >= noLess.doubleValue();
    }
}
