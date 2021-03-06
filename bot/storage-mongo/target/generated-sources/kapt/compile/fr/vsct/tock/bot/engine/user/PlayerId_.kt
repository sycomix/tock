package fr.vsct.tock.bot.engine.user

import kotlin.String
import kotlin.collections.Collection
import kotlin.reflect.KProperty1
import org.litote.kmongo.property.KPropertyPath

class PlayerId_<T>(previous: KPropertyPath<T, *>?, property: KProperty1<*, PlayerId?>) : KPropertyPath<T, PlayerId?>(previous,property) {
    val id: KProperty1<T, String?>
        get() = org.litote.kmongo.property.KPropertyPath(this,PlayerId::id)

    val type: KProperty1<T, PlayerType?>
        get() = org.litote.kmongo.property.KPropertyPath(this,PlayerId::type)

    val clientId: KProperty1<T, String?>
        get() = org.litote.kmongo.property.KPropertyPath(this,PlayerId::clientId)
    companion object {
        val Id: KProperty1<PlayerId, String?>
            get() = PlayerId::id
        val Type: KProperty1<PlayerId, PlayerType?>
            get() = PlayerId::type
        val ClientId: KProperty1<PlayerId, String?>
            get() = PlayerId::clientId}
}

class PlayerId_Col<T>(previous: KPropertyPath<T, *>?, property: KProperty1<*, Collection<PlayerId>?>) : KPropertyPath<T, Collection<PlayerId>?>(previous,property) {
    val id: KProperty1<T, String?>
        get() = org.litote.kmongo.property.KPropertyPath(this,PlayerId::id)

    val type: KProperty1<T, PlayerType?>
        get() = org.litote.kmongo.property.KPropertyPath(this,PlayerId::type)

    val clientId: KProperty1<T, String?>
        get() = org.litote.kmongo.property.KPropertyPath(this,PlayerId::clientId)
}
