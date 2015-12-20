package projects.my.maintest.db.dao.extensions;

import android.util.Log;

import com.j256.ormlite.dao.CloseableIterator;

import java.sql.SQLException;
import java.util.List;

import projects.my.maintest.db.dao.GenericDao;
import projects.my.maintest.db.models.ListItem;

/**
 * Методы работы с отсечками.
 */
public class ListItemExtension extends BaseExtension<ListItem> {

    private static final String TAG = ListItemExtension.class.getSimpleName();

    public ListItemExtension(GenericDao<ListItem> dao) throws NullPointerException {
        super(dao);
    }

    /**
     * Реализует получение всех значений списка.
     * @return Возвращает массив значений списка.
     */
    public ListItem[] getSavedItems() {
        try {
            List<ListItem> items = dao.queryBuilder().query();
            return items.toArray(new ListItem[items.size()]);
        }
        catch (SQLException e) {
            Log.e(TAG, "Ошибка получения сохраненных значений.");
            return null;
        }
    }

    /**
     * Реализует получение общее количество записей значений в БД.
     * @return Возвращает количество записей.
     */
    public int getCount() {
        try {
            return (int) dao.countOf();
        }
        catch (SQLException e) {
            Log.e(TAG, "Ошибка получения общего количества записей значений.");
            return -1;
        }
    }

    /**
     * Реализует получение значения по указанному порядковому номеру.
     * @param position Порядковый номер.
     * @return Возвращает значение из БД.
     */
    public ListItem getItemAt(int position) {
        try {
            CloseableIterator<ListItem> iterator = dao.iterator();
            ListItem item = iterator.moveRelative(position);
            iterator.close();
            return item;
        }
        catch (SQLException e) {
            Log.e(TAG, "Ошибка получения значения по порядковому номеру " +
                    String.valueOf(position));
            return null;
        }
    }
}
