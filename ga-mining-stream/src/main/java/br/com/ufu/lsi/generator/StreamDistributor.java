
package br.com.ufu.lsi.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.Nursery;
import br.com.ufu.lsi.model.Window;

public class StreamDistributor {

    public void initializeWindows( HashMap< String, Window > windows, List< String > encodedClasses ) {

        for ( String encodedClass : encodedClasses ) {

            Window w = windows.get( encodedClass );
            
            if ( w != null ) {
                w.clear();
            } else {
                windows.put( encodedClass, new Window( encodedClass, new ArrayList<Chromossome>() ) );
            }
        }

    }
    
    
    public void resizeWindows() {
        
    }

    public void distribute( List< String > chunk, HashMap< String, Window > windows ) {

        for ( String line : chunk ) {

            Chromossome chromossome = new Nursery();
            String encodedClass = chromossome.encode( line );

            Window window = windows.get( encodedClass );
            window.getChromossomes().add( chromossome );
        }
    }

}
