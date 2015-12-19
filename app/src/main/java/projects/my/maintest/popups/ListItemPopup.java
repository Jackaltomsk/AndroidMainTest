package projects.my.maintest.popups;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import projects.my.maintest.R;
import projects.my.maintest.db.models.ListItem;

/**
 * Всплывающее окно для работы с моделью ListItem.
 */
public class ListItemPopup extends android.widget.PopupWindow {
    View popupView;
    Button btnDecline;
    Button btnAccept;
    EditText editText;
    ListItem model;

    public ListItemPopup(Context context, ListItem model) {
        super(context);
        popupView = LayoutInflater.from(context).inflate(R.layout.popup_item_fragment_list, null);
        setContentView(popupView);
        btnDecline = (Button) popupView.findViewById(R.id.btnDeclinePopupItem);
        btnAccept = (Button) popupView.findViewById(R.id.btnAcceptPopupItem);
        editText = (EditText) popupView.findViewById(R.id.popupItemText);
        createListeners();
        editText.setText(model.getText());
    }

    private void createListeners() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setText(editText.getText().toString());
                ListItemPopup.this.dismiss();
            }
        });
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItemPopup.this.dismiss();
            }
        });
        popupView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    if (model.getText().equals(editText.getText().toString())) {
                        return btnDecline.callOnClick();
                    }
                    else {
                        // зовем алерт
                        return false;
                    }
                }
                return false;
            }
        });
    }
}