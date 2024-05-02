package grechushnikova.natali

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private var oneNumber: TextView? = null
    private var twoNumber: TextView? = null
    private var oper: TextView? = null
    private var result: EditText? = null
    private var checkButton: Button? = null
    private var startButton: Button? = null
    private var rightAnswer: TextView? = null
    private var wrongAnswer: TextView? = null
    private var procentRight: TextView? = null
    private var allCount: TextView? = null
    private var color: LinearLayout? = null
    private var rightAnswersCount = 0
    private var wrongAnswersCount = 0
    private var totalAnswersCount = 0

    private var colorValue = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneNumber = findViewById(R.id.one_number)
        twoNumber = findViewById(R.id.two_number)
        oper = findViewById(R.id.oper)
        result = findViewById(R.id.result)
        checkButton = findViewById(R.id.check)
        startButton = findViewById(R.id.start)
        rightAnswer = findViewById(R.id.right)
        wrongAnswer = findViewById(R.id.wrong)
        procentRight = findViewById(R.id.procent)
        allCount = findViewById(R.id.all_count)
        color = findViewById(R.id.color)

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        } else {
            generateRandom()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("correctAnswers", rightAnswersCount)
        outState.putInt("incorrectAnswers", wrongAnswersCount)
        outState.putInt("totalAnswers", totalAnswersCount)
        outState.putInt("colorValue", colorValue)
        outState.putInt("num1", oneNumber?.text.toString().toInt())
        outState.putInt("num2", twoNumber?.text.toString().toInt())
        outState.putString("operator", oper?.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        rightAnswersCount = savedInstanceState.getInt("correctAnswers")
        wrongAnswersCount = savedInstanceState.getInt("incorrectAnswers")
        totalAnswersCount = savedInstanceState.getInt("totalAnswers")
        colorValue = savedInstanceState.getInt("colorValue")
        val savedNum1 = savedInstanceState.getInt("num1")
        val savedNum2 = savedInstanceState.getInt("num2")
        val savedOperator = savedInstanceState.getString("operator")
        oneNumber?.text = savedNum1.toString()
        twoNumber?.text = savedNum2.toString()
        oper?.text = savedOperator
        rightAnswer?.text = rightAnswersCount.toString()
        wrongAnswer?.text = wrongAnswersCount.toString()
        allCount?.text = totalAnswersCount.toString()
        color?.setBackgroundColor(colorValue)
        procentRight?.text = if (totalAnswersCount > 0) {
            String.format("%.2f",(rightAnswersCount.toDouble() / totalAnswersCount.toDouble())*100 ) + "%"
        } else {
            "0%"
        }

    }

    fun onStartButtonClick(view: View) {
        generateRandom()
        checkButton?.isEnabled = true
        startButton?.isEnabled = false
        result?.isEnabled = true
        colorValue = Color.WHITE
        color?.setBackgroundColor(colorValue)
        result?.setText("")

    }

    fun onCheckButtonClick(view: View) {
        checkAnswer()
        checkButton?.isEnabled = false
        startButton?.isEnabled = true
        result?.isEnabled = false
        result?.setText("")
    }

    fun generateRandom() {
        val num1 = Random.nextInt(10, 100)
        val num2 = Random.nextInt(10, 100)
        var operator: String
        do {
            operator = when (Random.nextInt(0, 4)) {
                0 -> "+"
                1 -> "-"
                2 -> "*"
                3 -> "/"
                else -> "error"
            }
        } while (operator == "/" && num1 % num2 != 0)

        oneNumber?.text = num1.toString()
        twoNumber?.text = num2.toString()
        oper?.text = operator
    }

    fun checkAnswer() {
        val userResult = result?.text.toString().toInt()

        val correctResult = when (oper?.text) {
            "+" -> oneNumber?.text.toString().toInt() + twoNumber?.text.toString().toInt()
            "-" -> oneNumber?.text.toString().toInt() - twoNumber?.text.toString().toInt()
            "*" -> oneNumber?.text.toString().toInt() * twoNumber?.text.toString().toInt()
            "/" -> oneNumber?.text.toString().toInt() / twoNumber?.text.toString().toInt()
            else -> 0
        }

        if (userResult == correctResult) {
            rightAnswersCount +=1
            rightAnswer?.text = rightAnswersCount.toString()
            colorValue = Color.GREEN
            color?.setBackgroundColor(colorValue)

        } else {
            wrongAnswersCount +=1
            wrongAnswer?.text = wrongAnswersCount.toString()
            colorValue = Color.RED
            color?.setBackgroundColor(colorValue)
        }
        totalAnswersCount +=1
        allCount?.text = totalAnswersCount.toString()
        procentRight?.text = if (totalAnswersCount > 0) {
            String.format("%.2f",(rightAnswersCount.toDouble() / totalAnswersCount.toDouble())*100 ) + "%"
        } else {
            "0%"
        }
    }
    private fun restoreInstanceState(savedInstanceState: Bundle) {
        rightAnswersCount = savedInstanceState.getInt("correctAnswers")
        wrongAnswersCount = savedInstanceState.getInt("incorrectAnswers")
        totalAnswersCount = savedInstanceState.getInt("totalAnswers")
        colorValue = savedInstanceState.getInt("colorValue")
    }
}

