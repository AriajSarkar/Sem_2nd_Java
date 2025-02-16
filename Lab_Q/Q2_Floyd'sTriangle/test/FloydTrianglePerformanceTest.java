import java.util.concurrent.TimeUnit;

public class FloydTrianglePerformanceTest {
    private static final int WARM_UP_ITERATIONS = 10_000;
    private static final int TEST_ITERATIONS = 100_000;
    private static final int[] TEST_SIZES = {
        1 << 3,  // 8 rows
        1 << 6,  // 64 rows
        1 << 8,  // 256 rows
        1 << 10  // 1024 rows
    };

    public static void main(String[] args) {
        // Warm up JVM and trigger JIT compilation
        warmUp();
        
        // Run performance tests for each size
        for (int size : TEST_SIZES) {
            testPatternPerformance(size);
        }
    }

    private static void warmUp() {
        System.out.println("Warming up JVM...");
        long start = System.nanoTime();
        
        for (int i = 0; i < WARM_UP_ITERATIONS; i++) {
            // Test all methods with small input
            optimizedFloydTriangle(10, false, false);
            optimizedFloydTriangle(10, true, false);
            optimizedFloydTriangle(10, false, true);
            optimizedCenteredTriangle(10);
        }
        
        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.printf("Warm-up completed in %d ms%n%n", duration);
    }

    private static void testPatternPerformance(int rows) {
        System.out.printf("Testing with %d rows%n", rows);
        System.out.println("----------------------------------------");

        // Test each pattern type
        long[][] times = new long[4][TEST_ITERATIONS];
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            times[0][i] = measurePattern(() -> optimizedFloydTriangle(rows, false, false));
            times[1][i] = measurePattern(() -> optimizedFloydTriangle(rows, true, false));
            times[2][i] = measurePattern(() -> optimizedFloydTriangle(rows, false, true));
            times[3][i] = measurePattern(() -> optimizedCenteredTriangle(rows));
        }

        // Print results
        printStatistics("Regular Floyd", times[0]);
        printStatistics("Mirror Floyd", times[1]);
        printStatistics("Reverse Floyd", times[2]);
        printStatistics("Centered Floyd", times[3]);
        System.out.println();
    }

    private static StringBuilder optimizedFloydTriangle(int rows, boolean mirror, boolean reverse) {
        // Binary optimization for triangle size calculation
        int maxNumber = (rows * (rows + 1)) >>> 1; // Divide by 2 using shift
        int maxWidth = Integer.SIZE - Integer.numberOfLeadingZeros(maxNumber);
        int number = reverse ? maxNumber : 1;
        
        // Pre-calculate buffer size to avoid resizing
        int bufferSize = (((maxWidth + 1) * (rows + 1) * rows) >>> 1) + rows;
        StringBuilder sb = new StringBuilder(bufferSize);
        
        int rowWidth = maxWidth + 1;
        char[] spaces = new char[rows * rowWidth]; // Changed from byte[] to char[]
        java.util.Arrays.fill(spaces, ' ');
        
        for (int i = 1; i <= rows; i++) {
            if (mirror) {
                sb.append(spaces, 0, (rows - i) * rowWidth);
            }
            for (int j = 1; j <= i; j++) {
                sb.append(String.format("%" + maxWidth + "d ", number));
                number = reverse ? number - 1 : number + 1;
            }
            sb.append('\n');
        }
        return sb;
    }

    private static StringBuilder optimizedCenteredTriangle(int rows) {
        int maxNumber = (rows * (rows + 1)) >>> 1;
        int maxWidth = Integer.SIZE - Integer.numberOfLeadingZeros(maxNumber);
        int number = 1;
        
        int bufferSize = (((maxWidth + 1) * (rows + 1) * rows) >>> 1) + rows;
        StringBuilder sb = new StringBuilder(bufferSize);
        
        int totalWidth = rows * (maxWidth + 1) - 1;
        char[] spaces = new char[totalWidth]; // Changed from byte[] to char[]
        java.util.Arrays.fill(spaces, ' ');
        
        for (int i = 1; i <= rows; i++) {
            int rowWidth = i * (maxWidth + 1) - 1;
            int padding = (totalWidth - rowWidth) >>> 1;
            sb.append(spaces, 0, padding);
            
            for (int j = 1; j <= i; j++) {
                sb.append(String.format("%" + maxWidth + "d ", number++));
            }
            sb.append('\n');
        }
        return sb;
    }

    private static long measurePattern(Runnable pattern) {
        long start = System.nanoTime();
        pattern.run();
        return System.nanoTime() - start;
    }

    private static void printStatistics(String patternName, long[] times) {
        double avg = calculateAverage(times);
        double stdDev = calculateStdDev(times, avg);
        long min = calculateMin(times);
        long max = calculateMax(times);
        
        System.out.printf("%s Pattern:%n", patternName);
        System.out.printf("  Average time: %.3f ms%n", avg / 1_000_000.0);
        System.out.printf("  Std Dev    : %.3f ms%n", stdDev / 1_000_000.0);
        System.out.printf("  Min time   : %.3f ms%n", min / 1_000_000.0);
        System.out.printf("  Max time   : %.3f ms%n", max / 1_000_000.0);
    }

    private static double calculateAverage(long[] times) {
        return java.util.Arrays.stream(times).average().orElse(0);
    }

    private static double calculateStdDev(long[] times, double mean) {
        return Math.sqrt(java.util.Arrays.stream(times)
            .mapToDouble(t -> t - mean)
            .map(d -> d * d)
            .average()
            .orElse(0));
    }

    private static long calculateMin(long[] times) {
        return java.util.Arrays.stream(times).min().orElse(0);
    }

    private static long calculateMax(long[] times) {
        return java.util.Arrays.stream(times).max().orElse(0);
    }
}
