package net.ucoz.testcompose.presentation.jobList.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.ucoz.testcompose.R
import net.ucoz.testcompose.presentation.jobList.JobUI
import net.ucoz.testcompose.ui.theme.DarkBlue
import net.ucoz.testcompose.ui.theme.GreenLight
import net.ucoz.testcompose.ui.theme.MiddleBlue
import net.ucoz.testcompose.ui.theme.OrangeLight
import net.ucoz.testcompose.ui.theme.White


@Composable
fun ItemJobList(item: JobUI) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(R.dimen.margin_medium),
                top = dimensionResource(R.dimen.margin_medium),
                end = dimensionResource(R.dimen.margin_medium)
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
        contentColor = White,
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.margin_medium))) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.margin_medium)),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(0.65f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(item.isPause) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_pause),
                            contentDescription = "drawable icons",
                            tint = OrangeLight
                        )
                        Text(
                            modifier = Modifier.padding(start = dimensionResource(R.dimen.margin_small)),
                            text = item.pauseName ?: "",
                            fontWeight = FontWeight.W900,
                            fontStyle = FontStyle.Normal,
                            fontSize = 14.sp,
                            color = DarkBlue,
                        )
                    } else {
                        if (item.isCompleted) {
                            Text(
                                text = stringResource(id = R.string.completed),
                                fontWeight = FontWeight.W900,
                                fontStyle = FontStyle.Normal,
                                fontSize = 14.sp,
                                color = DarkBlue,
                            )
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.size(28.dp),
                                color = GreenLight
                            )
                            Text(
                                modifier = Modifier.padding(start = dimensionResource(R.dimen.margin_small)),
                                text = stringResource(id = R.string.in_progress),
                                fontWeight = FontWeight.W900,
                                fontStyle = FontStyle.Normal,
                                fontSize = 14.sp,
                                color = DarkBlue,
                            )
                        }

                    }




                }
                Row(modifier = Modifier.weight(0.35f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.total),
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                        fontSize = 12.sp,
                        color = MiddleBlue,
                    )
                    Text(
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.margin_small)),
                        text = item.totalTime ?: "",
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        color = DarkBlue,
                    )
                }

            }
            Row(modifier = Modifier
                .fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.customer_name),
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                        fontSize = 12.sp,
                        color = MiddleBlue,
                    )
                    Text(
                        modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_small)),
                        text = item.customerName ?: "",
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        color = DarkBlue,
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.activity),
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                        fontSize = 12.sp,
                        color = MiddleBlue,
                    )
                    Text(
                        modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_small)),
                        text = item.activity ?: "",
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        color = DarkBlue,
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_medium)),
                text = stringResource(id = R.string.grid),
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Normal,
                fontSize = 12.sp,
                color = MiddleBlue,
            )
            Text(
                modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_small)),
                text = item.grid ?: "",
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Normal,
                fontSize = 14.sp,
                color = DarkBlue,
            )
            Text(
                modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_medium)),
                text = stringResource(id = R.string.file_name),
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Normal,
                fontSize = 12.sp,
                color = MiddleBlue,
            )
            Text(
                modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_small)),
                text = item.fileName ?: "",
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Normal,
                fontSize = 14.sp,
                color = DarkBlue,
            )

        }

    }


}

@Preview(showBackground = true)
@Composable
fun ItemJobListPreview() {
    ItemJobList(
        item = JobUI(
            customerName = "Kyler Anderson",
            activity = "Enclosing",
            grid = "211305-155307X-XWZP",
            fileName = "SRA3_LIVE-PO P 013 2258_501445",
            totalTime = "00:00:12",
            isPause = true,
            isCompleted = false,
            pauseName = "Checking another job",
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ItemJobListPreviewSecond() {
    ItemJobList(
        item = JobUI(
            customerName = "Kyler Anderson",
            activity = "Enclosing",
            grid = "211305-155307X-XWZP",
            fileName = "SRA3_LIVE-PO P 013 2258_501445",
            totalTime = "00:00:12",
            isPause = false,
            isCompleted = false,
            pauseName = "Checking another job",
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ItemJobListPreviewThird() {
    ItemJobList(
        item = JobUI(
            customerName = "Kyler Anderson",
            activity = "Enclosing",
            grid = "211305-155307X-XWZP",
            fileName = "SRA3_LIVE-PO P 013 2258_501445",
            totalTime = "00:00:12",
            isPause = false,
            isCompleted = true,
            pauseName = "Checking another job",
        )
    )
}