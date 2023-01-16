package aspu.life.game;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    /**
     * Класс главного окна
     * @param width - ширина экрана
     * @param height - высота экрана
     */
    public Window(int width, int height){
        super("Игра 'Жизнь'");
        setSize(width, height);
    }
}
