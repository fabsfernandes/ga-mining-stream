
package br.com.ufu.lsi.model;

import java.util.ArrayList;
import java.util.List;

public class Window {

    private List< Chromossome > chromossomes;

    private String encodedClass;

    private WindowStatistic currentSize;

    private List< WindowStatistic > historyStatistics;

    public Window( String encodedClass, List< Chromossome > chromossomes ) {
        this.encodedClass = encodedClass;
        this.chromossomes = chromossomes;
        this.historyStatistics = new ArrayList< WindowStatistic >();
        this.currentSize = new WindowStatistic( 0, 0.0 );
    }

    public List< Chromossome > getChromossomes() {
        return chromossomes;
    }

    public void setChromossomes( List< Chromossome > chromossomes ) {
        this.chromossomes = chromossomes;
    }

    public String getEncodedClass() {
        return encodedClass;
    }

    public void setEncodedClass( String encodedClass ) {
        this.encodedClass = encodedClass;
    }

    public void clear() {
        this.chromossomes.clear();
    }

    public List< WindowStatistic > getHistoryStatistics() {
        return historyStatistics;
    }

    public void setHistoryStatistics( List< WindowStatistic > historyStatistics ) {
        this.historyStatistics = historyStatistics;
    }

    public WindowStatistic getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize( WindowStatistic currentSize ) {
        this.currentSize = currentSize;
    }

}
