package aspu.life.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileGridStorage implements GridStorage {
    /**
     * Директория, в которой хранится файл игры
     */
    String directory;

    public FileGridStorage(String directory) {
        this.directory = directory;
    }


    /**
     * Метод чтения файла
     * @param gridName - имя файла {String}
     * @return возвращает сетку с начальными значениями для игры
     */
    @Override
    public Grid loadGrid(String gridName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory + gridName));
            Grid grid;
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            // построчно считываем результат в объект StringBuilder
            while ((inputLine = br.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            grid = loadJson(stringBuilder.toString());
            br.close();
            return grid;
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла!");
        }
        return null;
    }

    /**
     * Парсинг json файла
     * @param str - строка json из файла {String}
     * @return возвращает сетку с начальными значениями для игры
     */
    private Grid loadJson(String str) {
        // конвертируем строку с Json в JSONObject для дальнейшего его парсинга
        JSONObject jsonObject = new JSONObject(str);
        int width = (Integer) jsonObject.get("width");
        int height = (Integer) jsonObject.get("height");
        Grid grid = new Grid(width, height);
        // получаем массив клеток
        JSONArray cells = (JSONArray) jsonObject.get("cells");
        for (int i = 0; i < width; i++){
            JSONArray row = (JSONArray) cells.get(i);
            for (int j = 0; j < height; j++) {
                CellInterface newCell = new Cell((Boolean) row.get(j));
                grid.setCellAt(i, j, newCell);
            }
        }

        return grid;
    }
}
