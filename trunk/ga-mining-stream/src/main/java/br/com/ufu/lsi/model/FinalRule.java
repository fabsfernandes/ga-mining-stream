package br.com.ufu.lsi.model;

public class FinalRule {
    
    private Chromossome rule;
    
    private Double support;
    
    private Double confidence;

    public FinalRule( Chromossome rule, Double support, Double confidence ) {
        super();
        this.rule = rule;
        this.support = support;
        this.confidence = confidence;
    }

    public Chromossome getRule() {
        return rule;
    }

    public void setRule( Chromossome rule ) {
        this.rule = rule;
    }

    public Double getSupport() {
        return support;
    }

    public void setSupport( Double support ) {
        this.support = support;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence( Double confidence ) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "FinalRule [rule=" + rule.toStringEncoded() + ", support=" + support + ", confidence=" + confidence
                + "]";
    }

}
