package dslib.dataframe.backend;

import dslib.dataframe.*;

import java.util.List;

public class DStoreFrameProxy implements DFrameStore {

    private DFrame backFrame;

    @Override
    public int size() {
        return backFrame.size();
    }


    @Override
    public DRow getRow(int index) {
        DRow tmp = backFrame.loc(index);
        return  tmp;
    }

    @Override
    public List<String> getColNames() {
        return backFrame.getColNames();
    }

    public void setBackFrame(DFrame backFrame) {
        this.backFrame = backFrame;
    }
    public DStoreFrameProxy() {}
    public DStoreFrameProxy(DFrame newBackFrame) {backFrame = newBackFrame;}

}
