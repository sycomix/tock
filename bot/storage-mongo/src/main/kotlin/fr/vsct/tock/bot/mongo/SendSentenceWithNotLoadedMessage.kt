/*
 * Copyright (C) 2017 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.vsct.tock.bot.mongo

import fr.vsct.tock.bot.connector.ConnectorMessage
import fr.vsct.tock.bot.engine.action.Action
import fr.vsct.tock.bot.engine.action.ActionMetadata
import fr.vsct.tock.bot.engine.action.SendSentence
import fr.vsct.tock.bot.engine.dialog.Dialog
import fr.vsct.tock.bot.engine.dialog.EventState
import fr.vsct.tock.bot.engine.nlp.NlpCallStats
import fr.vsct.tock.bot.engine.user.PlayerId
import mu.KotlinLogging
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.Instant

/**
 *
 */
internal class SendSentenceWithNotLoadedMessage(val dialogId: Id<Dialog>,
                                                playerId: PlayerId,
                                                applicationId: String,
                                                recipientId: PlayerId,
                                                text: CharSequence?,
                                                id: Id<Action> = newId(),
                                                date: Instant = Instant.now(),
                                                state: EventState = EventState(),
                                                metadata: ActionMetadata = ActionMetadata(),
                                                nlpStats: NlpCallStats? = null) :
        SendSentence(playerId, applicationId, recipientId, text, mutableListOf(), id, date, state, metadata, nlpStats) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    internal var loaded = false
    private var loadedMessage: MutableList<ConnectorMessage> = mutableListOf()

    override val messages: MutableList<ConnectorMessage>
        get() {
            if (!loaded) {
                logger.debug { "load message for $id" }
                loaded = true
                loadedMessage.addAll(UserTimelineMongoDAO.loadConnectorMessage(toActionId(), dialogId))
            }
            return loadedMessage
        }
}