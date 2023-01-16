package aspu.life.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Program {
    /**
     * Константа ширины экрана
     */
    final private static int WIDTH = 270;
    /**
     * Константа высоты экрана
     */
    final private static int HEIGHT = 600;
    /**
     * Константа для размера клетки игрового поля
     */
    final private static int CELL_SIZE = 25;
    /**
     * Экземпляр сетки ячеек
     */
    public static Grid grid;
    /**
     * Экзампляр класса отображения ячеек
     */
    public static GridView view;
    /**
     * Экземпляр класса игры
     */
    public static Game game;
    /**
     * Кнопка установки значений вручную
     */
    public static Button setGridButton;
    /**
     * Кнопка начала прогрывания игры
     */
    public static Button startButton;
    /**
     * Кнопка рандомного генирирования начальных значений
     */
    public static Button generateGridButton;
    /**
     * Кнопка загрузки из файла начальных значениц
     */
    public static Button loadFileGridButton;
    /**
     * Кнопка очистки данной "игры"
     */
    public static Button clearButton;
    /**
     * Время выполнения "игры"
     */
    public static int playTime = 15;


    /**
     * Метод запуска игры. Отвечает за отображение элементов и функциональность
     */
    public void start(){
        Window window = new Window(WIDTH, HEIGHT);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,1,10,10));
        window.setLayout(new GridLayout(2,1,10,10));

        String[] items = {
                "1 сек",
                "5 сек",
                "10 сек",
                "15 сек",
                "30 сек",
        };
        JComboBox timeComboBox = new JComboBox(items);
        timeComboBox.setSelectedIndex(3);
        panel.add(timeComboBox);
        addBtnForPanel(panel);

        // создаем новую сетку размером х ячеек
        grid = new Grid(10, 10);
        // создаем новое окно для отображения сетки
        view = new GridView(grid, CELL_SIZE, window);
        // отображаем игровую сетку
        view.setShowGridLines(true);
        game = new Game(grid, view);
        window.add(panel);
        view.updateView();
        window.setAlwaysOnTop(true);
        window.setResizable(false);
        window.setVisible(true);


        // Слушатель для события выбора времени для проигрывания
        ActionListener timeChooseListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                String item = (String)box.getSelectedItem();
                System.out.println(Integer.parseInt(item.replaceAll("\\D+","")));
                playTime = Integer.parseInt(item.replaceAll("\\D+",""));
            }
        };
        timeComboBox.addActionListener(timeChooseListener);

        // обработчик кнопки генерации начальных значений
        generateGridButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.populate();
                view.updateView();
                enabled(true);
            }
        });

        // обработчик кнопки по загрузки начальных значений из файла
        loadFileGridButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileGridStorage gs = new FileGridStorage("./games/");
                grid = gs.loadGrid("data.json");
                if(grid != null){
                    view.setGrid(grid, CELL_SIZE);
                    game.updateGrid(grid, view);
                    view.updateView();
                }

                enabled(true);
            }
        });

        // обработчик кнопки очистки "игры"
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.filling();
                view.updateView();
                enabled(false);
            }
        });

        // слушитель для события клика по панели окна. используется для установки начальных значений в ручную
        MouseListener setGridListener = new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = (e.getY() - 30) / CELL_SIZE;

                enabled(true);
                if (x < grid.getWidth() && y < grid.getHeight()){
                    Cell newCell = new Cell(!grid.getCellAt(x, y).isAlive());
                    grid.setCellAt(x, y, newCell);

                    view.updateView();
                }
            }
        };
        setGridButton.addActionListener(e -> window.addMouseListener(setGridListener));

        // обработчик кнопки начала "игры"
        startButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) {
                new Thread() {
                    @Override public void run () {
                        window.removeMouseListener(setGridListener);
                        game.multipleSteps(playTime);

                        SwingUtilities.invokeLater(new Runnable(){
                            @Override public void run() {

                                view.updateView();
                            }
                        });
                    }
                }.start();
            }
        });

        // обработчик нажатия на кнопку закрытия окна
        window.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) { // в качестве аргумента передаем событие
                e.getWindow().dispose (); // уничтожает объект Frame
            }
        });
    }

    /**
     * Изменения состояние кнопок по состоянию: заполнена ли сетка? можно ли начинать игру?
     * @param isStart {boolean}
     */
    private void enabled(boolean isStart){
        setGridButton.setEnabled(!isStart);
        generateGridButton.setEnabled(!isStart);
        loadFileGridButton.setEnabled(!isStart);
        startButton.setEnabled(isStart);
    }

    /**
     * Добавление кнопок управления на экран
     * @param panel {JPanel}
     */
    private void addBtnForPanel(JPanel panel){
        setGridButton = new Button("Установить значения вручную");
        generateGridButton = new Button("Сгенирировать значения");
        loadFileGridButton = new Button("Загрузить значения из файла");
        startButton = new Button("Запустить игру");
        startButton.setEnabled(false);

        clearButton = new Button("Очистить");

        panel.add(setGridButton);
        panel.add(generateGridButton);
        panel.add(loadFileGridButton);
        panel.add(startButton);
        panel.add(clearButton);
    }
}
