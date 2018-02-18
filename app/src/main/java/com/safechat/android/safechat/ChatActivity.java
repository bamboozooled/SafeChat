package com.safechat.android.safechat;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;

import java.net.URI;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage store = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef;
    StorageReference ref2;
    DatabaseReference mainDataBase;
    User selectedUser;
    UploadTask uploadTask;
    User currentUser;
    RecyclerView messages;
    EditText messageView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String room;
    ArrayList<Message> storage = new ArrayList<>();
    ImagePicker imagePicker;
    final int KEY = 9949;
    private final String TAG = ChatActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageView = (EditText) findViewById(R.id.TextInput);

        mAuth = FirebaseAuth.getInstance();
        storageRef = store.getReference();
        imagePicker = new ImagePicker(this, null, new OnImagePickedListener() {
            @Override
            public void onImagePicked(Uri imageUri) {
                Intent intent = new Intent(ChatActivity.this, SendMediaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("uri", imageUri.toString());
                intent.putExtras(bundle);
                startActivityForResult(intent, KEY);
            }

        });

        Bundle gettingUser = getIntent().getExtras();

        selectedUser = (User) gettingUser.getParcelable("UserSelected");
        currentUser = new User();

        FirebaseUser mUser = mAuth.getCurrentUser();
        currentUser.setEmail(mUser.getEmail());
        currentUser.setName(mUser.getDisplayName());
        currentUser.setUid(mUser.getUid());
        currentUser.setPhotoUrl(mUser.getPhotoUrl().toString());

        room = createRoomID();

        mainDataBase = database.getReference("rooms/"+room);

        messages = (RecyclerView) findViewById(R.id.ChatMain);


        mLayoutManager = new LinearLayoutManager(this);
        messages.setLayoutManager(mLayoutManager);


        mAdapter = new ChatMaster(storage);
        messages.setAdapter(mAdapter);
        checkRoom();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);
        if(requestCode == KEY) {
            if(resultCode == RESULT_OK) {
                sendImage(data.getData());
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_upload:
                uploadImage();
                return true;
            default:
                return true;

        }
    }

    public String createRoomID(){
        if (selectedUser.getUid().compareTo(mAuth.getCurrentUser().getUid()) < 0){
            return selectedUser.getUid()+mAuth.getCurrentUser().getUid();
        }
        else{
            return mAuth.getCurrentUser().getUid() + selectedUser.getUid();
        }
    }

    public void checkRoom(){
        ChildEventListener roomListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                storage.add(message);
                mAdapter.notifyDataSetChanged();
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

        mainDataBase.addChildEventListener(roomListener);
    }

    public void sendMessage(View view){
        String currentMessage = messageView.getText().toString();
        Message message = new Message(currentMessage, System.currentTimeMillis(), null,
                currentUser.getName());
        mainDataBase.push().setValue(message);
    }

    public void uploadImage() {
        imagePicker.choosePicture(true);
    }

    public void sendImage(Uri uri) {
        if(uri == null){
            Toast.makeText(this, "Uri is null", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, uri.toString());
        ref2 = storageRef.child(room).child(currentUser.getUid()+uri.getLastPathSegment());
        uploadTask = ref2.putFile(uri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String url = downloadUrl.toString();
                String path = storageRef.getPath();
                Media media = new Media(url, path, "image", true);
                Message message = new Message("", System.currentTimeMillis(), media, currentUser.getName());
                sendMessage(message);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.getMessage());
            }
        });
    }

    public void sendMessage(Message message) {
        mainDataBase.push().setValue(message);
    }
}
