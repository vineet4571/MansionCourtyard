package com.example.justkotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun submitOrder(view: View) {
        var isCheckWhippedCream: Boolean =
            findViewById<CheckBox>(R.id.whipped_cream_checkBox).isChecked
        var isCheckedChocolate: Boolean = findViewById<CheckBox>(R.id.chocolate_checkBox).isChecked
        var name: String = findViewById<EditText>(R.id.name_input_box).text.toString()
        var price = calculatePrice(isCheckWhippedCream,isCheckedChocolate)
        var message: String =
            createOrderSummary(price, isCheckWhippedCream, isCheckedChocolate, name)

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            type = "*/*"
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, "vineet4571@gmail.com")
            putExtra(Intent.EXTRA_SUBJECT, "Mansion Courtyard Order for $name")
            putExtra(Intent.EXTRA_TEXT, message)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }

    }

    private fun calculatePrice(isCheckWhippedCream: Boolean, isCheckedChocolate: Boolean): Int{
        var price = 5
        if (isCheckWhippedCream) price+=2
        if (isCheckedChocolate) price+=1
        return price
    }

    private fun createOrderSummary(
        cost: Int,
        isCheckWhippedCream: Boolean,
        isCheckedChocolate: Boolean,
        name: String
    ): String {
        var message: String = "Name: $name\n"
        message += "Add whipped Cream? $isCheckWhippedCream\n"
        message += "Add Chocolate? $isCheckedChocolate\n"
        message += "Quantity: $quantity\n"
        message += "Total: $" + quantity * cost + "\n"
        message += "Thank You !!"
        return message
    }

    private fun display(num: Int) {
        val quantityTextView: TextView = findViewById(R.id.quantity_text_view)
        quantityTextView.text = "" + num
    }

    fun increment(view: View) {
        if(quantity==100) {
            val toast = Toast.makeText(applicationContext, "You Can not have more than 100 coffees", LENGTH_SHORT)
            toast.show()
            return
        }
        quantity += 1
        display(quantity)
    }

    fun decrement(view: View) {
        if (quantity == 1) {
            val toast = Toast.makeText(applicationContext, "You Can not have less than 1 coffee", LENGTH_SHORT)
            toast.show()
            return
        }
        quantity -= 1
        display(quantity)
    }
}
