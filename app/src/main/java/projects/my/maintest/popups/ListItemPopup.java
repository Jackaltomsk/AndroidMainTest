package projects.my.maintest.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import projects.my.maintest.R;
import projects.my.maintest.common.ActivityUtils;
import projects.my.maintest.db.models.ListItem;

/**
 * Всплывающее окно для работы с моделью ListItem.
 */
public class ListItemPopup extends android.widget.PopupWindow {
    Context ctx;
    View popupView;
    Button btnDecline;
    Button btnAccept;
    EditText editText;
    ListItem model;

    /**
     * Флаг закрытия окна по кнопке. Если не выставлен,
     * окно закрывается по кнопке "назад" устройства.
     */
    boolean isBtnDismiss;
    OnModelChangeListener modelChangeListener;

    /**
     * Интерфейс события изменения модели данных внутри окна.
     */
    public interface OnModelChangeListener {
        void onModelChange();
    }

    public void setOnModelChangeListener(OnModelChangeListener modelChangeListener) {
        this.modelChangeListener = modelChangeListener;
    }

    @SuppressWarnings("deprecation")
    public ListItemPopup(Context context, ListItem model) throws NullPointerException {
        super(LayoutInflater.from(context).inflate(R.layout.popup_item_fragment_list, null),
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (model == null) throw new NullPointerException("Не передана модель.");
        this.model = model;
        ctx = context;

        popupView = this.getContentView();
        btnDecline = (Button) popupView.findViewById(R.id.btnDeclinePopupItem);
        btnAccept = (Button) popupView.findViewById(R.id.btnAcceptPopupItem);
        editText = (EditText) popupView.findViewById(R.id.popupItemText);

        createListeners();
        editText.setText(model.getText());
        this.setBackgroundDrawable(new ColorDrawable(ctx.getResources()
                .getColor(R.color.background_material)));
        this.setFocusable(true);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void createListeners() {
        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();
                    if (x < 0 || x >= popupView.getWidth() || y < 0 || y >= popupView.getHeight())
                        return true;
                }
                return false;
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!isBtnDismiss) {
                    final String text = editText.getText().toString();
                    String modelText = model.getText();
                    if ((modelText == null && text.equals("")) ||
                            (modelText != null && modelText.equals(text))) return;

                    // зовем алерт
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle(R.string.popup_dialog_title);
                    builder.setMessage(R.string.popup_dialog_message);
                    builder.setPositiveButton(R.string.popup_dialog_save_btn,
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            model.setText(text);
                            if (modelChangeListener != null) modelChangeListener.onModelChange();
                        }
                    });
                    builder.setNegativeButton(R.string.popup_dialog_cancel_btn, null);
                    builder.show();
                }
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                isBtnDismiss = true;
                ListItemPopup.this.dismiss();
                model.setText(text);
                if (modelChangeListener != null) modelChangeListener.onModelChange();
            }
        });
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBtnDismiss = true;
                ListItemPopup.this.dismiss();
            }
        });
    }

    public void show(View parentView) {
        this.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }
}