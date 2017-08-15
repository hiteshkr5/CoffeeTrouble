/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.example.hitesh0505.coffeetrouble;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int priceOfCoffee = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
    /**
     * Sees if the checkbox is clicked or not and stores it in a varible/field isWhippedCreamCheckbox and same for chocolate one .
     */
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean isWhippedCreamCheckboxChecked = whippedCreamCheckBox.isChecked();


        CheckBox chocolateToppingCheckbox = (CheckBox) findViewById(R.id.chocolate_topping_checkbox);
        boolean isChocolateToppingCheckboxChecked = chocolateToppingCheckbox.isChecked();
    /**
     *finds the user name by id and stores it into string name
     */
        EditText nameOfOrderer = (EditText) findViewById(R.id.name_field);
        String name = nameOfOrderer.getText().toString();


    /**
     *createOrderSummary is called and stored in ourFinalOrderSummary
     */
        int price = calculatePrice(isWhippedCreamCheckboxChecked,isChocolateToppingCheckboxChecked);
        String ourFinalOrderSummary = createOrderSummary(price,name);

    /**
     *intents are used to direct our running activity to some other activity inside our app or to another app.Implicit and explicit.
     */final Intent intentForMaps = new Intent(Intent.ACTION_SENDTO);
        intentForMaps.setData(Uri.parse("mailto:"));
        intentForMaps.putExtra(Intent.EXTRA_SUBJECT, "Coffees from CoffeeTrouble for: " + name);
        intentForMaps.putExtra(Intent.EXTRA_TEXT, ourFinalOrderSummary);


        /**
         * Just trying to pop up a dialog box. If it works i'll write more comments.
         *
         */
        AlertDialog.Builder dialogBoxBuilder = new AlertDialog.Builder(this);
                dialogBoxBuilder.setTitle("Order Confirmation")
                .setMessage("Are you sure you want to place the order?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        if(intentForMaps.resolveActivity(getPackageManager()) != null){
                            startActivity(intentForMaps);
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolateTopping){
        int basePrice = 5;

        if(addWhippedCream){
            basePrice+=1;
        }
        if(addChocolateTopping){
            basePrice+=2;
        }

        return quantity*basePrice;
    }

    /**
     * This method displays the given price on the screen.


    private void displayPrice(int number){
     Textview textview = (TextView) findViewById(R.id.price_text_view);
     textView.setText(""+number);
     }
    */

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    /**
     * This method displays the increment in price on the screen.
     * making this method as a private method, crashes the app..idk why
     */

    public void increment(View view){
        if(quantity == 10){
            Toast.makeText(this,"Ahh..i'm tired, Such number of coffees!!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity= quantity+1;
        display(quantity);

    }
    /**
     * This method displays the decerement in price on the screen.
     * making this method as a private method, it crashes the app ..maybe it's a view so it must be public.
     */

    public void decrement(View view){
        if(quantity == 1){
            Toast.makeText(this,"Oops, Don't know how to make 0 cups!!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity-=1;
        display(quantity);


    }

    private String createOrderSummary(int price, String nameOfOrderingPerson){
        String ourSummary = "Name: "+ nameOfOrderingPerson;
        ourSummary += "\nQuantity: " + quantity;
        ourSummary += "\nTotal: $" + price;
        ourSummary += "\nThank You !!";

        return ourSummary;
    }



}