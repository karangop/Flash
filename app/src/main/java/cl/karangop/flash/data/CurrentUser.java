package cl.karangop.flash.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by karan_000 on 06-03-2017.
 */

public class CurrentUser {

    public FirebaseUser get(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public String email(){
        return get().getEmail();
    }

    public String sanitizeEmail(String email){
        return email.replace("@","AT").replace(".","DOT");
    }

    public String name(){
        return get().getDisplayName();
    }

    public String userId() {
        return get().getUid();
    }
}
