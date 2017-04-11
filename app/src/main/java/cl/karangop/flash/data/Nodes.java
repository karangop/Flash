package cl.karangop.flash.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by karan_000 on 06-03-2017.
 */

public class Nodes {

    private DatabaseReference root(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference users(){
        return root().child("users");
    }

    public DatabaseReference userByUid(String uid){
        return users().child(uid);

    }

    public DatabaseReference userChats(String uid){
        return root().child("chats").child(uid);
    }

    public DatabaseReference messages(String chatId){
        return root().child("messages").child(chatId);

    }

}
