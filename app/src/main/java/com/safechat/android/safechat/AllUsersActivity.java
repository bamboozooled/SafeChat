package com.safechat.android.safechat;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AllUsersActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    final int RC_SIGN_IN = 1000;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<User> allUsers;
    ArrayList<String> userNames;
    AllUsersAdapter adapter;
    ListView listofUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        mAuth = FirebaseAuth.getInstance();

        allUsers = new ArrayList<>();
        userNames = new ArrayList<>();

        adapter = new AllUsersAdapter(this,allUsers);
        listofUsers = (ListView) findViewById(R.id.listofusers);
        listofUsers.setAdapter(adapter);

        readUsers();

        listofUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AllUsersActivity.this,ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("UserSelected",allUsers.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            firebaseUi();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.users_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_email:
                addEmail();
                return true;

            default:
                return true;

        }
    }

    public void firebaseUi()
    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
    }

    public void readUsers(){
        ChildEventListener uniListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                allUsers.add(user);
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference userReference = database.getReference("users");
        userReference.addChildEventListener(uniListener);
    }

    public void addEmail() {
        final Dialog dialog = new Dialog(AllUsersActivity.this);
        dialog.setContentView(R.layout.add_email_layout);
        final EditText emailView = (EditText)dialog.findViewById(R.id.email_view);
        Button addButton = (Button)dialog.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailView.getText().toString().trim();
                addEmail(email);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void addEmail(String email) {
        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("email", email);
        DatabaseReference userReference = database.getReference("emails").child(mAuth.getCurrentUser().getUid());
        userReference.setValue(emailMap);
    }
}
