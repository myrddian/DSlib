package dslib.dataframe.kernels;

public class IndexValueDoubleTuple implements Comparable<IndexValueDoubleTuple> {
    private int index;
    private double value;

    public IndexValueDoubleTuple(int index, double value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() { return index; }
    public double getValue() { return value; }

    @Override
    public int compareTo(IndexValueDoubleTuple o) {
        if(this.value<o.value)
            return -1;
        else if(o.value<this.value)
            return 1;
        return 0;
    }
}
