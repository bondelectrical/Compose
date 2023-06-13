package net.ucoz.testcompose

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp

@Composable
fun textSize10() = fontDimensionResource(R.dimen.text10)
@Composable
fun textSize12() = fontDimensionResource(R.dimen.text12)
@Composable
fun textSize14() = fontDimensionResource(R.dimen.text14)
@Composable
fun textSize16() = fontDimensionResource(R.dimen.text16)
@Composable
fun textSize24() = fontDimensionResource(R.dimen.text24)
