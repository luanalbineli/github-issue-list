package com.githubussuelist.common

import com.github.javafaker.Faker
import com.githubussuelist.model.room.RepositoryEntityModel
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LiveDataInstantExecutorExtension::class)
abstract class BaseTest {
    protected val faker by lazy { Faker() }
    private var mUniqueId = 0

    fun createNewRepositoryEntityModel(fullName: String) = RepositoryEntityModel(
        id = ++mUniqueId,
        starCount = faker.number().randomDigit(),
        openIssueCount = faker.number().randomDigit(),
        fullName = fullName,
        subscriberCount = faker.number().randomDigit(),
        forkCount = faker.number().randomDigit(),
        description = faker.lorem().words(5).joinToString(" "),
        name = fullName.split('/')[1]
    )
}