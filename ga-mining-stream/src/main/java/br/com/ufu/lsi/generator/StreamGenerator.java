
package br.com.ufu.lsi.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StreamGenerator {

    private static final Logger logger = LogManager.getLogger( StreamGenerator.class.getName() );
    
    private static final String DATA_SET_PATH = "/Users/fabiola/Doutorado/IA/Trabalho1/dataset/nursery.csv";
    
    
    public BufferedReader initializeStream() {
        
        try {
            BufferedReader br = new BufferedReader( new FileReader( DATA_SET_PATH ) );
            return br;
           
        } catch ( FileNotFoundException e ) {
            logger.error( e.getMessage() );
            return null;
        }
        
    }

    public List<String> readChunk( int recordsNumber, BufferedReader bf ) {
        
        List<String> records = new ArrayList<String>();

        for( int i=0; i<recordsNumber; i++ ){
            try {
                String record = bf.readLine();
                if( record == null )
                    break;
                records.add( record );
                
            } catch ( IOException e ) {
                logger.error( e.getMessage() );
                return null;
            }    
        }
        
        return records;
    }
    

}
