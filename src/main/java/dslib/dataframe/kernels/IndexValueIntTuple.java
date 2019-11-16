package dslib.dataframe.kernels;

public class IndexValueIntTuple implements Comparable<IndexValueIntTuple> {
    private int index;
    private long value;

    public IndexValueIntTuple(int index, long value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() { return index; }
    public long getValue() { return value; }

    @Override
    public int compareTo(IndexValueIntTuple o) {
        return (int)(this.value - o.value);
    }
}
