package projects.my.maintest.db.infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import projects.my.maintest.db.dao.GenericDao;
import projects.my.maintest.db.models.BaseEntity;
import projects.my.maintest.db.models.ListItem;

/**
 * Контекст БД.
 */
public class DbContext extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DbContext.class.getSimpleName();
    public static final String DATABASE_NAME ="test.db";
    private static final int DATABASE_VERSION = 1;
    private static final Map<Class<? extends BaseEntity>, GenericDao<? extends BaseEntity>>
            daoMap = new HashMap<>();

    public DbContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Реализует создание БД в случае, если ее нет на устройстве.
     * @param db БД.
     * @param connectionSource Ресурс поделючения.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, ListItem.class);
        }
        catch (SQLException ex) {
            Log.e(TAG, "Ошибка создания БД " + DATABASE_NAME);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Реализует обновление БД (в случае, когда БД имеет отличную от текущей версию).
     * @param db БД.
     * @param connectionSource Ресурс поделючения.
     * @param oldVer Старая версия.
     * @param newVer Новая версия.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            // Никогда не выполняющийся код-заглушка для компиляции. В противном случае пришлось
            // бы комментировать сами блоки try-catch.
            if (false) throw new SQLException();
            // В данный момент версия БД не итерировалась, ничего обновлять не нужно.
            onCreate(db, connectionSource);
        }
        catch (SQLException e) {
            Log.e(TAG, "Ошибка обновления БД "+ DATABASE_NAME + " с версии " + oldVer);
            throw new RuntimeException(e);
        }
    }

    /**
     * Реализует создание объекта репозитория указанного типа.
     * @param cls Класс параметра.
     * @param <T> Тип модели БД.
     * @return Возвращает репозиторий запрошенного типа.
     */
    @SuppressWarnings("unchecked")
    public synchronized <T extends BaseEntity> GenericDao<T> getGenericDao(Class<T> cls) {
        GenericDao<T> dao = (GenericDao<T>) daoMap.get(cls);
        if (dao == null) {
            try {
                dao = new GenericDao<>(getConnectionSource(), cls);
                daoMap.put(cls, dao);
            }
            catch (SQLException e) {
                Log.e(TAG, "Ошибка создания Dao типа " + cls.getSimpleName());
                throw new RuntimeException(e);
            }
        }

        return dao;
    }

    @Override
    public void close(){
        super.close();
    }

    /**
     * Реализует очистку таблиц БД.
     */
    public void clearTables() {
        try {
            TableUtils.clearTable(getConnectionSource(), ListItem.class);
        }
        catch (SQLException ex) {
            Log.e(TAG, "Ошибка очистки таблиц БД.");
            throw new RuntimeException(ex);
        }
    }
}
