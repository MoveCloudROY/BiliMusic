package top.roy1994.bilimusic.ui.components.topselect

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.relay.compose.CrossAxisAlignment
import com.google.relay.compose.MainAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayText

// Design to select for TopSelectBarElem
enum class Status {
    On,
    Off
}

/**
 * This composable was generated from the UI Package 'top_select_bar_elem'.
 * Generated code; do not edit directly
 */
@Composable
fun TopSelect(
    modifier: Modifier = Modifier,
    property1: Status = Status.On,
    ontext: String,
    offtext: String
) {
    when (property1) {
        Status.On -> TopLevelProperty1On(modifier = modifier) {
            OntextProperty1On(ontext = ontext)
        }
        Status.Off -> TopLevelProperty1Off(modifier = modifier) {
            OfftextProperty1Off(offtext = offtext)
        }
    }
}

@Preview(widthDp = 48, heightDp = 35)
@Composable
private fun TopSelectBarElemProperty1OnPreview() {
    MaterialTheme {
        RelayContainer {
            TopSelect(
                ontext = "主页",
                offtext = "主页",
                property1 = Status.On,
                modifier = Modifier.columnWeight(1.0f)
            )
        }
    }
}

@Preview(widthDp = 28, heightDp = 35)
@Composable
private fun TopSelectBarElemProperty1OffPreview() {
    MaterialTheme {
        RelayContainer {
            TopSelect(
                ontext = "主页",
                offtext = "主页",
                property1 = Status.Off,
                modifier = Modifier.columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun OntextProperty1On(
    ontext: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = ontext,
        fontSize = 24.0.sp,
        fontFamily = notoSansSC,
        color = Color(
            alpha = 255,
            red = 51,
            green = 61,
            blue = 100
        ),
        height = 1.219000021616618.em,
        letterSpacing = -0.2800000011920929.sp,
        fontWeight = FontWeight(700.0.toInt()),
        modifier = modifier
    )
}

@Composable
fun TopLevelProperty1On(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.Start,
        crossAxisAlignment = CrossAxisAlignment.End,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 10.0,
        clipToParent = false,
        content = content,
//        modifier = modifier.fillMaxHeight(1.0f)
    )
}

@Composable
fun OfftextProperty1Off(
    offtext: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = offtext,
        fontFamily = notoSansSC,
        color = Color(
            alpha = 255,
            red = 177,
            green = 183,
            blue = 215
        ),
        height = 1.2189999989100866.em,
        letterSpacing = -0.2800000011920929.sp,
        fontWeight = FontWeight(700.0.toInt()),
        modifier = modifier
    )
}

@Composable
fun TopLevelProperty1Off(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.Start,
        crossAxisAlignment = CrossAxisAlignment.End,
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 0.0.dp,
//            top = 6.0.dp,
            end = 0.0.dp,
//            bottom = 6.0.dp
        ),
        itemSpacing = 10.0,
        clipToParent = false,
        content = content,
        modifier = modifier.fillMaxHeight(1.0f)
    )
}
