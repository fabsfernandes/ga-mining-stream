package br.com.ufu.lsi.util.test;

import org.junit.Test;

import br.com.ufu.lsi.util.RandomGenerator;


public class TestRandom {
    
    @Test
    public void testRandomGenerator(){
        
        System.out.println( RandomGenerator.randInt( 0, 12959 ) );
        System.out.println( RandomGenerator.randInt( 0, 12959 ) );
    }

}
