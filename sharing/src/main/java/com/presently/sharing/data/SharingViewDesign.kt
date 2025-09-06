package com.presently.sharing.data

import com.presently.sharing.R

data class SharingViewDesign(
    val id: String,
    val headerTextColor: Int,
    val bodyTextColor: Int,
    val backgroundColor: Int
)

val designs = listOf(
    SharingViewDesign(
        "original",
        com.presently.ui.R.color.originalTimelineColor,
        com.presently.ui.R.color.originalTimelineColor,
        com.presently.ui.R.color.originalBackgroundColor,
    ),
    SharingViewDesign(
        "moonlight",
        com.presently.ui.R.color.moonlightMainTextAndButtonColor,
        com.presently.ui.R.color.moonlightMainTextAndButtonColor,
        com.presently.ui.R.color.moonlightBackgroundColor
    ),
    SharingViewDesign(
        "daisy",
        com.presently.ui.R.color.daisyMainTextAndButtonColor,
        com.presently.ui.R.color.daisyMainTextAndButtonColor,
        com.presently.ui.R.color.daisyBackgroundColor
    ),
    SharingViewDesign(
        "sunlight",
        com.presently.ui.R.color.sunlightTimelineColor,
        com.presently.ui.R.color.sunlightDateTextEntryScreenTextColor,
        com.presently.ui.R.color.sunlightBackgroundColor
    ),
    SharingViewDesign(
        "rosie",
        com.presently.ui.R.color.rosieMainTextAndButtonColor,
        com.presently.ui.R.color.rosieHintQuoteTextColor,
        com.presently.ui.R.color.rosieBackgroundColor
    ),
    SharingViewDesign(
        "dawn",
        com.presently.ui.R.color.dawnMainTextAndButtonColor,
        com.presently.ui.R.color.dawnMainTextAndButtonColor,
        com.presently.ui.R.color.dawnBackgroundColor
    ),
    SharingViewDesign(
        "katie",
        com.presently.ui.R.color.katieTimelineColor,
        com.presently.ui.R.color.katieDateTextEntryScreenTextColor,
        com.presently.ui.R.color.katieBackgroundColor
    ),
    SharingViewDesign(
        "brittany",
        com.presently.ui.R.color.brittanyDateTextEntryScreenTextColor,
        com.presently.ui.R.color.brittanyDateTextEntryScreenTextColor,
        com.presently.ui.R.color.brittanyBackgroundColor
    ),
    SharingViewDesign(
        "matisse",
        com.presently.ui.R.color.matisseMainTextAndButtonColor,
        com.presently.ui.R.color.matisseMainTextAndButtonColor,
        com.presently.ui.R.color.matisseBackgroundColor
    ),
)