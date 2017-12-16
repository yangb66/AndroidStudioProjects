package com.yangb66.birthday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {

    private EditText name, birthday, gift;
    private Button add;
    mySQLOH sqloh;

    void init(){
        name = (EditText)findViewById(R.id.nameText);
        birthday = (EditText)findViewById(R.id.birthdayText);
        gift = (EditText)findViewById(R.id.giftText);
        add = (Button)findViewById(R.id.addButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        init();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdd();
            }
        });
    }

    void setAdd(){
        String str = name.getText().toString();
        int cnt = 0;
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) == ' ') cnt = cnt + 1;
        }

        if(str.length() == 0 || cnt == str.length()){
            Toast.makeText(this, "名字为空，请完善", Toast.LENGTH_SHORT).show();
        }
        else {
            sqloh = new mySQLOH(getApplication());
            if(sqloh.query(name.getText().toString())){
                Toast.makeText(this, "名字重复，请检查", Toast.LENGTH_SHORT).show();
            }
            else {
                Member member = new Member(name.getText().toString(), birthday.getText().toString(), gift.getText().toString());
                sqloh.insert(member.name, member.birthday, member.gift);

                Intent intent = new Intent();
                intent.putExtra("name", member.name).putExtra("birthday", member.birthday).putExtra("gift", member.gift);
                int resultCode = 1;
                this.setResult(resultCode, intent);
                finish();
            }
        }
    }
}
