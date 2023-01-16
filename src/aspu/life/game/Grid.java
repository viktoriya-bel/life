package aspu.life.game;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    /**
     * константы, описывающие направления поиска соседних ячеек
     */
    public static final int WEST = 1;
    public static final int EAST = 2;
    public static final int NORTH = 3;
    public static final int SOUTH = 4;
    public static final int NORTHWEST = 5;
    public static final int NORTHEAST = 6;
    public static final int SOUTHWEST = 7;
    public static final int SOUTHEAST = 8;

    /**
     * двумерный массив ячеек
     */
    private CellInterface[][] cells;

    /**
     * ширина сетки (в ячейках)
     */
    private int width;

    /**
     * высота сетки (вячейках)
     */
    private int height;

    /**
     * Создает сетку с заданным количеством яччек по ширине и высоте
     * @param width - ширина сетки {int}
     * @param height - высота сетки {int}
     */
    public Grid(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        cells = new CellInterface[width][height];
    }

    /**
     * возвращает ширину сетки
     * @return - ширинс сетки
     */
    public int getWidth() {
        return width;
    }

    /**
     * возвращает высоту сетки
     * @return - высота сетки
     */
    public int getHeight() {
        return height;
    }

    /**
     * возвращает ячейку в заданном столбце и строке
     * если заданы координаты, выходящие за границы сетки, возвращает null
     * @param x - координата клетки х в ячейках {int}
     * @param y - координата клетки y в ячейках {int}
     * @return - возращает ячейку, если она вне игрового поля возвращает null
     */
    public CellInterface getCellAt(int x, int y) {
        if ((x >= width) || (x < 0))
            return null;
        if ((y >= height) || (y < 0))
            return null;
        return cells[x][y];
    }

    /**
     * устанавливает ячейку в заданном столбце и строке
     * @param x - координата клетки х в ячейках {int}
     * @param y - координата клетки y в ячейках {int}
     * @param cell - данные ячейки {CellInterface}
     */
    public void setCellAt(int x, int y, CellInterface cell) {
        cells[x][y] = cell;
    }

    /**
     * возвращает соседнюю ячейку для ячейки с заданными координатами x и y
     * в заданном направлении
     * (т.е. для параметров x=4, y=6, direction = SOUTH
     * соседней ячейкой будет ячейка с координатами x=4, y=7)
     * @param x - координата клетки х в ячейках {int}
     * @param y - координата клетки y в ячейках {int}
     * @param direction - направления поиска соседней ячейки {int}
     * @return возвращает найденную ячейку
     */
    public CellInterface getNeigbour(int x, int y, int direction) {
        int xres = findNeighbourX(x, direction);
        int yres = findNeighbourY(y, direction);
        return getCellAt(xres, yres);
    }

    /**
     * определяет координату x для соседней ячейки по указанному направлению
     * @param x- координата клетки х в ячейках {int}
     * @param direction - направления поиска соседней ячейки {int}
     * @return возвращает координату x найденной ячейки
     */
    private int findNeighbourX(int x, int direction) {
        int xres = x;
        if ((direction == NORTHWEST) || (direction == NORTH) || (direction == NORTHEAST))
            xres = x - 1;
        if ((direction == SOUTHWEST) || (direction == SOUTH) || (direction == SOUTHEAST))
            xres = x + 1;
        return xres;
    }

    /**
     * определяет координату y для соседней ячейки по указанному направлению
     * @param y - координата клетки y в ячейках {int}
     * @param direction - направления поиска соседней ячейки {int}
     * @return возвращает координату y найденной ячейки
     */
    private int findNeighbourY(int y, int direction) {
        int yres = y;
        if ((direction == NORTHWEST) || (direction == WEST) || (direction == SOUTHWEST))
            yres = y - 1;
        if ((direction == NORTHEAST) || (direction == EAST) || (direction == SOUTHEAST))
            yres = y + 1;
        return yres;
    }

    /**
     * возвращает список восьми соседних ячеек для ячейки с заданными координатами
     * гарантируется, что результирующий список не будет содержать null,
     * т.е. метод вернет только непустые (существующие) ячейки
     * @param x - координата клетки х в ячейках {int}
     * @param y - координата клетки y в ячейках {int}
     * @return - возвращает массив соседей
     */
    public List<CellInterface> get8Neighbours(int x, int y) {
        List<CellInterface> result = new ArrayList<CellInterface>();
        CellInterface[] res = new CellInterface[8];
        res[0] = getNeigbour(x, y, NORTHWEST);
        res[1] = getNeigbour(x, y, NORTH);
        res[2] = getNeigbour(x, y, NORTHEAST);
        res[3] = getNeigbour(x, y, EAST);
        res[4] = getNeigbour(x, y, SOUTHEAST);
        res[5] = getNeigbour(x, y, SOUTH);
        res[6] = getNeigbour(x, y, SOUTHWEST);
        res[7] = getNeigbour(x, y, WEST);
        for (CellInterface c: res)
            if (c != null)
                result.add(c);
        return result;
    }
}
