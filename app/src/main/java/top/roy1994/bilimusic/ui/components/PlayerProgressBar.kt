package top.roy1994.bilimusic.ui.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
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
        PlayerProgressBar(viewModel())
    }
}


@Composable
fun PlayerProgressBar(
    PlayerVM: PlayerViewModel,
    indicatorHeight: Dp = 16.dp,
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
    // bar是否被按下
    var isBarPressed by remember { mutableStateOf(false) }
    // 锚点的半径, 根据barPressed的状态'平滑'地改变自身的大小
    val radius by animateFloatAsState(if (isBarPressed) 40f else 32f)

    val playedPercentage by PlayerVM.playedPercentage.observeAsState(initial = 0f)
    var dragedPercentage by remember { mutableStateOf(0f) }



    val animateNumber = animateFloatAsState(
        targetValue = if (isBarPressed) dragedPercentage else playedPercentage,
        animationSpec = tween(
            durationMillis = if (isBarPressed) 0  else animationDuration,
            delayMillis = animationDelay
        )
    )


    LaunchedEffect(Unit) {
        PlayerVM.startThreadGradient()
    }

    Canvas(
        modifier = Modifier
            .background(Color(0xFFFFFFFF))
            .fillMaxWidth()
            .height(indicatorHeight)
            .padding(start = indicatorPadding, end = indicatorPadding)
            .pointerInput(Unit) {
                detectDragGestures( // 响应滑动事件
                    onDragStart = {
//                        if (!PlayerVM.isEmptyMusic()) {
                            dragedPercentage = playedPercentage
                            isBarPressed = true
//                        }
                    },
                    onDragCancel = { isBarPressed = false },
                    onDragEnd = {
                        // 滑动结束时， 回调setProgressPercentage函数
                        // 恢复锚点大小
                        PlayerVM.setProgressPercentage(dragedPercentage/100f)
                        isBarPressed = false
                    },
                    onDrag = { change, dragAmount ->
                        // 滑动过程中， 实时刷新progress的值(注意左右边界的问题)，
                        // 此值一旦改变， 整个Seekbar就会重组(刷新)
                        dragedPercentage = if (change.position.x < 0) {
                            0f
                        } else if (change.position.x > size.width) {
                            1f
                        } else {
                            (change.position.x / this.size.width)
                        } * 100f
                        Log.i("Player", """
                            dragedPercentage:   ${dragedPercentage},
                            animateNumber:      ${animateNumber.value}
                        """.trimIndent())
                    })
            }
            .pointerInput(Unit) {
                // 响应点击事件， 直接跳到该进度处
                detectTapGestures(onTap = {
                    if (!PlayerVM.isEmptyMusic()) {
                        PlayerVM.setProgressPercentage(it.x / size.width)
                        isBarPressed = false
                    }
                })
            },
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

        // anchor
        drawCircle(
            color =  Color(0xFFFFFFFF),
            radius = radius,
            center = Offset(x = progress, y = 0f),
//            style = Stroke(9f)
        )
        drawCircle(
            color =  Color(0x8F8F8F8F),
            radius = radius,
            center = Offset(x = progress, y = 0f),
            style = Stroke(12f)
        )


    }

    Row(
        modifier = Modifier
            .background(Color(0xFFFFFFFF))
            .fillMaxWidth()
            .padding(start = indicatorPadding, end = indicatorPadding),
    ) {
        Text(
            text = PlayerVM.playedSeconds.value.div(60).toString().padStart(2, '0')
                + ":"
                + PlayerVM.playedSeconds.value.mod(60).toString().padStart(2, '0'),
            style = numberStyle,
            color = Color(
                alpha = 255,
                red = 51,
                green = 61,
                blue = 100
            ),
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = PlayerVM.nowMusic.value.second.div(60).toString().padStart(2, '0')
                + ":"
                + PlayerVM.nowMusic.value.second.mod(60).toString().padStart(2, '0'),
            style = numberStyle,
            color = Color(
                alpha = 255,
                red = 51,
                green = 61,
                blue = 100
            ),
        )
    }


}

