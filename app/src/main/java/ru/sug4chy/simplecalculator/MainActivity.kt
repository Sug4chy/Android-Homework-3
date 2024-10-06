package ru.sug4chy.simplecalculator

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.sug4chy.simplecalculator.extensions.containsMathOperations
import ru.sug4chy.simplecalculator.extensions.findButtonById
import ru.sug4chy.simplecalculator.extensions.findTextViewById
import ru.sug4chy.simplecalculator.extensions.startsWithDigit
import ru.sug4chy.simplecalculator.utils.MathOperationsUtils

class MainActivity : AppCompatActivity() {

    private lateinit var outputTextView: TextView
    private var resetOutputOnNextButtonClick: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create()

        initRefsToViewElements()
        setOnClickListeners()
    }

    private fun initRefsToViewElements() {
        outputTextView = findTextViewById(R.id.result_txv)
    }

    private fun setOnClickListeners() {
        setNumberButtonsOnClickListeners()
        setOperationButtonsOnClickListeners()

        with(findButtonById(R.id.btn_dot)) {
            this.setOnClickListener {
                if (!outputTextView.text.contains(".")) {
                    outputTextView.append(this.text)
                }
            }
        }

        with(findButtonById(R.id.btn_result)) {
            this.setOnClickListener {
                outputTextView.text = try {
                    MathOperationsUtils.performCalculation(outputTextView.text.toString())
                        .toString().removeSuffix(".0")
                } catch (e: Throwable) {
                    e.message
                }
                this@MainActivity.resetOutputOnNextButtonClick = true
            }
        }
    }

    private fun setNumberButtonsOnClickListeners() {
        val numberButtonsIds = arrayOf(
            R.id.btn_0,
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6,
            R.id.btn_7,
            R.id.btn_8,
            R.id.btn_9
        )

        numberButtonsIds.forEach {
            with(findButtonById(it)) {
                this.setOnClickListener {
                    if (this@MainActivity.resetOutputOnNextButtonClick) {
                        outputTextView.text = this.text
                        this@MainActivity.resetOutputOnNextButtonClick = false
                    } else {
                        outputTextView.append(this.text)
                    }
                }
            }
        }
    }

    private fun setOperationButtonsOnClickListeners() {
        val operationButtonsIds = arrayOf(
            R.id.btn_sum,
            R.id.btn_sub,
            R.id.btn_mul,
            R.id.btn_div
        )

        operationButtonsIds.forEach {
            with(findButtonById(it)) {
                this.setOnClickListener {
                    if (!outputTextView.text.containsMathOperations()) {
                        outputTextView.append(" ${this.text} ")
                    }

                    if (!outputTextView.text.startsWithDigit()) {
                        outputTextView.let { txv: TextView ->
                            txv.text = txv.text.removeRange(0..<txv.text.length)
                        }
                    }

                    this@MainActivity.resetOutputOnNextButtonClick = false
                }
            }
        }
    }

    private fun create() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}