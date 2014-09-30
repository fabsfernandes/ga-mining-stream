
package br.com.ufu.lsi.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.Nursery;
import br.com.ufu.lsi.model.Window;
import br.com.ufu.lsi.model.WindowStatistic;

public class StreamDistributor {

    public void initializeWindows( HashMap< String, Window > windows, List< String > encodedClasses ) {

        for ( String encodedClass : encodedClasses ) {

            Window w = windows.get( encodedClass );

            if ( w != null ) {
                w.clear();
            } else {
                windows.put( encodedClass, new Window( encodedClass, new ArrayList< Chromossome >() ) );
            }
        }

    }

    public List< Chromossome > analyzeWindows( int k, List< String > chunk, HashMap< String, Window > windows ) {

        List< Chromossome > chromossomes = new ArrayList< Chromossome >();
        Map< String, Integer > quantities = new HashMap< String, Integer >();

        // calculate proportion and number of registers
        for ( String line : chunk ) {

            Chromossome chromossome = new Nursery();
            String encodedClass = chromossome.encode( line );

            chromossomes.add( chromossome );

            if ( quantities.get( encodedClass ) == null ) {
                quantities.put( encodedClass, 1 );
            } else {
                Integer quantity = quantities.get( encodedClass );
                quantities.put( encodedClass, ++quantity );
            }
        }

        // for each window define window size
        for ( Map.Entry< String, Window > windowMap : windows.entrySet() ) {
            String windowClass = windowMap.getKey();
            Window window = windowMap.getValue();

            if ( quantities.get( windowClass ) != null ) {

                WindowStatistic stat = new WindowStatistic( quantities.get( windowClass ), ( quantities.get( windowClass ).doubleValue() / chunk.size() ) );

                List< WindowStatistic > historyStatistics = window.getHistoryStatistics();
                historyStatistics.add( stat );

                if ( historyStatistics.size() < k ) {
                    window.setCurrentSize( stat );
                    
                } else {

                    double sum = 0.0;
                    for ( int i = 0; i < k; i++ ) {
                        sum += historyStatistics.get( i ).getProportion();
                    }
                    double averageProportion = sum / k;

                    /*if ( averageProportion > 0.5 && window.getCurrentSize().getProportion() < 0.5 ) {
                        // update window size
                        window.setCurrentSize( stat );
                    }
                    if ( averageProportion < 0.5 && window.getCurrentSize().getProportion() > 0.5 ) {
                        // update window size
                        window.setCurrentSize( stat );
                    }*/
                    if (averageProportion - window.getCurrentSize().getProportion()  > 0.001 ) {
                        // update window size
                        window.setCurrentSize( stat );
                    }
                    
                }
            }
        }

        return chromossomes;
    }

    public void distribute( List< Chromossome > chromossomes, HashMap< String, Window > windows ) {
        
        /*for ( Map.Entry< String, Window > windowMap : windows.entrySet() ) {
           
            Window window = windowMap.getValue();
            window.getChromossomes().clear();
        }
        
        for ( Chromossome chromossome : chromossomes ) {
            
            Window window = windows.get( chromossome.getEncodedClass() );
            window.getChromossomes().add( chromossome );
        }*/

  //      System.out.println( "== ANTES == " );
        Window window2 = windows.get( "00100");
        /*for( Chromossome c : window2.getChromossomes() ){
            System.out.println( c.toStringEncoded() );
        }*/
 //       System.out.println( " TOTAL ANTES = " + window2.getChromossomes().size() );
        
  //      System.out.println("== LIDOS ==");
        int total = 0;
        for ( Chromossome chromossome : chromossomes ) {
            if( chromossome.getEncodedClass().equals( "00100" ) ){
                //System.out.println( chromossome.toStringEncoded() );
                total++;
            }
        }
  //      System.out.println( " TOTAL LIDOS = " + total );
        
        
        
        for ( Chromossome chromossome : chromossomes ) {
            
            Window window = windows.get( chromossome.getEncodedClass() );
            window.getChromossomes().add( chromossome );
        }

        for ( Map.Entry< String, Window > windowMap : windows.entrySet() ) {
            Window window = windowMap.getValue();

            List< Chromossome > chroms = window.getChromossomes();
            
            for( ;window.getCurrentSize().getSize() < chroms.size();   ){
                //if( window.getEncodedClass().equals( "10000" ))
                //System.out.println( "Remove: " + chroms.get( 0 ).toStringEncoded() );
                chroms.remove( 0 );
            }
            
        }
        
 //       System.out.println( "== RESULTADO == " );
        Window window3 = windows.get( "00100");
        for( Chromossome c : window3.getChromossomes() ){
            //System.out.println( c.toStringEncoded() );
        }
  //      System.out.println( " TOTAL RESULTADO = " + window3.getChromossomes().size() );

    }

}
