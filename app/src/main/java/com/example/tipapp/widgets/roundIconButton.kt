package com.example.tipapp.widgets

import android.service.autofill.OnClickAction
import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val IconbuttonSizeModifier=Modifier.size(40.dp)
@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    elevation:Dp=4.dp)
{
    Card(modifier = Modifier.padding(4.dp)
        .clickable{ onClick.invoke()}.then(IconbuttonSizeModifier).background(Color.Red),
        shape = CircleShape,
        elevation= CardDefaults.cardElevation(
            defaultElevation = elevation
        )
    ) {
        Icon(imageVector=imageVector,contentDescription ="Plus or minus icon",
            tint=tint)
    }

}
