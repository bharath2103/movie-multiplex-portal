package models;

import java.util.List;

public class MultiplexModelList {
    private List<MultiplexModel> multiplexModels;

    public MultiplexModelList() {
    }

    public MultiplexModelList(List<MultiplexModel> multiplexModels) {
        this.multiplexModels = multiplexModels;
    }

    public List<MultiplexModel> getMultiplexModels() {
        return multiplexModels;
    }

    public void setMultiplexModels(List<MultiplexModel> multiplexModels) {
        this.multiplexModels = multiplexModels;
    }

    @Override
    public String toString() {
        return "MultiplexModelList{" +
                "multiplexModels=" + multiplexModels +
                '}';
    }
}
