package com.example.tipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipapp.components.InputField
import com.example.tipapp.ui.theme.TipAppTheme
import com.example.tipapp.util.calculateTotalPerPerson
import com.example.tipapp.util.calculateTotalTip
import com.example.tipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
                MyApp {
                    //TopHeader()
                    MainContent()
                }



        }
    }
}
@Composable
fun MyApp(content: @Composable () ->Unit) {

    TipAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()


        }
    }
}

@Preview
@Composable
fun TopHeader(totalPerPerson:Double=134.00){

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .height(150.dp)
        .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
            //.clip(shape= RoundedCornerShape(corner = CornerSize(12.dp)))
         color= Color(0xFFE4C7FA)
    ) {
        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

           val total="%.2f".format(totalPerPerson)
            Text(text = "Total Per Person", style = MaterialTheme.typography.titleLarge, fontSize = 18.sp)
            Text(text = "$$total",color=Color.Black, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        }
        
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent(){

    Column(modifier=Modifier.padding(all=12.dp)) {
        BillForm() { billAmt ->
            Log.d("AMT", "MainContent: ${billAmt.toInt()}")
        }
    }


}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier=Modifier,
             onValChange:(String) -> Unit ={}

              ){

    val totalBillState= remember {
        mutableStateOf("")
    }




    val validState= remember (totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }


    val sliderPostionState =remember {
        mutableStateOf(0f)

        }
    val tipPercentage=(sliderPostionState.value*100).toInt()
    val splitByState= remember {
        mutableStateOf(1)
    }

    val tipAmountState= remember {
        mutableStateOf(0.00)
    }
    val totalPerPersonState= remember {
        mutableStateOf(0.00)
    }

    val range=IntRange(start = 1, endInclusive = 100)

    val keyboardController=LocalSoftwareKeyboardController.current


    TopHeader(totalPerPerson = totalPerPersonState.value)

    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(modifier=Modifier.padding(6.dp),
        verticalArrangement =Arrangement.Top,
        horizontalAlignment = Alignment.Start) {
            InputField(valueState = totalBillState
                , labelId = "Enter Bill"
                , enabled = true
                , isSngleLine = true,
                onAction = KeyboardActions {
                    if(!validState) return@KeyboardActions
                    //Todo - onvaluechanged
                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()

                })
   //         if(validState){
                Row(modifier = Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start) {
                    Text(text = "Split",
                        modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    ))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End) {
                        RoundIconButton( imageVector = Icons.Default.Delete,
                            onClick = {
                                splitByState.value = if(splitByState.value>1) splitByState.value-1 else 1
                                totalPerPersonState.value= calculateTotalPerPerson(totalBill=totalBillState.value.toDouble(),
                                    splitBy = splitByState.value,tipPercentage=tipPercentage)

                            })

                        Text(text = "${splitByState.value}",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 9.dp, end = 9.dp))


                        RoundIconButton(imageVector = Icons.Default.Add,
                            onClick = {
                                if(splitByState.value<range.last){
                                    splitByState.value=splitByState.value+1
                                    totalPerPersonState.value= calculateTotalPerPerson(totalBill=totalBillState.value.toDouble(),
                                        splitBy = splitByState.value,tipPercentage=tipPercentage)
                                }
                            })


                    }

                }

                    //tip row
            Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                Text(text = "tip",
                modifier =Modifier.align(alignment = Alignment.CenterVertically) )
                Spacer(modifier = Modifier.width(200.dp))

                Text(text = "$${tipAmountState.value}",modifier =Modifier.align(alignment = Alignment.CenterVertically) )

            }

            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$tipPercentage %")
                Spacer(modifier = Modifier.height(14.dp))

                //Slider
                Slider(value = sliderPostionState.value,
                    onValueChange = {newVal->
                        sliderPostionState.value=newVal
                        tipAmountState.value= calculateTotalTip(totalBillState.value.toDouble(),tipPercentage=tipPercentage)

                       /* totalPerPersonState.value= calculateTotalPerPerson(totalBill=totalBillState.value.toDouble(),
                        splitBy = splitByState.value,tipPercentage=tipPercentage)

                        */
                    }, modifier = Modifier.padding(start = 16.dp,end=16.dp),
                    steps = 100,
                onValueChangeFinished = {
                   // Log.d("")
                })

            }

     //       } else {
       //         Box(){}
         //   }

        }

    }

}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipAppTheme {
        MyApp {
            Text( "Hello Again")
        }

    }
}