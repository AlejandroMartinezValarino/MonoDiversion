package com.example.monodiversion.model

import com.example.monodiversion.data.UserScoreDb
import com.example.monodiversion.data.entity.FlagEntity
import com.example.monodiversion.data.entity.ScoreEntity
import com.example.monodiversion.data.entity.UserEntity
import com.example.monodiversion.data.entity.UserWithFlag
import com.example.monodiversion.data.entity.UserWithScores
import javax.inject.Inject

class UserRepository @Inject constructor(private val database: UserScoreDb) {

    suspend fun getAll(): List<User> = database.userDao().getAll().toUsers()

    suspend fun getUserById(id:Long):User? = database.userDao().getById(id)?.toUser()

    suspend fun getAllWithScores(): Map<User, List<Score>> =
        database.userDao().getAllUsersWithScores().toUsersWithScores()

    suspend fun save(user: User):Long {
        val userId = database.userDao().upsert(user.toEntity())
        database.flagDao().insert(user.flag!!.toEntity(userId))
        return userId
    }

    suspend fun exist(user: User): Boolean {
        return database.userDao().getById(user.id)  != null
    }

    suspend fun remove(user: User) {
        database.userDao().removeById(user.id)
    }

    //Mappers
    private fun User.toEntity(): UserEntity =
        UserEntity(
            id = this.id,
            name = this.name,
            country = this.country
        )
    private fun Flag.toEntity(userId: Long): FlagEntity =
        FlagEntity(
            userId = userId,
            colors = this.colors,
            orientation = this.orientation
        )
    private fun FlagEntity.toFlag(): Flag =
        Flag(
            colors = this.colors,
            orientation = this.orientation
        )

    private fun UserWithFlag.toUser(): User =
        User(
            id = this.user.id,
            name = this.user.name,
            country = this.user.country,
            flag = this.flag?.toFlag()
        )

    private fun List<UserWithFlag>.toUsers(): List<User> {
        return this.map { it.toUser() }
    }

    private fun ScoreEntity.toScore(): Score =
        Score(
            scoreId = this.scoreId,
            points = this.points,
            gameType = this.gameType
        )

    private fun List<ScoreEntity>.toScores(): List<Score> =
        this.map { it.toScore() }

    private fun UserEntity.toUser(): User =
        User(
            id = this.id,
            name = this.name,
            country = this.country
        )

    private fun List<UserWithScores>.toUsersWithScores(): Map<User, List<Score>> =
        this.associate { it.user.toUser() to it.scores.toScores() }

}