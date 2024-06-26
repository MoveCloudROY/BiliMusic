package top.roy1994.bilimusic.ui.components.topshowblock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.relay.compose.MainAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayText
import com.google.relay.compose.relayDropShadow
import com.google.relay.compose.tappable

/**
 * This composable was generated from the UI Package 'top_show_block_elem'.
 * Generated code; do not edit directly
 */
@Composable
fun TopShowBlock(
    modifier: Modifier = Modifier,
    onTopShowBlockElemTapped: () -> Unit = {},
    number: String,
    type: String
) {
    TopLevel(
        onTopShowBlockElemTapped = onTopShowBlockElemTapped,
        modifier = modifier
    ) {
        Number(
            number = number,
            modifier = Modifier.rowWeight(1.0f)
        )
        Type(
            type = type,
            modifier = Modifier.rowWeight(1.0f)
        )
    }
}

@Preview(widthDp = 99, heightDp = 60)
@Composable
private fun TopShowBlockElemPreview() {
    MaterialTheme {
        RelayContainer {
            TopShowBlock(
                onTopShowBlockElemTapped = {},
                number = "32",
                type = "歌曲",
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun Number(
    number: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        modifier = modifier.fillMaxWidth(1.0f),
        content = number,
        fontSize = 28.0.sp,
        fontFamily = concertOne,
        color = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 0.77880859375.em,
        letterSpacing = (-0.2800000011920929).sp,
        maxLines = -1,

        shadow = Shadow(
            color = Color(
                alpha = 63,
                red = 0,
                green = 0,
                blue = 0
            ),
            offset = Offset(
                x = 2.0f,
                y = 3.0f
            ),
            blurRadius = 4.0f,
        )
    )
}

@Composable
fun Type(
    type: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        modifier = modifier.fillMaxWidth(1.0f),
        content = type,
        fontFamily = lxgwWenkaiBold,
        color = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 1.17138671875.em,
        letterSpacing = (-0.2800000011920929).sp,
        maxLines = -1,
        shadow = Shadow(
            color = Color(
                alpha = 63,
                red = 0,
                green = 0,
                blue = 0
            ),
            offset = Offset(
                x = 2.0f,
                y = 3.0f
            ),
            blurRadius = 4.0f,
        ),

    )
}

@Composable
fun TopLevel(
    onTopShowBlockElemTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.Start,
        padding = PaddingValues(
            start = 0.0.dp,
            top = 5.0.dp,
            end = 0.0.dp,
            bottom = 5.0.dp
        ),
        itemSpacing = 0.0,
        clipToParent = false,
        radius = 14.0,
        content = content,
        modifier = modifier.background(Color.Transparent).drawWithContent(
            onDraw = {
                drawRect(
                    brush = Brush.linearGradient(
                        0.0f to Color(
                            alpha = 255,
                            red = 63,
                            green = 87,
                            blue = 255
                        ),
                        Float.POSITIVE_INFINITY to Color(
                            alpha = 181,
                            red = 159,
                            green = 114,
                            blue = 255
                        ),
                        start = Offset(
                            0.2f,
                            Float.POSITIVE_INFINITY
                        ),
                        end = Offset(
                            Float.POSITIVE_INFINITY,
                            -0.95f
                        )
                    )
                )
                drawContent()
            }
        ).tappable(onTap = onTopShowBlockElemTapped).fillMaxWidth(1.0f).fillMaxHeight(1.0f).relayDropShadow(
            color = Color(
                alpha = 255,
                red = 144,
                green = 157,
                blue = 250
            ),
            borderRadius = 14.0.dp,
            blur = 4.0.dp,
            offsetX = 1.0.dp,
            offsetY = 2.0.dp,
            spread = 0.0.dp
        )
    )
}
