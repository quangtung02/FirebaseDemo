package rlm.sfv.org.com.firebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import rlm.sfv.org.com.firebasedemo.model.Person;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getName();

    private Firebase mFireBase;

    private EditText mEdtName;
    private EditText mEdtAddress;
    private Button mBtnSave;
    private TextView mTxtName;
    private TextView mTxtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mEdtName = (EditText) findViewById(R.id.edit_text_name);
        mEdtAddress = (EditText) findViewById(R.id.edit_text_address);
        mBtnSave = (Button) findViewById(R.id.button_save);
        mTxtName = (TextView) findViewById(R.id.text_view_name);
        mTxtAddress = (TextView) findViewById(R.id.text_view_address);
        mBtnSave.setOnClickListener(this);

        // Create a fire base object

    }

    @Override
    public void onClick(View v) {
        mFireBase = new Firebase(Constant.FIRE_BASE_URL);
        // Create an object Person
        if (!TextUtils.isEmpty(getName()) && !TextUtils.isEmpty(getAddress())) {
            Person person = new Person();
            person.setName(getName());
            person.setAddress(getAddress());

            // Store the values to Fire Base
            mFireBase.setValue(person);
        } else {
            Toast.makeText(this, "Name and Address empty", Toast.LENGTH_LONG).show();
        }

        // Value event for data real time update
        mFireBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // getting data from Snapshot
                    Person person = dataSnapshot.getValue(Person.class);
                    mTxtName.setText(person.getName().toString());
                    mTxtAddress.setText(person.getAddress().toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(TAG, "Fire base error:" + firebaseError.getMessage());
            }
        });
    }

    public String getName() {
        return mEdtName.getText().toString().trim();
    }

    public String getAddress() {
        return mEdtAddress.getText().toString().trim();
    }
}
