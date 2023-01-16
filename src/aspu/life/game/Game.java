package aspu.life.game;

import java.util.List;
import java.util.Random;

public class Game {
    /**
     * сетка
     */
    private Grid grid;

    /**
     * форма для отрисовки сетки
     */
    private GridView gridView;

    /**
     * создает экземпляр игры с заданной сеткой и окном отображения
     * @param grid - сетка {Grid}
     * @param gridView - отображение сетки {GridView}
     */
    public Game(Grid grid, GridView gridView) {
        super();
        updateGrid(grid, gridView);
        filling();
    }

    /**
     * выполняет один шаг игры
     */
    public void step() {
        updateGrid();
        gridView.updateView();
    }

    /**
     * Обновление данных игры
     * @param grid - сетка {Grid}
     * @param gridView - отображение сетки {GridView}
     */
    public void updateGrid(Grid grid, GridView gridView){
        this.grid = grid;
        this.gridView = gridView;
    }

    /**
     * посекундно выполняет заданное количество секунд игру
     * @param n - количество секунд для проигрывания {int}
     */
    public void multipleSteps(int n) {
        try {
            for (int i = 0; i < n; i++) {
                step();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * выполняет обновление сетки в рамках одного шага
     */
    private void updateGrid() {
        int width = grid.getWidth();
        int height = grid.getHeight();
        // все модификации выполняем в новой временной сетке
        Grid tempGrid = new Grid(width, height);
        // перебираем каждую ячейку сетки
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                // приводим текущую ячейку к классу Cell
                Cell currentCell = (Cell) grid.getCellAt(i, j);
                // получаем список соседних с текущей ячеек
                List<CellInterface> neighbours = grid.get8Neighbours(i, j);
                // считаем количество живых соседей
                int aliveNeighbours = countAliveCells(neighbours);
                // создаем новую ячейку как копию текущей
                Cell newCell = new Cell(currentCell.isAlive());
                // изменяем состояние созданной ячейки в соответствии с
                // правилами
                if (currentCell.isAlive()) {
                    if ((aliveNeighbours < 2) || (aliveNeighbours > 3))
                        newCell.setAlive(false);
                }
                if ((!currentCell.isAlive()) && (aliveNeighbours == 3))
                    newCell.setAlive(true);
                // помещаем новую ячейку в новую сетку
                tempGrid.setCellAt(i, j, newCell);
            }
        // переносим все ячейки из временной сетки в рабочую
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                grid.setCellAt(i, j, tempGrid.getCellAt(i, j));
    }

    /**
     * подсчитывает "живые" ячейки в заданном списке ячеек
     * @param cells - список ячеек {List<CellInterface>}
     * @return - количество живых ячеек
     */
    private int countAliveCells(List<CellInterface> cells) {
        int aliveNeighbours = 0;
        // перебираем все ячейки в списке
        for (CellInterface c : cells)
            // проверяем, что ячейка существует и является ячейкой игры "Жизнь"
            if ((c != null) && (c instanceof Cell)) {
                // приводим ячейку к классу LifeCell
                Cell lcell = (Cell) c;
                if (lcell.isAlive())
                    aliveNeighbours++;
            }
        return aliveNeighbours;
    }

    /**
     * Заполняет рандомно сетку живыми или не живыми клетками
     */
    public void populate() {
        int width = grid.getWidth();
        int height = grid.getHeight();
        Random rnd = new Random();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                grid.setCellAt(i, j, new Cell(rnd.nextBoolean()));
            }

    }

    /**
     * Заполнение сетки "мертвыми" ячейками
     */
    public void filling() {
        int width = grid.getWidth();
        int height = grid.getHeight();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                grid.setCellAt(i, j, new Cell(false));
            }

    }
}
