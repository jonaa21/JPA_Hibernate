package sr.unasat.jpa.datastructures.builder;

public interface ProductSpecs {

    void setMemory(int memory);

    void hasKeyboard(boolean hasKeyboard);

    void isTouchScreen(boolean isTouchScreen);

    void hasPowerAdapter(boolean hasPowerAdapter);

    void hasCamera(boolean hasCamera);

    void setSize(char size);
}
