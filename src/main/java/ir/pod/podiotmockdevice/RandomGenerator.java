package ir.pod.podiotmockdevice;

import java.util.Arrays;
import java.util.Random;

/**
 * @author S-Serajzadeh
 * @Date 9/10/22
 * @Project PANEL IOT-Bank
 */
public class RandomGenerator {
    public static int[] generateIncreasingRandoms(int amount, int max) {
        int[] randomNumbers = new int[amount];
        Random random = new Random();
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = random.nextInt(max);
        }
        Arrays.sort(randomNumbers);
        return randomNumbers;
    }

    public static Integer generateRandom(int max) {
        return new Random().nextInt(max);
    }

    public static String generateString(String... args) {
        return args[generateRandom(args.length)];
    }
}