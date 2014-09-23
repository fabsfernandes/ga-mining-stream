
package br.com.ufu.lsi.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ufu.lsi.util.RandomGenerator;

public class StreamGenerator {

    private static final Logger logger = LogManager.getLogger( StreamGenerator.class.getName() );

    private static final String DATA_SET_ORIGINAL_PATH = "/Users/fabiola/Doutorado/IA/Trabalho1/dataset/nursery.csv";

    private static final String DATA_SET_GENERATED_PATH = "/Users/fabiola/Doutorado/IA/Trabalho1/dataset/nurseryGenerated.csv";

    public BufferedReader buildDataset( int datasetSize ) {

        List< String > records = new ArrayList< String >();

        BufferedReader bufferReader = null;

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

            PrintWriter writer = new PrintWriter( DATA_SET_GENERATED_PATH, "UTF-8" );
            for ( String record : records ) {
                writer.println( record );
            }
            writer.close();

            bufferReader = new BufferedReader( new FileReader( DATA_SET_GENERATED_PATH ) );

        } catch ( IOException e ) {
            logger.error( e.getMessage() );
            return null;
        }

        return bufferReader;

    }

    
    /**
     * Read sequentially from generated dataset. This is ok because it was
     * generate randomly. The param datasetSize is just due to compatibility
     *  
     * @param recordsNumber
     * @param datasetSize
     * @param bufferedReader
     * @return
     */
    public List< String > readChunkSequence( int recordsNumber, int datasetSize, BufferedReader bufferedReader ) {

        List< String > records = new ArrayList< String >();

        for ( int i = 0; i < recordsNumber; i++ ) {
            try {
                String record = bufferedReader.readLine();
                if ( record == null )
                    break;
                records.add( record );

            } catch ( IOException e ) {
                logger.error( e.getMessage() );
                return null;
            }
        }

        return records;
    }

    
    /**
     * Randomly get records from generated file. The records can repeat, even in
     * the same chunk. The param bufferedReader is just due to compatibility
     * issues
     * 
     * @param recordsNumber
     * @param datasetSize
     * @param bufferedReader
     * @return
     */
    public List< String > readChunkRandomly( int recordsNumber, int datasetSize,
            BufferedReader bufferedReader ) {

        List< String > records = new ArrayList< String >();

        for ( int i = 0; i < recordsNumber; i++ ) {

            try {
                BufferedReader bf = this.initializeStream();
                int random = RandomGenerator.randInt( 0, datasetSize - 1 );

                for ( int j = 0; j < random; j++ ) {
                    bf.readLine();
                }

                String record = bf.readLine();
                records.add( record );

            } catch ( IOException e ) {
                logger.error( e.getMessage() );
                return null;
            }
        }
        return records;
    }

    public BufferedReader initializeStream() {

        try {
            BufferedReader br = new BufferedReader( new FileReader( DATA_SET_GENERATED_PATH ) );
            return br;

        } catch ( FileNotFoundException e ) {
            logger.error( e.getMessage() );
            return null;
        }

    }

}
