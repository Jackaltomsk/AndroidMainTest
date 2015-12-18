package projects.my.maintest.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Элемент списка.
 */
@DatabaseTable
public class ListItem extends BaseEntity {
    @DatabaseField
    private String text;
    @DatabaseField(canBeNull = false)
    private boolean isChecked;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}