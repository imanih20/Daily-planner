package com.mohyeddin.dialyplaner.presentation.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.Lifecycle
import com.mohyeddin.dialyplaner.domain.model.PlannedWork
import com.mohyeddin.dialyplaner.presentation.destinations.PlanScreenDestination
import com.mohyeddin.dialyplaner.presentation.my_components.MyCard
import com.mohyeddin.dialyplaner.presentation.my_components.MyText
import com.mohyeddin.dialyplaner.presentation.my_components.withPersianNumber
import com.mohyeddin.dialyplaner.ui.theme.vazir
import com.mohyeddin.dialyplaner.ui.values.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(modifier: Modifier = Modifier,viewModel: HomeViewModel = koinViewModel(),navigator: DestinationsNavigator){
    var todayScore by remember {
        mutableStateOf(0f)
    }
    var yesterdayScore by remember {
        mutableStateOf(0f)
    }
    var weakScore by remember {
        mutableStateOf(0f)
    }
    var plansList by remember {
        mutableStateOf(emptyList<PlannedWork>())
    }

    LaunchedEffect(key1 = viewModel.state) {
        viewModel.state.collect(){
            todayScore = it.todayScore
            yesterdayScore = it.yesterdayScore
            weakScore = it.weakScore
            plansList = it.taskList
        }
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier
                .background(Color.LightGray)
                .padding(15.dp)
        ) {
            StatisticView(rate = todayScore, title = TODAY_RATE_TITLE,Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(5.dp))
            Row {
                StatisticView(rate = yesterdayScore, title = YESTERDAY_RATE_TITLE,Modifier.weight(1f))
                Spacer(modifier = Modifier.size(5.dp))
                StatisticView(rate = weakScore, title = WEAK_RATE_TITLE,Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.size(5.dp))
            PreviewListView(modifier = Modifier.fillMaxSize(), planList = plansList) {
                navigator.navigate(PlanScreenDestination)
            }
        }
    }
}

@Composable
fun PreviewListView(modifier: Modifier = Modifier, planList: List<PlannedWork>, onClick: ()->Unit){
    MyCard(modifier) {
        Column(Modifier.padding(15.dp)) {
            Row(Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                MyText(text = TASKS_TITLE)
                TextButton(onClick = onClick){
                    MyText(text = SEE_ALL_TITLE)
                }
            }
            Spacer(modifier = Modifier.size(1.dp))
            Box(contentAlignment = Alignment.Center) {
                if (planList.isNotEmpty())  LazyRow(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    items(planList){
                        HomePlanView(plannedWork = it)
                        Spacer(modifier = Modifier.size(2.dp))
                    }
                }
                else
                    MyText(text = PREVIEW_EMPTY_LIST_NOTICE, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun HomePlanView(plannedWork: PlannedWork, modifier: Modifier = Modifier){
    MyCard(
        modifier,
        background = MaterialTheme.colors.secondary
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 50.dp),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            MyText(text = plannedWork.workTitle, fontSize = 28.sp)
            Spacer(modifier = Modifier.size(10.dp))
            MyText(text = "${plannedWork.startTime}-${plannedWork.endTime}")
        }
    }
}
@Composable
fun StatisticView(rate: Float,title: String,modifier: Modifier = Modifier) {
    MyCard(
        modifier
            .aspectRatio(1.7f),
    ){
        BoxWithConstraints(Modifier, contentAlignment = Alignment.Center) {
            val size = maxWidth
            Row(Modifier
                .matchParentSize(), horizontalArrangement = Arrangement.SpaceEvenly , verticalAlignment = Alignment.CenterVertically) {
                val radius = size/4
                MyText(text = title, fontSize = (radius /4).value.sp)
                PercentIndicator(radius = radius, stroke = radius/3, percentage = rate, fontSize = (radius/3).value.sp)
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun PercentIndicator(
    modifier: Modifier = Modifier,
    radius: Dp,
    stroke: Dp,
    percentage: Float,
    backgroundColor: Color = Color.Gray,
    color : Color = MaterialTheme.colors.primary,
    textColor : Color = MaterialTheme.colors.onBackground,
    fontSize : TextUnit = TextStyle.Default.fontSize,
){
    val textMeasurer = rememberTextMeasurer()
    val measuredText = textMeasurer.measure(
        text = "${(percentage * 100).toInt()}%".withPersianNumber(),
        style = TextStyle.Default.copy(fontSize = fontSize, fontFamily = vazir)
    )
    val recSize = measuredText.size
    val cirSize = radius*2
    Box(modifier = modifier.wrapContentSize(), contentAlignment = Alignment.Center){
        Canvas(modifier = Modifier.size(cirSize)) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            drawText(measuredText,
                topLeft =  Offset(
                    (canvasWidth-recSize.width)/2f,
                    (canvasHeight-recSize.height)/2f
                ),
                color = textColor
            )
        }
        CircularProgressIndicator(
            progress = percentage,
            Modifier.size(cirSize),
            strokeWidth = stroke,
            backgroundColor = backgroundColor,
            color = color,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
fun Preview(){
    val list = listOf(
        PlannedWork(workTitle = "test", startTime = "14", endTime = "12"),
        PlannedWork(workTitle = "test", startTime = "14", endTime = "12"),
        PlannedWork(workTitle = "test", startTime = "14", endTime = "12"),
        PlannedWork(workTitle = "test", startTime = "14", endTime = "12"),
        PlannedWork(workTitle = "test", startTime = "14", endTime = "12"),

    )
    PreviewListView(planList =list ) {

    }
}

