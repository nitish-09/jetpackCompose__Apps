package com.example.tipapp.components

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(modifier: Modifier=Modifier,
               valueState:MutableState<String>,
               labelId:String,
               enabled:Boolean,
               isSngleLine:Boolean,
               keyboardType: KeyboardType= KeyboardType.Number,
               imeAction:ImeAction=ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default

               ){

              OutlinedTextField(value = valueState.value,
                  onValueChange ={ valueState.value=it},
              label={ Text(labelId)},
                  leadingIcon = { Icon(imageVector = Icons.Rounded.ThumbUp, contentDescription ="MoneyIcon" )},
                  singleLine = isSngleLine,
                  textStyle = TextStyle(fontSize = 18.sp,
                      color = MaterialTheme.colorScheme.onBackground),
                  modifier=Modifier.padding(bottom = 10.dp, start = 10.dp,end=10.dp).fillMaxWidth(),
                  enabled=enabled,
                  keyboardOptions = KeyboardOptions(keyboardType=keyboardType,
                  imeAction = imeAction),
                  keyboardActions = onAction
                 )

    // changes for pull request

         }

//more changes
