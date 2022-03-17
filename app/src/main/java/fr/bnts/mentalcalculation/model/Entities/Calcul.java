package fr.bnts.mentalcalculation.model.Entities;

public class Calcul extends BaseEntity{
    Long premierElement;
    Long deuxiemeElement;
    Integer resultat;
    String symbol;

    public Long getPremierElement() {
        return premierElement;
    }

    public void setPremierElement(Long premierElement) {
        this.premierElement = premierElement;
    }

    public Long getDeuxiemeElement() {
        return deuxiemeElement;
    }

    public void setDeuxiemeElement(Long deuxiemeElement) {
        this.deuxiemeElement = deuxiemeElement;
    }

    public Integer getResultat() {
        return resultat;
    }

    public void setResultat(Integer resultat) {
        this.resultat = resultat;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
