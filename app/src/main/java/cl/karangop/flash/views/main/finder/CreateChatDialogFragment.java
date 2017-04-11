package cl.karangop.flash.views.main.finder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import cl.karangop.flash.R;

/**
 * Created by karan_000 on 08-03-2017.
 */

public class CreateChatDialogFragment extends DialogFragment implements FinderCallback {

    private LinearLayout wrapper;
    private SpinKitView loading;
    private EditText editText;

    public static CreateChatDialogFragment newInstance(){
        return new CreateChatDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog= new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_createchat,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wrapper= (LinearLayout) view.findViewById(R.id.wrapperLl);
        loading = (SpinKitView) view.findViewById(R.id.searchSpkv);
        editText = (EditText) view.findViewById(R.id.emailEt);

        ImageButton button = (ImageButton) view.findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString();
                editText.setError(null);
                getDialog().setCancelable(false);
                wrapper.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);

                // TODO: 08-03-2017 call the presenter
                new CreateChatValidation(CreateChatDialogFragment.this, getContext()).init(email);

            }
        });
    }

    private void restoreViews(){
        wrapper.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        getDialog().setCancelable(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void success() {
        getDialog().dismiss();
    }

    @Override
    public void error(String error) {
        restoreViews();
        editText.setError(error);
    }

    @Override
    public void notFound() {
        restoreViews();
        Toast.makeText(getContext(), "No hallado", Toast.LENGTH_SHORT).show();
        // TODO: 08-03-2017 replace this with simple dialog dismis and intent to share app url

    }
}
