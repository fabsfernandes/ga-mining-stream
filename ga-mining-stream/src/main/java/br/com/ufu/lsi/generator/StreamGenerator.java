
package br.com.ufu.lsi.generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ufu.lsi.model.NurseryDataset;
import br.com.ufu.lsi.util.RandomGenerator;

public class StreamGenerator {

    private static final Logger logger = LogManager.getLogger( StreamGenerator.class.getName() );

    private static final String DATA_SET_ORIGINAL_PATH = "/Users/fabiola/Doutorado/IA/Trabalho1/dataset/nursery4classes.csv";
    
    public List< String > buildDataset( int datasetSize ) {

        List< String > records = new ArrayList< String >();

        try {

            while ( records.size() < datasetSize ) {

                BufferedReader br = new BufferedReader( new FileReader( DATA_SET_ORIGINAL_PATH ) );

                for ( ;; ) {
                    String record = br.readLine();

                    if ( records.size() >= datasetSize || record == null ) {
                        br.close();
                        break;
                    }
                    records.add( record );
                }
                br.close();
            }

            Collections.shuffle( records, RandomGenerator.random );

        } catch ( IOException e ) {
            logger.error( e.getMessage() );
            return null;
        }

        return records;

    }

    public List< String > readChunkSequence( List<String> lines, int index, int chunkLength ) {

        if( index > lines.size() )
            return null;
        
        int toIndex = index + chunkLength;
        if( toIndex > lines.size() ) {
            toIndex = lines.size() - 1; 
        }
        List< String > records = lines.subList( index, toIndex );

        return records;
    }
    
    public void conceptDriftOnAttribute( List< String > dataset, int attIndex ) {
        
        for( int i = 0; i< dataset.size(); i++ ) {
            String[] attributes = dataset.get( i ).split( "," );
            String attValue = attributes[attIndex];
            HashMap<String,String> attValues = NurseryDataset.attributes.get( attIndex );
            String encodedValue = attValues.get( attValue );
            
            
            char [] encodedValueArray = encodedValue.toCharArray();
            String str = "";
            for( int k = 0; k<encodedValue.length()-1; k++ ) {
                str += encodedValueArray[k];
            }
            str = encodedValueArray[ encodedValueArray.length - 1] + str;
            
            for ( Map.Entry< String, String > attValue1 : attValues.entrySet() ) {
                String strKey = attValue1.getKey();
                String strValue = attValue1.getValue();
                if( strValue.equals( str ) ){
                    attributes[attIndex] = strKey;
                    String finishLine = "";
                    
                    for( int j = 0; j < attributes.length-1; j++ )
                        finishLine += attributes[j] + ",";
                    finishLine += attributes[ attributes.length-1 ];
                    dataset.set( i, finishLine );
                }
            }
        }
    }


}
