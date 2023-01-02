package top.roy1994.bilimusic.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.viewmodel.PlayerProgressBarViewModel
import top.roy1994.bilimusic.viewmodel.PlayerViewModel


/*
You can use the following code for commercial purposes with some restrictions.
Read the full license here: https://semicolonspace.com/semicolonspace-license/
For more designs with source code,
visit: https://semicolonspace.com/jetpack-compose-samples/
 */


@Preview
@Composable
fun PreviewPlayerProgressBar() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerProgressBar()
    }
}


@Composable
fun PlayerProgressBar(
    PlayerBarVM: PlayerViewModel = viewModel(),
    indicatorHeight: Dp = 24.dp,
    backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.3f),
    indicatorPadding: Dp = 24.dp,
    gradientColors: List<Color> = listOf(
        Color(0xFF3f5fff),
        Color(0xFFaf71ff),
    ),
    numberStyle: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.relay_montserrat_bold, FontWeight.Bold)),
        fontSize = 16.sp
    ),
    animationDuration: Int = 1000,
    animationDelay: Int = 0
) {
    val playedPercentage by PlayerBarVM.playedPercentage.observeAsState(initial = 0f)

    val animateNumber = animateFloatAsState(
        targetValue = playedPercentage,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(Unit) {
        PlayerBarVM.startThreadGradient()
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(indicatorHeight)
            .padding(start = indicatorPadding, end = indicatorPadding)
    ) {

        // Background indicator
        drawLine(
            color = backgroundIndicatorColor,
            cap = StrokeCap.Round,
            strokeWidth = size.height,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = 0f)
        )

        // Convert the downloaded percentage into progress (width of foreground indicator)
        val progress =
            (animateNumber.value / 100) * size.width // size.width returns the width of the canvas

        // Foreground indicator
        drawLine(
            brush = Brush.linearGradient(
                colors = gradientColors
            ),
            cap = StrokeCap.Round,
            strokeWidth = size.height,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = progress, y = 0f)
        )

    }
    val playedSeconds = PlayerBarVM.playedSeconds.value
    val totalSeconds = PlayerBarVM.totalSeconds.value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = indicatorPadding, end = indicatorPadding)
    ) {
        Text(
            text = playedSeconds?.div(60).toString().padStart(2, '0')
                + ":"
                + playedSeconds?.mod(60).toString().padStart(2, '0'),
            style = numberStyle
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = totalSeconds?.div(60).toString().padStart(2, '0')
                + ":"
                + totalSeconds?.mod(60).toString().padStart(2, '0'),
            style = numberStyle
        )
    }


}

