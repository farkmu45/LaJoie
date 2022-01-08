package com.tigro.lajoie.models

import com.tigro.lajoie.R

data class AccountScreen(val icon: Int, val title: String, val subtitle: String)

val regularScreenData = listOf(
    AccountScreen(
        R.drawable.ic_person,
        "Account Information",
        "Change your personal information"
    ),
    AccountScreen(R.drawable.ic_verified, "Submission", "Change your status to expert"),
    AccountScreen(R.drawable.ic_knowledge, "About", "Info about our team"),
    AccountScreen(R.drawable.ic_logout, "Logout", "Log out of your account")
)

val expertScreenData = listOf(
    AccountScreen(
        R.drawable.ic_person,
        "Account Information",
        "Change your personal information"
    ),
    AccountScreen(R.drawable.ic_knowledge, "About", "Info about our team"),
    AccountScreen(R.drawable.ic_logout, "Logout", "Log out of your account")
)