
package br.com.ufu.lsi.engine;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;

import br.com.ufu.lsi.generator.StreamDistributor;
import br.com.ufu.lsi.generator.StreamGenerator;
import br.com.ufu.lsi.model.NurseryDataset;
import br.com.ufu.lsi.model.Window;
import br.com.ufu.lsi.util.Print;

public class Main {

    private StreamGenerator streamGenerator = new StreamGenerator();

    private StreamDistributor streamDistributor = new StreamDistributor();

    private HashMap< String, Window > windows = new HashMap< String, Window >();

    public void run() {
        
        BufferedReader bf = streamGenerator.initializeStream();
        
        List< String > records = streamGenerator.readChunk( 5000, bf );

        streamDistributor.initializeWindows( windows, NurseryDataset.encodedClasses );

        streamDistributor.distribute( records, windows );
        
        Print.printWindows( windows );
    }

    public static void main( String... args ) {

        Main main = new Main();
        main.run();
    }

}
