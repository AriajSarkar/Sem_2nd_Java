public class EnhancedPerformanceTest {
    private static final int WARMUP_RUNS = 1000;
    private static final int MEASUREMENT_RUNS = 10000;

    public static void main(String[] args) {
        // Test cases with different ranges
        int[][] testCases = {
            {1, 100},           // Small range
            {1, 10000},         // Medium range
            {1, 1000000},       // Large range
            {1000000, 2000000}  // High numbers range
        };

        // Warmup phase to allow JIT optimization
        System.out.println("Warming up JVM...");
        warmup(testCases[0][0], testCases[0][1]);
        
        for (int[] testCase : testCases) {
            int lower = testCase[0];
            int upper = testCase[1];
            
            System.out.println("\nTesting range: " + lower + " to " + upper);
            System.out.println("----------------------------------------");

            // Measure with multiple runs
            BenchmarkResult efficientResult = benchmarkMethod(lower, upper, true);
            BenchmarkResult bruteForceResult = benchmarkMethod(lower, upper, false);

            // Print detailed results
            System.out.printf("Efficient method:%n");
            System.out.printf("  Average time: %.6f ms%n", efficientResult.averageTime);
            System.out.printf("  Min time: %.6f ms%n", efficientResult.minTime);
            System.out.printf("  Max time: %.6f ms%n", efficientResult.maxTime);
            System.out.printf("  Standard deviation: %.6f ms%n", efficientResult.standardDeviation);
            System.out.printf("  Results: count=%d, sum=%d%n", 
                efficientResult.computationResult.count,
                efficientResult.computationResult.sum);

            System.out.printf("%nBrute force method:%n");
            System.out.printf("  Average time: %.6f ms%n", bruteForceResult.averageTime);
            System.out.printf("  Min time: %.6f ms%n", bruteForceResult.minTime);
            System.out.printf("  Max time: %.6f ms%n", bruteForceResult.maxTime);
            System.out.printf("  Standard deviation: %.6f ms%n", bruteForceResult.standardDeviation);
            System.out.printf("  Results: count=%d, sum=%d%n", 
                bruteForceResult.computationResult.count,
                bruteForceResult.computationResult.sum);

            System.out.printf("%nSpeed improvement: %.2fx%n", 
                bruteForceResult.averageTime / efficientResult.averageTime);
        }
    }

    private static void warmup(int lower, int upper) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            efficientMethod(lower, upper);
            bruteForceMethod(lower, upper);
        }
    }

    private static BenchmarkResult benchmarkMethod(int lower, int upper, boolean efficient) {
        double[] times = new double[MEASUREMENT_RUNS];
        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        Result firstResult = null;

        for (int i = 0; i < MEASUREMENT_RUNS; i++) {
            long startTime = System.nanoTime();
            Result result;
            if (efficient) {
                result = efficientMethod(lower, upper);
            } else {
                result = bruteForceMethod(lower, upper);
            }
            double time = (System.nanoTime() - startTime) / 1e6; // Convert to ms
            
            // Verify results consistency across runs
            if (firstResult == null) {
                firstResult = result;
            } else if (!firstResult.equals(result)) {
                throw new IllegalStateException("Inconsistent results detected in " + 
                    (efficient ? "efficient" : "brute force") + " method!");
            }

            times[i] = time;
            sum += time;
            min = Math.min(min, time);
            max = Math.max(max, time);
        }

        double avg = sum / MEASUREMENT_RUNS;
        double variance = 0;
        for (double time : times) {
            variance += Math.pow(time - avg, 2);
        }
        double stdDev = Math.sqrt(variance / MEASUREMENT_RUNS);

        return new BenchmarkResult(avg, min, max, stdDev, firstResult);
    }

    private static Result efficientMethod(int lower, int upper) {
        int firstDivisible = lower + (7 - (lower % 7)) % 7;
        int lastDivisible = upper - (upper % 7);
        int count = (lastDivisible - firstDivisible) / 7 + 1;
        long sum = count > 0 ? (long) count * (firstDivisible + lastDivisible) / 2 : 0;
        return new Result(count, sum);
    }

    private static Result bruteForceMethod(int lower, int upper) {
        int count = 0;
        long sum = 0;
        for (int i = lower; i <= upper; i++) {
            if (i % 7 == 0) {
                count++;
                sum += i;
            }
        }
        return new Result(count, sum);
    }

    private static class Result {
        final int count;
        final long sum;

        Result(int count, long sum) {
            this.count = count;
            this.sum = sum;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Result)) return false;
            Result other = (Result) obj;
            return this.count == other.count && this.sum == other.sum;
        }

        @Override
        public int hashCode() {
            return 31 * Integer.hashCode(count) + Long.hashCode(sum);
        }

        @Override
        public String toString() {
            return String.format("(count=%d, sum=%d)", count, sum);
        }
    }

    private static class BenchmarkResult {
        final double averageTime;
        final double minTime;
        final double maxTime;
        final double standardDeviation;
        final Result computationResult;

        BenchmarkResult(double avg, double min, double max, double stdDev, Result result) {
            this.averageTime = avg;
            this.minTime = min;
            this.maxTime = max;
            this.standardDeviation = stdDev;
            this.computationResult = result;
        }
    }
}
