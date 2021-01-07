/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*import java.text.NumberFormat;
*/
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */

    public void increment(View view)
    {
        if(quantity==100) {
            //Show an Error Message as Toast
            Toast.makeText(this,"You cannot have more than 100 Coffees",Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
        //displayPrice(quantity*5);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view)
    {
        if(quantity==1) {
            Toast.makeText(this,"You cannot have less than 1 Coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        Log.v("MainActivity","Has Chocolate "+hasChocolate);
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = (createOrderSummary(name,price,hasWhippedCream,hasChocolate));

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for "+ name);
            intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            //displayMessage(createOrderSummary(name,price,hasWhippedCream,hasChocolate));



    }


    private int calculatePrice(boolean addWhippedCream,boolean addChocolate)            //@return total price
    {
        int basePrice = 5;
        if(addWhippedCream)
            basePrice += 1;
        if(addChocolate)
            basePrice += 2;

        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param name is the Name of the customer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */


    private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate)        //@return order summary
    {

        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream ? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate ? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price ;
        priceMessage += "\nThank You!";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
    /**
     * This method displays the given price on the screen.
     */

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message)
    {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
