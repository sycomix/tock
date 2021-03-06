package fr.vsct.tock.bot.mongo

import kotlin.String
import kotlin.collections.Collection
import kotlin.collections.Set
import kotlin.reflect.KProperty1
import org.litote.kmongo.Id
import org.litote.kmongo.property.KPropertyPath

class ClientIdCol_<T>(previous: KPropertyPath<T, *>?, property: KProperty1<*, ClientIdCol?>) : KPropertyPath<T, ClientIdCol?>(previous,property) {
    val userIds: KProperty1<T, Set<String>?>
        get() = org.litote.kmongo.property.KPropertyPath(this,ClientIdCol::userIds)

    val _id: KProperty1<T, Id<ClientIdCol>?>
        get() = org.litote.kmongo.property.KPropertyPath(this,ClientIdCol::_id)
    companion object {
        val UserIds: KProperty1<ClientIdCol, Set<String>?>
            get() = ClientIdCol::userIds
        val _id: KProperty1<ClientIdCol, Id<ClientIdCol>?>
            get() = ClientIdCol::_id}
}

class ClientIdCol_Col<T>(previous: KPropertyPath<T, *>?, property: KProperty1<*, Collection<ClientIdCol>?>) : KPropertyPath<T, Collection<ClientIdCol>?>(previous,property) {
    val userIds: KProperty1<T, Set<String>?>
        get() = org.litote.kmongo.property.KPropertyPath(this,ClientIdCol::userIds)

    val _id: KProperty1<T, Id<ClientIdCol>?>
        get() = org.litote.kmongo.property.KPropertyPath(this,ClientIdCol::_id)
}
