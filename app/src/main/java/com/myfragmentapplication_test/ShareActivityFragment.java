package com.myfragmentapplication_test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.UserDAO;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShareActivityFragment extends Fragment {

    public static final String TAG = ShareActivityFragment.class.getSimpleName();
    OnShareItemClickedListener shareItemClickedListener;
    @Bind(R.id.textShareMessage)
    TextView txtShareMessage;
    private UserDAO userDAO;


    public ShareActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnShareItemClickedListener){
            shareItemClickedListener = (OnShareItemClickedListener) context;
        }else{
            throw new RuntimeException(context.toString()+" must implement the OnShareItemClickedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtShareMessage.setText(getArguments().getString(ShareActivity.SHARE_MESSAGE));
    }

    @OnClick(R.id.textShareMessage)
    public void showToastMessage(){

        shareItemClickedListener.onShareItemClicked("Send me message to Activity");

      /*  try {
            String value = "Here is the world";
            FileOutputStream fileOutputStream = getActivity().openFileOutput("myFile",Context.MODE_PRIVATE);
            fileOutputStream.write(value.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public interface OnShareItemClickedListener {
        void onShareItemClicked(String message);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
