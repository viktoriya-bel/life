package aspu.life.game;

import java.awt.*;

public class Cell implements CellInterface {
    /**
     * признак, является ли ячейка живой
     */
    private boolean alive = false;

    /**
     * создает новую ячейку, указывая ее состояние
     * @param alive - флаг состояния клетки (живая/не живая) {boolean}
     */
    public Cell(boolean alive) {
        super();
        this.alive = alive;
    }

    /**
     * возвращает состояние ячейки
     * @return {boolean}
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * устанавливает состояние ячейки
     * @param alive - {boolean}
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    /**
     * возвращает цвет ячейки (реализация метода интерфейса Cell)
     * цвет ячейки зависит от ее состояния
     * @return
     */
    public Color getColor() {
        if (isAlive())
            return Color.GREEN;
        else
            return Color.WHITE;
    }
}
