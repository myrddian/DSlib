package dslib.dataframe.kernels;

public class IndexValueStringTuple implements Comparable<IndexValueStringTuple> {
    private int index;
    private String value;

    public IndexValueStringTuple(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() { return index; }
    public String getValue() { return value; }

    @Override
    public int compareTo(IndexValueStringTuple o) {
        return this.value.length() - o.value.length();
    }
}
