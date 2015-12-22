package projects.my.maintest.db.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * Абстрактный родитель сущностей БД.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseEntity {
    public final static String ID_FIELD = "id";

    @DatabaseField(columnName = ID_FIELD, generatedId = true)
    protected int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id) + ", " + this.getClass().getSimpleName();
    }
}
