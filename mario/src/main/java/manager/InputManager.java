package manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputManager implements KeyListener, MouseListener {

    private final InputListener listener;

    public InputManager(InputListener listener) {
        this.listener = listener;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        listener.onKeyPressed(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        listener.onKeyReleased(keyCode);
    }

    public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        listener.onMousePressed();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
