package projects.my.maintest.activities;

/**
 * Интерфейс для работы со слушателями события нажатия кнопки "назад".
 */
@SuppressWarnings("WeakerAccess")
public interface BackPressedListeners {
    void addBackPressedListener(BackPressedListener listener);
    void removeBackPressedListener(BackPressedListener listener);
}

