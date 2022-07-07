package me.openani

import javax.security.auth.login.LoginException

open class App {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            try {
                Bot(TOKEN)
                syncTables()
            } catch (e: LoginException) {
                e.printStackTrace()
            }
        }
    }
}