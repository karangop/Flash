package cl.karangop.flash.views.login;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by karan_000 on 01-03-2017.
 */

public class LoginValidation {

    private LoginCallback callback;

    public LoginValidation(LoginCallback callback) {
        this.callback = callback;
    }

    public void init(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            callback.logged();

        }else{
            callback.sign();

        }
    }
}
