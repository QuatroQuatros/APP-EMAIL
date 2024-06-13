package com.example.challengelocaweb.domain.useCases.appEntry

import com.example.challengelocaweb.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry (
    private val localUserManager: LocalUserManager
){
    operator fun invoke(): Flow<Boolean>
    {
        return localUserManager.readAppEntry()
    }

}