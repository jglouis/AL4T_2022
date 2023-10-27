package manager;

import java.awt.event.KeyEvent;

public interface InputListener {
    void onKeyPressed(int keyCode);
    void onKeyReleased(int keyCode);
    void onMousePressed();
}