package com.example.xueapp.data

sealed class SettingList(
    val text: String,
) {
    object Account : SettingList("Account")
    object Subscription : SettingList("Subscrption")
    object Preference : SettingList("Preference")
    object About : SettingList("About Xue Chinese")
    companion object {
        val settingitems = listOf(Account, Subscription, Preference, About)
    }
}