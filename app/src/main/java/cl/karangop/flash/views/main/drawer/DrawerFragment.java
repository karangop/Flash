package cl.karangop.flash.views.main.drawer;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frosquivel.magicalcamera.Functionallities.PermissionGranted;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import cl.karangop.flash.R;

public class DrawerFragment extends Fragment implements PhotoCallback{

    private PermissionGranted permissionGranted = new PermissionGranted(getActivity());
    private static final int RESIZE_PHOTO_PIXELS_PERCENTAGE = 30;
    private MagicalCamera magicalCamera;
    private CircularImageView avatar;

    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avatar= (CircularImageView) view.findViewById(R.id.avatarSi);

        new PhotoValidation(getContext(),this).checkLocal();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionGranted.permissionGrant(requestCode,permissions,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode,resultCode,data);

        Bitmap bitmap= magicalCamera.getPhoto();
        String path = magicalCamera.savePhotoInMemoryDevice(bitmap,"avatar","flash", MagicalCamera.JPEG, false);



        if(path != null){
            path = "file://" + path;
            showPhoto(path);
            new UploadPhoto(getContext()).toFirebase(path);
            Toast.makeText(getContext(), "The photo is save in device, please check this path: " + path, Toast.LENGTH_SHORT).show();
        }else{
            takePhoto();
            Toast.makeText(getContext(), "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void takePhoto() {
        AlertDialog.Builder photoDialog= new AlertDialog.Builder(getContext());
        photoDialog.setTitle("Tomate una selfie");
        photoDialog.setMessage("Para completar tu perfil debes tomarte tu primer selfie o actualizar la que ya tienes");
        photoDialog.setPositiveButton("selfie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                magicalCamera= new MagicalCamera(getActivity(),RESIZE_PHOTO_PIXELS_PERCENTAGE,permissionGranted);
                if (magicalCamera.takeFragmentPhoto()){
                    startActivityForResult(magicalCamera.getIntentFragment(),MagicalCamera.TAKE_PHOTO);
                }
            }
        });

        photoDialog.setCancelable(false);
        photoDialog.show();

        magicalCamera= new MagicalCamera(getActivity(),RESIZE_PHOTO_PIXELS_PERCENTAGE, permissionGranted);
        if(magicalCamera.takeFragmentPhoto()) {
            startActivityForResult(magicalCamera.getIntentFragment(), MagicalCamera.TAKE_PHOTO);
        }
    }

    @Override
    public void showPhoto(String path) {
        Picasso.with(getContext()).load(path).centerCrop().fit().into(avatar);

    }
}
