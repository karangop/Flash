package cl.karangop.flash.views.main.drawer;

import android.content.Context;

import cl.karangop.flash.data.LocalPhoto;

/**
 * Created by karan_000 on 08-03-2017.
 */

public class PhotoValidation {
    private Context context;
    private PhotoCallback callback;

    public PhotoValidation(Context context, PhotoCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void checkLocal(){
        /*Operador ternario
        String oli= (1>4) ? "oli": "noli";*/
        String path= new LocalPhoto(context).getPath();
        if(path != null){
            callback.showPhoto(path);
        }
        else{
            callback.takePhoto();
        }
    }
}
