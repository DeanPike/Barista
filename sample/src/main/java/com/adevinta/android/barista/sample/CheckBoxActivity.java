package com.adevinta.android.barista.sample;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CheckBoxActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_checkbox);

    TextView selectedItem = (TextView) findViewById(R.id.selected_item);
    setListener(selectedItem, R.id.first_item);
    setListener(selectedItem, R.id.second_item);
  }

  private void setListener(TextView selectedItem, int checkBoxId) {
    ((CheckBox) findViewById(checkBoxId)).setOnCheckedChangeListener(new CheckListener(selectedItem));
  }

  static class CheckListener implements CheckBox.OnCheckedChangeListener {

    private final TextView selectedItem;

    CheckListener(TextView selectedItem) {
      this.selectedItem = selectedItem;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
      selectedItem.setText("" + compoundButton.getId());
    }
  }
}
