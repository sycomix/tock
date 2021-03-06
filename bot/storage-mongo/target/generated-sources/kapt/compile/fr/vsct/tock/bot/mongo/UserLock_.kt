package fr.vsct.tock.bot.mongo

import java.time.Instant
import kotlin.Boolean
import kotlin.collections.Collection
import kotlin.reflect.KProperty1
import org.litote.kmongo.Id
import org.litote.kmongo.property.KPropertyPath

class UserLock_<T>(previous: KPropertyPath<T, *>?, property: KProperty1<*, MongoUserLock.UserLock?>) : KPropertyPath<T, MongoUserLock.UserLock?>(previous,property) {
    val _id: KProperty1<T, Id<MongoUserLock.UserLock>?>
        get() = org.litote.kmongo.property.KPropertyPath(this,MongoUserLock.UserLock::_id)

    val locked: KProperty1<T, Boolean?>
        get() = org.litote.kmongo.property.KPropertyPath(this,MongoUserLock.UserLock::locked)

    val date: KProperty1<T, Instant?>
        get() = org.litote.kmongo.property.KPropertyPath(this,MongoUserLock.UserLock::date)
    companion object {
        val _id: KProperty1<MongoUserLock.UserLock, Id<MongoUserLock.UserLock>?>
            get() = MongoUserLock.UserLock::_id
        val Locked: KProperty1<MongoUserLock.UserLock, Boolean?>
            get() = MongoUserLock.UserLock::locked
        val Date: KProperty1<MongoUserLock.UserLock, Instant?>
            get() = MongoUserLock.UserLock::date}
}

class UserLock_Col<T>(previous: KPropertyPath<T, *>?, property: KProperty1<*, Collection<MongoUserLock.UserLock>?>) : KPropertyPath<T, Collection<MongoUserLock.UserLock>?>(previous,property) {
    val _id: KProperty1<T, Id<MongoUserLock.UserLock>?>
        get() = org.litote.kmongo.property.KPropertyPath(this,MongoUserLock.UserLock::_id)

    val locked: KProperty1<T, Boolean?>
        get() = org.litote.kmongo.property.KPropertyPath(this,MongoUserLock.UserLock::locked)

    val date: KProperty1<T, Instant?>
        get() = org.litote.kmongo.property.KPropertyPath(this,MongoUserLock.UserLock::date)
}
