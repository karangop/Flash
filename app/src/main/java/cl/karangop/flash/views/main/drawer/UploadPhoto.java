package cl.karangop.flash.views.main.drawer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.karangop.flash.data.CurrentUser;
import cl.karangop.flash.data.LocalPhoto;
import cl.karangop.flash.data.Nodes;
import cl.karangop.flash.models.User;

/**
 * Created by karan_000 on 06-03-2017.
 */

public class UploadPhoto {

    private Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void toFirebase(String path){
        final CurrentUser currentUser= new CurrentUser();

        String email= currentUser.email();

        String folder= currentUser.sanitizeEmail(currentUser.email()) + "/";
        String photoName= "avatar.jpeg";
        String baseUrl= "gs://flash-7eaa3.appspot.com/avatars/";
        String refUrl= baseUrl + folder + photoName;
        StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);

        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String[] fullurl= taskSnapshot.getDownloadUrl().toString().split("&token");
                String url= fullurl[0];

                new LocalPhoto(context).savePath(url);

                String uid= currentUser.userId();

                User user= new User();
                user.setName(currentUser.name());
                user.setPhoto(url);
                user.setUid(currentUser.userId());
                user.setEmail(currentUser.email());


                Log.d("URL",url);

//                Subimos el usaurio a Firebase
                DatabaseReference reference= new Nodes().userByUid(uid);
                reference.setValue(user);
            }
        });
    }

}
