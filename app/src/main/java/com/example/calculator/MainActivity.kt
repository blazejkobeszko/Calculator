package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric : Boolean=false
    var lastComma : Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput=findViewById(R.id.tvInput)
    }

    fun onDigit(view:View){
        tvInput?.append((view as Button).text)
        lastNumeric=true
    }

    fun onBack(view: View){
        tvInput?.text?.let {
            var tvValue = tvInput?.text.toString()
            if(tvValue.isNotEmpty()) {
                tvValue = tvValue.substring(0, tvValue.length - 1)
                tvInput?.text = tvValue
            }
        }
    }

    fun onClear(view: View){
        tvInput?.text=""
        lastNumeric=false
        lastComma=false
    }

    fun onComma(view: View){
        if(lastNumeric && !lastComma){
            tvInput?.append(".")
            lastComma=true
            lastNumeric=false
        }

    }

    fun onOperator(view: View){
        tvInput?.text?.let{

            if (lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastComma=false
                lastNumeric=false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue=tvInput?.text.toString()
            var prefix= ""
            try{
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }

                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    var result = one.toDouble() - two.toDouble()
                    tvInput?.text = removeZeroAfterComma(result.toString())
                }else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    var result = one.toDouble() + two.toDouble()
                    tvInput?.text = removeZeroAfterComma(result.toString())
                }else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    var result = one.toDouble() * two.toDouble()
                    tvInput?.text = removeZeroAfterComma(result.toString())
                }else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    var result = one.toDouble() / two.toDouble()
                    tvInput?.text = removeZeroAfterComma(result.toString())
                }


            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterComma(result: String) : String{
        var value= result
        if(result.contains(".0"))
            value=result.substring(0,result.length-2)

        return value
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{ value.contains("/")
                    ||value.contains("*")
                    ||value.contains("+")
                    ||value.contains("-")
        }
    }

}