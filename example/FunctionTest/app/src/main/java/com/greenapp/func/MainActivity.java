package com.greenapp.func;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private EditText et_a,et_b,et_c;
    private FunctionView func_view;
    private TextView tv_view;
    private int a=1,b=1,c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        et_a= (EditText) findViewById(R.id.et_a);
        et_b= (EditText) findViewById(R.id.et_b);
        et_c= (EditText) findViewById(R.id.et_c);
        tv_view= (TextView) findViewById(R.id.tv_view);

        func_view= (FunctionView) findViewById(R.id.sv_func);
        func_view.initView(20, 20, new FunctionFormula() {
            @Override
            public float getFunctionValue(float x) {
                return a*x*x+b*x+c;
            }
        });

    }

    public void flush(View v){
        a=Integer.parseInt(et_a.getText().toString());
        b=Integer.parseInt(et_b.getText().toString());
        c=Integer.parseInt(et_c.getText().toString());
        tv_view.setText("y="+a+"x²+"+b+"x+"+c);
        updateFunction();
    }

    private void updateFunction(){
        func_view.changeFunction(new FunctionFormula() {
            @Override
            public float getFunctionValue(float x) {
                return a*x*x+b*x+c;
            }
        });
    }

}
