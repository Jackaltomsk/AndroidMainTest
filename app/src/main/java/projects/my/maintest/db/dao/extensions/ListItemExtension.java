package projects.my.maintest.db.dao.extensions;

import android.util.Log;

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
     * Реализует получение осхраненных значений таймера.
     * @return Возвращает массив значений времени.
     */
    public ListItem[] getSavedItems() {
        try {
            List<ListItem> items = dao.queryBuilder().query();
            return items.toArray(new ListItem[items.size()]);
        }
        catch (SQLException e) {
            Log.e(TAG, "Ошибка получения сохраненных таймеров");
            return null;
        }
    }
}
