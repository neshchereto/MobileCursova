package com.neshchereto.abitsearch.presentation.screen.main.components

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun InstructionDialog(isVisible: Boolean, onDismiss: () -> Unit) {
    AnimatedVisibility(visible = isVisible) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Card() {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp).padding(bottom = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Приклади пошукових запитів",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W700
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(7f).padding(top = 6.dp)
                        )
                        IconButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Outlined.Close, contentDescription = null)
                        }
                    }

                    Spacer(modifier = Modifier.height(1.dp))
                    Text(text = "• Петрова В. О. 2022")
                    Text(text = "• Петрова В. О.")
                    Text(text = "• Ромадін В. В. 10.3 2018, де 10.3 - середній бал документа про освіту")
                }
            }

        }
    }
}