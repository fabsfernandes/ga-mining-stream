
package br.com.ufu.lsi.util;

import java.util.Random;

public class RandomGenerator {
    
    public static Random random;
    
    private static long seed = 100;
    
    static {
        random = new Random( seed );
    }

    public static int randInt( int min, int max ) {

        int randomNum = random.nextInt( ( max - min ) + 1 ) + min;

        return randomNum;
    }
    
    

}
