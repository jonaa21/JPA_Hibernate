package sr.unasat.jpa.datastructures.builder;

public class ProductBuilder implements ProductSpecs {

    private int memory, size;
    private boolean hasKeyboard, isTouchScreen, hasPowerAdapter, hasCamera;

    public ProductBuilder() {
    }

    public ProductBuilder(int memory, char size, boolean hasKeyboard,
                          boolean isTouchScreen, boolean hasPowerAdapter, boolean hasCamera) {
        this.memory = memory;
        this.size = size;
        this.hasKeyboard = hasKeyboard;
        this.isTouchScreen = isTouchScreen;
        this.hasPowerAdapter = hasPowerAdapter;
        this.hasCamera = hasCamera;
    }

    @Override
    public void hasKeyboard(boolean hasKeyboard) {
        this.hasKeyboard = hasKeyboard;
    }

    @Override
    public void isTouchScreen(boolean isTouchScreen) {
        this.isTouchScreen = isTouchScreen;
    }

    @Override
    public void hasPowerAdapter(boolean hasPowerAdapter) {
        this.hasPowerAdapter = hasPowerAdapter;
    }

    @Override
    public void hasCamera(boolean hasCamera) {
        this.hasCamera = hasCamera;
    }

    public int getMemory() {
        return memory;
    }

    @Override
    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void setSize(char size) {
        this.size = size;
    }

    public boolean isHasKeyboard() {
        return hasKeyboard;
    }

    public boolean isTouchScreen() {
        return isTouchScreen;
    }

    public boolean isHasPowerAdapter() {
        return hasPowerAdapter;
    }

    public boolean isHasCamera() {
        return hasCamera;
    }

}
