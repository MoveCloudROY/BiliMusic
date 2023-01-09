package top.roy1994.bilimusic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun swipeToDismiss(
    onDelete: () -> Unit,
    content: @Composable () -> Unit,
) {
    val dismissState = rememberDismissState(
        initialValue = DismissValue.Default,
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDelete()
            }
            true
        }
    )


    SwipeToDismiss(
        state = dismissState,
        /***  create dismiss alert Background */
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.EndToStart -> Color.Red
                else -> Color.Transparent
            }
            val direction = dismissState.dismissDirection

            if (direction == DismissDirection.EndToStart) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.heightIn(5.dp))
                        Text(
                            text = "Move to Delete",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray
                        )

                    }
                }
            }
        },
        /**** Dismiss Content */
        dismissContent = {
            content()
        },
        /*** Set Direction to dismiss */
        directions = setOf(DismissDirection.EndToStart),
    )
}