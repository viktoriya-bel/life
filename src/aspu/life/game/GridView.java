package aspu.life.game;

import javax.swing.*;
import java.awt.*;

public class GridView {
    /**
     * ширина сетки (в ячейках)
     */
    private int height;

    /**
     * высота сетки (в ячейках)
     */
    private int width;

    /**
     * размер ячейки (в пикселях)
     */
    private int cellSize;

    /**
     * сетка
     */
    private Grid grid;

    /**
     * признак отрисовки линий сетки
     */
    private boolean showGridLines = false;

    /**
     * константа - цвет фона
     */
    public final static Color BACKGROUND_COLOR = Color.WHITE;

    /**
     * константа - цвет линий стеки
     */
    public final static Color LINE_COLOR = Color.LIGHT_GRAY;

    /**
     * Экземпляр текущего окна
     */
    private JFrame frame;

    /**
     * Экземпляр канваса
     */
    private GridPanel canvasPanel;

    /**
     * Экземпляр изображения
     */
    private Image canvasImage;

    /**
     * ширина и высота формы (в пикселях)
     */
    private int frameHeight, frameWidth;

    /**
     * создает новое отображение игровго поля
     * @param grid - сетка {Grid}
     * @param cellSize - размер ячейки {int}
     * @param frame - экземпляр окна {JFrame}
     */
    public GridView(Grid grid, int cellSize, JFrame frame) {
        super();
        this.grid = grid;
        this.height = grid.getHeight();
        this.width = grid.getWidth();
        this.cellSize = cellSize;
        this.frame = frame;
        frameHeight = height * cellSize;
        frameWidth = width * cellSize;
        buildFrame();
    }

    /**
     * построение окна при создании окна
     */
    private void buildFrame() {
        canvasPanel = new GridPanel();
        canvasPanel.setPreferredSize(new Dimension(frameWidth, frameHeight));
        canvasImage = canvasPanel.createImage(frameWidth, frameHeight);
        frame.add(canvasPanel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * установка новой сетки и размера ячейки
     * @param grid - сетка {Grid}
     * @param cellSize - размер ячейки {int}
     */
    public void setGrid(Grid grid, int cellSize) {
        this.grid = grid;
        this.height = grid.getHeight();
        this.width = grid.getWidth();
        this.cellSize = cellSize;
        frameHeight = height * cellSize;
        frameWidth = width * cellSize;
        canvasPanel.setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.pack();
    }

    /**
     * обновление (перерисовка) окна
     */
    public void updateView() {
        if (canvasImage == null)
            canvasImage = canvasPanel.createImage(frameWidth, frameHeight);
        drawGrid();
        if (showGridLines)
            drawLines();
        canvasPanel.repaint();
    }

    /**
     * установка признака отрисовки линий сетки
     * @param showGridLines - флаг - признак отрисовывать ли сетку {boolean}
     */
    public void setShowGridLines(boolean showGridLines) {
        this.showGridLines = showGridLines;
    }

    /**
     * отрисовка ячеек сетки
     */
    private void drawGrid() {
        Graphics2D g = (Graphics2D)canvasImage.getGraphics();
        // рисуем прямоугольник - фон
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, frameWidth, frameHeight);
        // рисуем рамку
        g.setColor(LINE_COLOR);
        g.drawRect(0, 0, frameWidth - 1, frameHeight - 1);
        // рисуем ячейки
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                if (grid.getCellAt(i, j) == null)
                    continue;
                int left = i * cellSize;
                int top = j * cellSize;
                g.setColor(grid.getCellAt(i, j).getColor());
                g.fillRect(left, top, cellSize, cellSize);
            }
    }

    /**
     * отрисовка линий сетки
     */
    private void drawLines() {
        Graphics2D g = (Graphics2D)canvasImage.getGraphics();
        g.setColor(LINE_COLOR);
        for (int i = 0; i < width * cellSize; i += cellSize)
            g.drawLine(i, 0, i, frameHeight);
        for (int j = 0; j < height * cellSize; j+=cellSize)
            g.drawLine(0, j, frameWidth, j);
    }

    /**
     * служебный компонент для рисования
     */
    private class GridPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
}
