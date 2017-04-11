package cl.karangop.flash.views.chat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import cl.karangop.flash.data.CurrentUser;
import cl.karangop.flash.data.Nodes;

/**
 * Created by karan_000 on 15-03-2017.
 */

public class UpdateChat {

    public void lastSeen(String chatId){
        String uid = new CurrentUser().userId();
        DatabaseReference reference = new Nodes().userChats(uid).child(chatId);

        reference.child("notification").setValue(false);
        reference.child("timestamp").setValue(ServerValue.TIMESTAMP);
    }
}
