package com.example.challengelocaweb.domain.useCases.appEntry

import com.example.challengelocaweb.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
){
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }

}