package ndrwk.converter.Model;

import java.util.List;


public class ModelHolder {
    private static final ModelHolder instance = new ModelHolder();

    public static ModelHolder getInstance() {
        return instance;
    }

    private ModelHolder() {
    }

    private List<Currency> currencyList;
    private int sourceCell;
    private int destCell;
    private double sourceSumm;
    private double destSumm;
    private int dialogFlag = 0;
    private String xml = "";

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public int getSourceCell() {
        return sourceCell;
    }

    public int getDestCell() {
        return destCell;
    }

    public double getSourceSumm() {
        return sourceSumm;
    }

    public double getDestSumm() {
        return destSumm;
    }

    public int getDialogFlag() {
        return dialogFlag;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public void setSourceCell(int sourceCell) {
        this.sourceCell = sourceCell;
    }

    public void setDestCell(int destCell) {
        this.destCell = destCell;
    }

    public void setSourceSumm(double sourceSumm) {
        this.sourceSumm = sourceSumm;
    }

    public void setDestSumm(double destSumm) {
        this.destSumm = destSumm;
    }

    public void setDialogFlag(int dialogFlag) {
        this.dialogFlag = dialogFlag;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
