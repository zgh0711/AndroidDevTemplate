package com.zgh.appdevtemplate.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgh.appdevtemplate.R;


/**
 * 自定义搜索框，延迟一秒自动搜索
 */

public class SearchView extends LinearLayout implements View.OnClickListener {
    private EditText    et_search;//输入框
    private ImageButton btn_clear,btn_search;//删除键
    private Context          mContext;//上下文对象
    private OnSearchListener mListener;//搜索回调接口

    //使用handler 来达到延迟自动搜索
    private static final int MSG_SEARCH = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //搜索请求
            if (mListener != null) {
                mListener.onSearch(et_search.getText().toString());
            }
        }
    };

    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setOnSearchListener(OnSearchListener listener) {
        mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_view, this);
        initViews();
    }

    private void initViews() {
        et_search = (EditText) findViewById(R.id.et_search);
        btn_clear = (ImageButton) findViewById(R.id.iBtn_search_clear);
        btn_search = (ImageButton) findViewById(R.id.iBtn_search);
        et_search.clearFocus();

        btn_clear.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        et_search.addTextChangedListener(new EditChangedListener());
        et_search.setOnClickListener(this);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    notifyStartSearching(et_search.getText().toString());
                }
                return true;
            }
        });
    }

    /**
     * 通知监听者 进行搜索操作
     * @param text
     */
    private void notifyStartSearching(String text){
        if (mListener != null && !text.isEmpty()) {
            mListener.onSearch(text);
        }
        hideKeyBoard();
    }

    private void hideKeyBoard() {
        //隐藏软键盘
        InputMethodManager
                imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                btn_clear.setVisibility(VISIBLE);
            } else {
                btn_clear.setVisibility(GONE);
                //当搜索框内的数据被清空时，触发重置
                if (mListener != null) {
                    mListener.onReset();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //文字变动 ， 有未发出的搜索请求，应取消
            if(mHandler.hasMessages(MSG_SEARCH)){
                mHandler.removeMessages(MSG_SEARCH);
            }

            //如果在规定时间内文字未变动，则自动开始搜索
            mHandler.sendEmptyMessageDelayed(MSG_SEARCH,1000);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iBtn_search_clear) {
            et_search.setText("");
            btn_clear.setVisibility(GONE);
            //点击清除，触发重置方法，在调用处将关键字清空并搜索
            if (mListener != null) {
                mListener.onReset();
            }

        } else if (i == R.id.iBtn_search) {
            hideKeyBoard();
            notifyStartSearching(et_search.getText().toString());

        }
    }

    /**
     * 设置搜索框内的提示信息
     * @param hint
     */
    public void setHint(String hint) {
        et_search.setHint(hint);
    }

    /**
     * search view回调方法
     */
    public interface OnSearchListener {

//        /**
//         * 更新自动补全内容
//         *
//         * @param text 传入补全后的文本
//         */
//        void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param keyWord 传入输入框的文本
         */
        void onSearch(String keyWord);

        /**
         * 重置方法，在调用处将关键字清空并搜索
         */
        void onReset();

        //        /**
        //         * 提示列表项点击时回调方法 (提示/自动补全)
        //         */
        //        void onTipsItemClick(String text);
    }
}

