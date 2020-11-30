package com.example.homeworkcorrect;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class InfoDialog extends Dialog {

    private InfoDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private View mLayout;

        private ImageView mIcon;
        private TextView mTitle;
        private TextView mMessage;
        private Button mButton;

        private View.OnClickListener mButtonClickListener;

        private InfoDialog mDialog;

        public Builder(Context context,int layoutRes) {
            mDialog = new InfoDialog(context, R.style.Theme_AppCompat_Dialog);
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //加载布局文件
            mLayout = inflater.inflate(layoutRes, null, false);
            //添加布局文件到 Dialog
            mDialog.addContentView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            mIcon = mLayout.findViewById(R.id.dialog_icon);
            mTitle = mLayout.findViewById(R.id.dialog_title);
            mMessage = mLayout.findViewById(R.id.dialog_message);
            mButton = mLayout.findViewById(R.id.dialog_button);
        }

        /**
         * 通过 ID 设置 Dialog 图标
         */
        public Builder setIcon(int resId) {
            mIcon.setImageResource(resId);
            return this;
        }

        /**
         * 用 Bitmap 作为 Dialog 图标
         */
        public Builder setIcon(Bitmap bitmap) {
            mIcon.setImageBitmap(bitmap);
            return this;
        }

        /**
         * 设置 Dialog 标题
         */
        public Builder setTitle(@NonNull String title) {
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
            return this;
        }

        /**
         * 设置 Message
         */
        public Builder setMessage(@NonNull String message) {
            mMessage.setText(message);
            return this;
        }

        /**
         * 设置按钮文字和监听
         */
        public Builder setButton(@NonNull String text, View.OnClickListener listener) {
            mButton.setText(text);
            mButtonClickListener = listener;
            return this;
        }

        public InfoDialog create() {
            mButton.setOnClickListener(view -> {
                mDialog.dismiss();
                mButtonClickListener.onClick(view);
            });
            mDialog.setContentView(mLayout);
            mDialog.setCancelable(true);                //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
            return mDialog;
        }
    }
}
