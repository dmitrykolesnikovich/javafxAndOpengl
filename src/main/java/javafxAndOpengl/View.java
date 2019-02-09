package javafxAndOpengl;

import javafx.scene.image.ImageView;

public interface View {

    void onUpdate();

    void onResize(int width, int height);
    void onResizeWidth(int width);
    void onResizeHeight(int width);

    void onClose();
    ImageView get();

}
