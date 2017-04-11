package cl.karangop.flash.views.main.finder;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.karangop.flash.data.CurrentUser;
import cl.karangop.flash.data.LocalPhoto;
import cl.karangop.flash.data.Nodes;
import cl.karangop.flash.models.Chat;
import cl.karangop.flash.models.User;

/**
 * Created by karan_000 on 10-03-2017.
 */

public class CreateChatValidation {

    private FinderCallback callback;
    private Context context;

    public CreateChatValidation(FinderCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    public void init(String email){
        if (email.trim().length() > 0){
            if (email.contains("@")){
                if (!email.equals(new CurrentUser().email())){
                    searchUser(email);

                }else{
                    callback.error("Chat contigo mismo");
                }
            }else{
                callback.error("mail no valido");
            }
        }else{
            callback.error("escriba un mail");
        }
    }

    private void searchUser(String email) {
        new Nodes().users().orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User user = dataSnapshot.getValue(User.class);
                    Log.d("USER", user.getName());
//                    callback.success();
                    createChats(user);
                } else {
                    callback.notFound();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    private void createChats(User other){
        CurrentUser currentUser= new CurrentUser();
        String currentEmail= currentUser.email();
        String otherEmail= other.getEmail();
        String currentPhoto= new LocalPhoto(context).getPath();
        String id= currentUser.sanitizeEmail(currentEmail) + " : " + currentUser.sanitizeEmail(otherEmail);

        Chat currentChat= new Chat();
        currentChat.setId(id);
        currentChat.setReceiver(otherEmail);
        currentChat.setPhoto(other.getPhoto());
        currentChat.setNotification(true);

        Chat otherChat= new Chat();
        otherChat.setId(id);
        otherChat.setReceiver(currentEmail);
        otherChat.setPhoto(currentPhoto);
        otherChat.setNotification(true);


        new Nodes().userChats(currentUser.userId()).child(id).setValue(currentChat);
        new Nodes().userChats(other.getUid()).child(id).setValue(otherChat);

        callback.success();


    }

}
